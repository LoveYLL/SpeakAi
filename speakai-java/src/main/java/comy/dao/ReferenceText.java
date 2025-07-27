package comy.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("reference_text")
public class ReferenceText {
    @TableId
    private Long referenceTextId;
    private String content;
    private String level;
    private LocalDateTime createdTime;
}
