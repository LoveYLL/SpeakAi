package comy.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import comy.result.Result;
import comy.utils.KafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api/audio")
@Tag(name = "跟读模块", description = "用于处理用户上传音频文件进行发音评分和文字比对的功能")
public class AudioController {
    @Autowired
    KafkaProducer kafkaProducer;
    @Operation(summary = "上传音频文件并评分", description = "接收用户上传的音频文件，与参考文本进行比对，返回发音评分及相关信息")
    @PostMapping(value = "/score")
    public Result<Map<String, Object>> handleAudioUpload(@RequestParam("file") MultipartFile file,
                                                         @RequestParam String refText) {
        Map<String, Object> result = new HashMap<>();
        try {
            String uploadDir = "src/resources/static/";
            FileUtil.mkdir(uploadDir);
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destFile = FileUtil.file(uploadDir, filename);
            try (InputStream in = file.getInputStream()) {
                FileUtil.writeFromStream(in, destFile);}
            HttpRequest form = HttpRequest.post("http://localhost:8000/whisper").form("file", destFile);
            String text=form.execute().body();
            JSONObject jsonText = JSONUtil.parseObj(text);
            System.out.println(jsonText.getStr("text"));
            String userText=jsonText.getStr("text");
            int distance = StringUtils.getLevenshteinDistance(userText.toLowerCase(), refText.toLowerCase());
            int maxLen = Math.max(userText.length(), refText.length());
            float v =1.0f - Float.intBitsToFloat(distance) / Float.intBitsToFloat(maxLen);
            String message;
            int score=Math.round(v*100);
            if (score>=80) message="发音非常棒";
            else if (score>=60) message="发音不错哦";
            else message="发音不太好，还需要加油哦";
            result.put("score",score);
            result.put("message",message);
            kafkaProducer.sendDiffWords(refText, userText);
            return Result.success(result);}
        catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "上传失败：" + e.getMessage());
            return Result.success(result);}
    }
}
