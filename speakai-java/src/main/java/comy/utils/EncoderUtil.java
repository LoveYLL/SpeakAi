package comy.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
public class EncoderUtil<T> implements Encoder.Text<T> {
    @Override
    public  String encode(T t) throws EncodeException {
        try{
            JsonMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(t);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void init(EndpointConfig endpointConfig) {
        Text.super.init(endpointConfig);
    }
    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
