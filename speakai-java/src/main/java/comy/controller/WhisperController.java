package comy.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import comy.result.Result;
import comy.utils.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
@CrossOrigin
@RestController
@RequestMapping("/api/whisper")
public class WhisperController {
    @Autowired
    KafkaProducer kafkaProducer;
    @PostMapping(value = "/text")
    public Result<String> handleAudioUpload(@RequestParam("file") MultipartFile file) throws IOException {
            String uploadDir = "src/resources/static/";
            FileUtil.mkdir(uploadDir);
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destFile = FileUtil.file(uploadDir, filename);
            InputStream in = file.getInputStream();
            FileUtil.writeFromStream(in, destFile);
            HttpRequest form = HttpRequest.post("http://localhost:8000/whisper").form("file", destFile);
            String text=form.execute().body();
            JSONObject jsonText = JSONUtil.parseObj(text);
            return Result.success(jsonText.getStr("text"));
    }

}
