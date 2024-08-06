package org.example.miniproject_5.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {

    private Integer rno;
    private Integer eno;
    private Integer sno;
    private Integer score;

}
