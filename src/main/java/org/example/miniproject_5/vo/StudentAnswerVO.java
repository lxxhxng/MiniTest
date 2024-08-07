package org.example.miniproject_5.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswerVO {
    private int std;
    private boolean correct;
    private int checkedNum;
    private int sno;
    private int qno;
    private int eno;
}
