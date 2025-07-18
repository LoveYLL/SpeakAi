package comy;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
public class OllamaTest {


    @Resource
    private OllamaChatModel ollamaChatModel;


    @Test
    public void test(){
        String s = ollamaChatModel.call("hello");
        System.out.println(s);
    }

}
