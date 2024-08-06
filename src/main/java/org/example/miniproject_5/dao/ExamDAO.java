package org.example.miniproject_5.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.ExamVO;
import org.example.miniproject_5.vo.QuizVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum ExamDAO {

    INSTANCE;


    // JDBC 연결 및 데이터베이스 작업
    public List<ExamVO> getAllExams() throws Exception {

        String query = """
                select
                    *
                from tbl_exam
                where eno > 0""";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<ExamVO> list = new ArrayList<>();

        while (rs.next()) {
            ExamVO vo = ExamVO.builder()
                    .eno(rs.getInt("eno"))
                    .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                    .endTime(rs.getTimestamp("end_time").toLocalDateTime())
                    .tno(rs.getInt("tno"))
                    .examName(rs.getString("exam_name"))
                    .build();
            list.add(vo);
        }//end while

        return list;
    }

    public Integer insertExam(ExamVO examVO) throws Exception {
        String sql = "insert into tbl_exam (start_time, end_time, tno, exam_name) values (?, ?, ?, ?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        con.setAutoCommit(false);

        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setObject(1, examVO.getStartTime());
        ps.setObject(2, examVO.getEndTime());
        ps.setInt(3, examVO.getTno());
        ps.setString(4, examVO.getExamName());

        int count = ps.executeUpdate();

        if (count != 1) {
            throw new Exception("Abnormal insertion");
        }

        ps.close();

        ps = con.prepareStatement("SELECT LAST_INSERT_ID()");

        @Cleanup ResultSet rs = ps.executeQuery();

        rs.next();
        Integer eno = rs.getInt(1);

        log.info("TNO:........." + examVO.getTno());

        con.commit();
        con.setAutoCommit(true);

        return eno;
    }


    public Boolean insertQuiz(List<QuizVO> quizList, int eno) throws Exception {

        String insertSQL = "INSERT INTO tbl_question (eno, no1, text, op1, op2, op3, op4, op5, answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Connection 객체를 try 블록 외부에서 선언합니다.
        Connection con = null;

        try {
            // @Cleanup 애노테이션을 사용하여 Connection 및 PreparedStatement 자원 자동 해제
            con = ConnectionUtil.INSTANCE.getDs().getConnection();

            // 자동 커밋 비활성화
            con.setAutoCommit(false);

            @Cleanup PreparedStatement ps = con.prepareStatement(insertSQL);

            for (QuizVO quiz : quizList) {
                ps.setInt(1, eno);
                ps.setInt(2, quiz.getQno());
                ps.setString(3, quiz.getQuestion());
                ps.setString(4, quiz.getOp1());
                ps.setString(5, quiz.getOp2());
                ps.setString(6, quiz.getOp3());
                ps.setString(7, quiz.getOp4());
                ps.setString(8, quiz.getOp5());
                ps.setInt(9, quiz.getAnswer());

                ps.addBatch(); // Batch 처리
            }

            int[] updateCounts = ps.executeBatch(); // Batch 실행

            // 배치 작업 결과 확인
            for (int count : updateCounts) {
                if (count == PreparedStatement.EXECUTE_FAILED) {
                    log.error("Failed to insert some quizzes into tbl_q table.");
                    // 오류 발생 시 롤백
                    con.rollback();
                    return false;
                }
            }

            // 모든 작업이 성공적으로 완료되면 트랜잭션 커밋
            con.commit();
            log.info("Quiz data successfully inserted into tbl_q table.");
            return true;

        } catch (Exception e) {
            log.error("Error inserting quiz data into tbl_q table", e);

            // 오류 발생 시 트랜잭션 롤백
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException se) {
                    log.error("Error during transaction rollback", se);
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    // 자동 커밋을 다시 활성화
                    con.setAutoCommit(true);
                } catch (SQLException se) {
                    log.error("Error setting auto-commit back to true", se);
                }
            }
        }
    }


}