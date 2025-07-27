package comy.utils;
import cn.hutool.json.JSONUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Set;
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "comparewords";
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendDiffWords(String refText, String userText) {
        Set<String> diffWords = TextDiffUtil.findMissingWords(refText, userText);
        String json = JSONUtil.toJsonStr(diffWords);
        kafkaTemplate.send(TOPIC, json);
    }}