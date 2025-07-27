package comy.websocket;
import comy.dao.WebSocketClient;
import comy.utils.EncoderUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
@ServerEndpoint(value = "/websocket/{username}",encoders = {EncoderUtil.class})
@Component
@CrossOrigin
public class WebSocketServer {
    private static ApplicationContext applicationContext;
    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String username;
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String userName){
        System.out.println(userName+"连接成功");
        this.session = session;
        this.username = userName;
        WebSocketClient client = new WebSocketClient();
        client.setSession(session);
        client.setUri(session.getRequestURI().toString());
        webSocketMap.put(userName, client);
    }
    @OnClose
    public void onClose(){
        System.out.println("断开连接");
    }
    @OnError
    public void onError(Throwable error,Session session){
        error.printStackTrace();
    }
    @OnMessage
    public void onMsg(Session session,String msg) throws IOException {
        System.out.println("收到消息："+msg+"，是"+session.getId()+"发的!");
    }
    public<T> void sendVo(String userName,T vo){
        try{
            WebSocketClient webSocketClient = webSocketMap.get(userName);
            if(webSocketClient != null){
                webSocketClient.getSession().getBasicRemote().sendObject(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMsg(String userName,String msg){
        try {
            WebSocketClient webSocketClient = webSocketMap.get(userName);
            if (webSocketClient != null) {
                webSocketClient.getSession().getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
