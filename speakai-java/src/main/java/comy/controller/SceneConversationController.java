package comy.controller;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import comy.dao.PracticeRequest;
import comy.result.ConversationResponse;
import comy.result.Result;
import comy.service.RoleVoiceService;
import comy.utils.AliOssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@CrossOrigin
@RestController
@RequestMapping("/api/conversation")
@Tag(name = "场景对话模块", description = "提供场景对话的接口，包括用户输入和语音合成等功能")
public class SceneConversationController {
    private String currentModel = "speakai";
    private String voice=null;
    @Autowired
    RoleVoiceService roleVoiceService;
    @Autowired
    AliOssService aliOssService;
    @Autowired
    WhisperController whisperController;
    @PostMapping("/chat")
    @Operation(summary = "进行场景对话",
            description = "用户可以通过文本或上传音频与场景对话，并根据角色选择语音模型进行语音合成")
    public Result<ConversationResponse> conversation(
            @ModelAttribute PracticeRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException{
        String text = request.getText();
        String roleCode = request.getRoleCode();
        String workspace = request.getWorkspace();
        if(text == null || text.isEmpty()){
            Result<String> stringResult = whisperController.handleAudioUpload(file);
            text=stringResult.getData();
        }
        if (workspace != null && !workspace.isEmpty())  currentModel = workspace;
        JSONObject modelRequest= JSONUtil.createObj()
                .putOnce("message", text)
                .putOnce("mode","chat")
                .putOnce("sessionId","identifier-to-partition-chats-by-external-id")
                .putOnce("reset",false);
        HttpResponse response= HttpRequest.post("http://localhost:3001/api/v1/workspace/" + currentModel + "/chat")
                .header(Header.USER_AGENT, "Hutool http")
                .header("Authorization", "Bearer CH5WWBZ-DKMMDZ0-MT62883-VN3DA9J")
                .header("accept", "application/json")
                .header(Header.CONTENT_TYPE, "application/json")
                .body(modelRequest.toString())
                .execute();
        JSONObject jsonResp = JSONUtil.parseObj(response.body());
        String reply = jsonResp.getStr("textResponse");
        reply = reply.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9.,!?\\s]", "");
        HttpRequest requestBuilder = HttpRequest.post("http://localhost:8001/tts")
                .form("req", reply);
        if (roleCode != null && !roleCode.trim().isEmpty()) {
            voice = roleVoiceService.getVoiceByRoleCode(roleCode);
            requestBuilder.form("voice",voice);}
        HttpResponse ttsResponse = requestBuilder.execute();
        byte[] audioBytes = ttsResponse.bodyBytes();
        ConversationResponse resp = new ConversationResponse();
        String audioUrl = aliOssService.upload(audioBytes, ".mp3");
        resp.setText(reply);
        resp.setAudioUrl(audioUrl);
        return Result.success(resp);
    }
}