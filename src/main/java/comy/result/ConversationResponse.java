package comy.result;

import lombok.Data;

@Data
public class ConversationResponse {
    private String text;
    private String audioBase64;

    public ConversationResponse() {}

    public ConversationResponse(String text, String audioBase64) {
        this.text = text;
        this.audioBase64 = audioBase64;
    }
}
