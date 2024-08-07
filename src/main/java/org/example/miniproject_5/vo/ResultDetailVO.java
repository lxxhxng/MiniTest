package org.example.miniproject_5.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDetailVO {

    private Integer no;
    private String question;
    private String op1;
    private String op2;
    private String op3;
    private String op4;
    private String op5;
    private int answer;
    private int checked;
    private Boolean correct;
}
