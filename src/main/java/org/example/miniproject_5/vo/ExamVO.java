package org.example.miniproject_5.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamVO {

    private Integer eno;
    private LocalDateTime stime;
    private LocalDateTime etime;
    private int tno;
    private String ename;

}
