package comy.utils;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import comy.dao.PronunciationDiffWords;
import comy.mapper.PronunciationDiffWordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class KafkaConsumer {
    @Autowired
    PronunciationDiffWordsMapper diffWordsMapper;

    @KafkaListener(topics = "comparewords", groupId = "pronunciation-group")
    public void listen(String message) {
        Set<String> diffWords = JSONUtil.toBean(message, new TypeReference<Set<String>>() {}, true);
        System.out.println("接收到差异单词: " + diffWords);

        for (String word : diffWords) {
            // 只接受纯英文单词（字母a-zA-Z组成）
            if (word != null && word.matches("^[a-zA-Z]+$")) {
                PronunciationDiffWords diffWord = new PronunciationDiffWords();
                diffWord.setWrongWord(word);
                diffWordsMapper.insert(diffWord);}}
    }
}
