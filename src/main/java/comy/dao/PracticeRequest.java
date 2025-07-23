package comy.dao;

import lombok.Data;

@Data
public class PracticeRequest {
    private String text;        // 用户输入内容
    private String roleCode;    // 角色或话题代码（如 travel, interview）
    private String workspace;   // 指定 workspace 名（大模型名）
}
