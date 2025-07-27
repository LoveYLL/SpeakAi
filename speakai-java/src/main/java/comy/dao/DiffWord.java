package comy.dao;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class DiffWord {
    private Long id;
    private String wrongWord;
    private LocalDateTime createTime;
}
