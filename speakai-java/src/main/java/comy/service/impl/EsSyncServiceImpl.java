package comy.service.impl;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import comy.dao.DiffWord;
import comy.dao.PronunciationDiffWords;
import comy.mapper.PronunciationDiffWordsMapper;
import comy.service.EsSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class EsSyncServiceImpl implements EsSyncService {
    @Autowired
    private ElasticsearchClient esClient;
    @Autowired
    PronunciationDiffWordsMapper diffWordMapper;
    @Override
    @Scheduled(cron = "0 */30 * * * ?")
    public void syncAllWordsToES() throws IOException {
        List<PronunciationDiffWords> wordList = diffWordMapper.selectList(null); // 使用 MyBatis-Plus 查询全部
        for (PronunciationDiffWords word : wordList) {
            esClient.index(i -> i
                    .index("pronunciation_diff_words")
                    .id(word.getId().toString())
                    .document(word));
        }
        System.out.println("同步成功，共同步：" + wordList.size() + " 条错词");
    }
    @Override
    public List<DiffWord> searchWrongWords(String keyword, int page, int size) throws IOException {
        int from = (page - 1) * size;
        var searchRequest = esClient.search(s -> s
                .index("pronunciation_diff_words")
                .from(from)
                .size(size)
                .query(q -> {
                    if (keyword == null || keyword.isBlank()) {
                        return q.matchAll(m -> m);
                    } else {
                        return q.match(m -> m
                                .field("wrongWord")
                                .query(keyword));
                    }
                }), DiffWord.class);
        List<DiffWord> results = new ArrayList<>();
        for (var hit : searchRequest.hits().hits()) {
            results.add(hit.source());
        }
        return results;
    }
}
