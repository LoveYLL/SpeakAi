package comy.controller;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import comy.dao.ReferenceText;
import comy.result.ConversationResponse;
import comy.result.Result;
import comy.service.ReferenceTextService;
import comy.utils.AliOssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/reference")
@Tag(name = "参考文本模块", description = "提供随机参考文本和生成对应的语音")
public class ReferenceTextController {
    @Autowired
    private ReferenceTextService referenceTextService;
    @Autowired
    private AliOssService aliOssService;
    @Operation(summary = "获取随机参考文本", description = "获取一个随机的参考文本，并生成对应的语音文件")
    @GetMapping("/random")
        public Result<ConversationResponse> getRandomReferenceText() {
            ReferenceText referenceText = referenceTextService.getText();
            String text=referenceText.getContent();
            HttpRequest requestBuilder = HttpRequest.post("http://localhost:8001/tts")
                    .form("req", text);
            HttpResponse ttsResponse = requestBuilder.execute();
            byte[] audioBytes = ttsResponse.bodyBytes();
            String audioBase64 = Base64.encode(audioBytes);
            ConversationResponse resp = new ConversationResponse();
            String audioUrl = aliOssService.upload(audioBytes, ".mp3");
            resp.setText(text);
            resp.setAudioUrl(audioUrl);  // 新增字段，返回URL
            return Result.success(resp);
        }
    }