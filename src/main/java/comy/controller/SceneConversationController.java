package comy.controller;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import comy.dao.PracticeRequest;
import comy.result.ConversationResponse;
import comy.result.Result;
import comy.service.RoleVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/conversion")
public class SceneConversationController {
    private String currentModel = "speakai";
    private String voice=null;
    @Autowired
    RoleVoiceService roleVoiceService;
    @PostMapping("/chat")
    public Result<ConversationResponse> conversation(@RequestBody PracticeRequest request){
        String text = request.getText();
        String roleCode = request.getRoleCode();
        String workspace = request.getWorkspace();
        // 根据场景切换模型（只要传入了场景就更新）
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
        // 获取二进制数据
        byte[] audioBytes = ttsResponse.bodyBytes();
        // 转Base64
        String audioBase64 = Base64.encode(audioBytes);
        // 构造返回对象
        ConversationResponse resp = new ConversationResponse();
        resp.setText(reply);
        resp.setAudioBase64(audioBase64);
        System.out.println(audioBase64);
        return Result.success(resp);
    }
}
