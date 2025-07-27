package comy.dao;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@TableName("pronunciation_diff_words")
public class PronunciationDiffWords {
    
    @TableId
    private Long id;

    private String wrongWord;

    private LocalDateTime createTime;
}
