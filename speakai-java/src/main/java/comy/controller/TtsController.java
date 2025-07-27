package comy.controller;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import comy.result.ConversationResponse;
import comy.result.Result;
import comy.utils.AliOssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/tts")
@Tag(name = "TTS模块", description = "提供文本转语音（TTS）服务")
public class TtsController {
    @Autowired
    AliOssService aliOssService;
    @PostMapping("/generate")
    @Operation(summary = "生成语音",
            description = "接收文本并通过 TTS 服务生成语音，返回音频 URL 和原文本")
    public Result<ConversationResponse> generateTts(String text) {
        HttpResponse response = HttpRequest.post("http://localhost:8001/tts")
                .form("req", text)
                .execute();
        byte[] audioBytes = response.bodyBytes();
        ConversationResponse resp = new ConversationResponse();
        String audioUrl = aliOssService.upload(audioBytes, ".mp3");
        resp.setText(text);
        resp.setAudioUrl(audioUrl);
        return Result.success(resp);
    }
}
