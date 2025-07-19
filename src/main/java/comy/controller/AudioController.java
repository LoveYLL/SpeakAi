package comy.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/audio")
public class AudioController {
    @PostMapping(value = "/score")
    public Map<String, Object> handleAudioUpload(@RequestParam("file") MultipartFile file,@RequestParam String refText) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 保存录音文件
            String uploadDir = "src/resources/static/";
            // 创建目录（自动判断是否存在）
            FileUtil.mkdir(uploadDir);
            // 目标文件路径
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destFile = FileUtil.file(uploadDir, filename);
            // 使用 Hutool 将上传文件保存
            try (InputStream in = file.getInputStream()) {
                FileUtil.writeFromStream(in, destFile);}
            HttpRequest form = HttpRequest.post("http://localhost:8000/whisper").form("file", destFile);
            String text=form.execute().body();
            JSONObject jsonText = JSONUtil.parseObj(text);
            System.out.println(jsonText.getStr("text"));


            //发音评分逻辑
            String userText=jsonText.getStr("text");
            int distance = StringUtils.getLevenshteinDistance(userText.toLowerCase(), refText.toLowerCase());
            int maxLen = Math.max(userText.length(), refText.length());
            float v =1.0f - Float.intBitsToFloat(distance) / Float.intBitsToFloat(maxLen);
            String message;
            int score=Math.round(v*100);
            if (score>=80) message="发音非常棒";
            else if (score>=60) message="发音不错哦";
            else message="发音不太好，还需要加油哦";
            result.put("success",true);
            result.put("score",score);
            result.put("message",message);
            return result;
       }
        catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "上传失败：" + e.getMessage());
            return result;}
    }

}
