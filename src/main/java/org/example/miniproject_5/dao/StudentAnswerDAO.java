package org.example.miniproject_5.dao;

import org.example.miniproject_5.vo.StudentAnswerVO;
import org.example.miniproject_5.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum StudentAnswerDAO {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(StudentAnswerDAO.class);

    // 학생의 답안 저장 메서드
    public void saveStudentAnswer(int sno, int qno, int checkedNum, boolean isCorrect, int eno) throws SQLException {
        String sql = "INSERT INTO tbl_student_answer (correct, checked_num, sno, qno, eno) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, isCorrect);
            ps.setInt(2, checkedNum);
            ps.setInt(3, sno);
            ps.setInt(4, qno);
            ps.setInt(5, eno);

            ps.executeUpdate();
        }
    }

    // 학생의 답안 목록 조회 메서드
    public List<StudentAnswerVO> getStudentAnswers(int sno, int eno) throws SQLException {
        String sql = "SELECT * FROM tbl_student_answer WHERE sno = ? AND eno = ?";
        List<StudentAnswerVO> answerList = new ArrayList<>();

        try (Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sno);
            ps.setInt(2, eno);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentAnswerVO vo = new StudentAnswerVO(
                            rs.getInt("std"),
                            rs.getBoolean("correct"),
                            rs.getInt("checked_num"),
                            rs.getInt("sno"),
                            rs.getInt("qno"),
                            rs.getInt("eno")
                    );
                    answerList.add(vo);
                }
            }
        }

        return answerList;
    }

    // 학생의 점수 계산 메서드
    public int calculateScore(int sno, int eno) throws SQLException {
        String sql = """
            SELECT SUM(CASE WHEN correct THEN 1 ELSE 0 END) AS score
            FROM tbl_student_answer
            WHERE sno = ? AND eno = ?
            """;

        try (Connection conn = ConnectionUtil.INSTANCE.getDs().getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, sno);
            psmt.setInt(2, eno);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("score");
                }
            }
        }

        return 0;
    }

    // 문제의 정답 여부 확인 메서드
    public boolean isAnswerCorrect(int qno, int checkedNum, int eno) throws SQLException {
        String sql = "SELECT answer FROM tbl_question WHERE no1 = ? AND eno = ?";

        try (Connection conn = ConnectionUtil.INSTANCE.getDs().getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, qno);
            psmt.setInt(2, eno);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    log.info("checkedNum: " + checkedNum);
                    log.info("answer: " + rs.getInt("answer"));
                    return checkedNum == rs.getInt("answer");
                }
            }
        }

        return false;
    }

    // 학생의 점수 저장 메서드
    public void saveResult(int sno, int eno, int score) throws SQLException {
        String sql = "INSERT INTO tbl_result (sno, eno, score) VALUES (?, ?, ?)";

        try (Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sno);
            ps.setInt(2, eno);
            ps.setInt(3, score);

            ps.executeUpdate();
        }
    }

}
