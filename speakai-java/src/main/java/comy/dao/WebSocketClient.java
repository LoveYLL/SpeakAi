package comy.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

@Schema(description = "WebSocketClient")
public class WebSocketClient {

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    @Schema(description = "与某个客户端的连接会话，需要通过它来给客户端发送数据")
    private Session session;

    //连接的uri
    @Schema(description = "连接的uri")
    private String uri;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
