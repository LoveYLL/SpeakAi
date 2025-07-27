package comy.dao;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime createdTime;
}
