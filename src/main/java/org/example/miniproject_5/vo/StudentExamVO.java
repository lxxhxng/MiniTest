package org.example.miniproject_5.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentExamVO {
    private Integer eno;
    private String examName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer tno;
    private boolean attended; // 응시 여부
}
