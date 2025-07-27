package comy.dao;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
@Data
@TableName("role_voice") // 对应数据库表名
public class RoleVoice {

    @TableId(value = "role_code_id")
    private Long roleCodeId;

    private String roleCode;

    private String voice;

    private String description;

    private Date createTime;
}
