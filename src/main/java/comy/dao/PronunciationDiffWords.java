package comy.dao;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@TableName("pronunciation_diff_words") // 对应表名
public class PronunciationDiffWords {
    
    @TableId
    private Long id;

    private String wrongWord;

    private LocalDateTime createTime;
}
