package comy.dao;

import lombok.Data;

@Data
public class PracticeRequest {
    private String text;
    private String roleCode;
    private String workspace;
}
