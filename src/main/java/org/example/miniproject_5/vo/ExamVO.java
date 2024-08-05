package org.example.miniproject_5.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamVO {

    private Integer eno;
    private Time stime;
    private Time etime;
    private int tno;
    private String ename;

}
