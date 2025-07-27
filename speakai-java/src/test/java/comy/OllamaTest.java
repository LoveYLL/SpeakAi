package comy;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class OllamaTest {


    @Resource
    private OllamaChatModel ollamaChatModel;


    @Test
    public void test() throws FileNotFoundException {
//        HttpRequest form = HttpRequest.post("http://localhost:8000/whisper").
//                form("file", new File("src/main/resources/static/output3.wav"));
//        System.out.println(form.execute().body());



//        String s=ollamaChatModel.call(form.execute().body());
//        System.out.println("AL回复："+s);
        String s="hello,who is there";
        HttpResponse voice = HttpRequest.post("http://localhost:8001/tts")
                .form("req",s).form("voice", "en-GB-RyanNeural").execute();
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/static/output10.mp3")) {
            IoUtil.copy(voice.bodyStream(), fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//            String expected="hellooo",actual="hell";
//            int distance = StringUtils.getLevenshteinDistance(expected.toLowerCase(), actual.toLowerCase());
//            int maxLen = Math.max(expected.length(), actual.length());
//            float v =1.0f - Float.intBitsToFloat(distance) / Float.intBitsToFloat(maxLen);
//            System.out.println(Math.round(v*100));

    }

}
