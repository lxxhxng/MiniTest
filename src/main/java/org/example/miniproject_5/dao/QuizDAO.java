package org.example.miniproject_5.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.QuizVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum QuizDAO {
    INSTANCE;

    public List<QuizVO> getQuizzesByExamNum(Integer examNum) throws Exception {
        String query = """
                SELECT no1 AS qno, text AS question, op1, op2, op3, op4, op5, answer
                FROM tbl_question
                WHERE eno = ?
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, examNum);

        @Cleanup ResultSet rs = ps.executeQuery();

        List<QuizVO> quizList = new ArrayList<>();
        while (rs.next()) {
            QuizVO quizVO = QuizVO.builder()
                    .qno(rs.getInt("qno"))
                    .question(rs.getString("question"))
                    .op1(rs.getString("op1"))
                    .op2(rs.getString("op2"))
                    .op3(rs.getString("op3"))
                    .op4(rs.getString("op4"))
                    .op5(rs.getString("op5"))
                    .answer(rs.getInt("answer"))
                    .build();
            quizList.add(quizVO);
        }

        return quizList;
    }
}
