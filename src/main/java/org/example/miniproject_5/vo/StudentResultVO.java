package org.example.miniproject_5.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResultVO {

    private Integer std;
    private boolean correct;
    private Integer checkedNum;
    private Integer sno;
    private Integer qno;

}
