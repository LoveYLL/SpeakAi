package comy.utils;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import comy.dao.PronunciationDiffWords;
import comy.mapper.PronunciationDiffWordsMapper;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class KafkaConsumer {
    @Autowired
    PronunciationDiffWordsMapper diffWordsMapper;
    @Autowired
    ElasticsearchClient esClient;  // 注入 ElasticsearchClient
    @KafkaListener(topics = "comparewords", groupId = "pronunciation-group")
    public void listen(String message) throws IOException {
        Set<String> diffWords = JSONUtil.toBean(message, new TypeReference<Set<String>>() {}, true);
        System.out.println("接收到差异单词: " + diffWords);
        for (String word : diffWords) {
            if (word != null && word.matches("^[a-zA-Z]+$")) {
                // 查询是否存在
                Long count = diffWordsMapper.selectCount(
                        new QueryWrapper<PronunciationDiffWords>()
                                .eq("wrong_word", word)
                );
                if (count == 0) {
                    // 写入数据库
                    PronunciationDiffWords diffWord = new PronunciationDiffWords();
                    diffWord.setWrongWord(word);
                    diffWordsMapper.insert(diffWord);

                    // 写入 Elasticsearch
                    IndexRequest<PronunciationDiffWords> indexRequest = new IndexRequest.Builder<PronunciationDiffWords>()
                            .index("pronunciation_diff_words")  // 这里指定ES索引名
                            .id(word)  // 可以使用word作为id
                            .document(diffWord)  // 写入的文档内容
                            .build();
                    // 执行索引请求
                    IndexResponse indexResponse = esClient.index(indexRequest);
                    System.out.println("写入ES成功，ID: " + indexResponse.id());
                }}}}}
