package org.example.miniproject_5.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.ExamVO;
import org.example.miniproject_5.vo.QuizVO;
import org.example.miniproject_5.vo.ResultDetailVO;
import org.example.miniproject_5.vo.ResultVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public enum ExamDAO {

    INSTANCE;

    // JDBC 연결 및 데이터베이스 작업
    public List<List<ExamVO>> getAllExams() throws Exception {
        String query = """
                select
                    *
                from tbl_exam
                where eno > 0
                order by eno desc""";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<ExamVO> finishedExams = new ArrayList<>();
        List<ExamVO> ongoingExams = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        while (rs.next()) {
            ExamVO vo = ExamVO.builder()
                    .eno(rs.getInt("eno"))
                    .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                    .endTime(rs.getTimestamp("end_time").toLocalDateTime())
                    .tno(rs.getInt("tno"))
                    .examName(rs.getString("exam_name"))
                    .build();

            if (vo.getEndTime().isBefore(now)) {
                finishedExams.add(vo);
            } else {
                ongoingExams.add(vo);
            }
        }

        List<List<ExamVO>> result = new ArrayList<>();
        result.add(finishedExams);
        result.add(ongoingExams);

        return result;
    }

    public Integer insertExam(ExamVO examVO, Connection con) throws Exception {
        log.info("insert exam start");
        String sql = "insert into tbl_exam (start_time, end_time, tno, exam_name) values (?, ?, ?, ?)";

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

        return eno;
    }

    public Boolean insertQuiz(List<QuizVO> quizList, int eno, Connection con) throws Exception {
        String insertSQL = "INSERT INTO tbl_question (eno, no1, text, op1, op2, op3, op4, op5, answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        log.info("Quiz data successfully inserted into tbl_q table.");
        return true;
    }

    public List<ResultVO> getResult(int examNo) throws Exception {
        String sql = """
                select *
                from tbl_result
                where eno = ?
                """;
        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, examNo);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<ResultVO> list = new ArrayList<>();

        while (rs.next()) {
            ResultVO result = ResultVO.builder()
                    .rno(rs.getInt("rno"))
                    .sno(rs.getInt("sno"))
                    .eno(rs.getInt("eno"))
                    .score(rs.getInt("score"))
                    .build();
            list.add(result);
        }

        return list;
    }

    // 특정 시험 번호에 대한 시험 정보를 조회하는 메서드
    public Optional<ExamVO> getExamById(Integer examNum) throws Exception {
        String query = """
                SELECT *
                FROM tbl_exam
                WHERE eno = ?""";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, examNum);

        @Cleanup ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ExamVO examVO = ExamVO.builder()
                    .eno(rs.getInt("eno"))
                    .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                    .endTime(rs.getTimestamp("end_time").toLocalDateTime())
                    .tno(rs.getInt("tno"))
                    .examName(rs.getString("exam_name"))
                    .build();
            return Optional.of(examVO);
        }

        return Optional.empty();
    }

    public List<ResultDetailVO> getResultDetail(Integer eno, Integer sno) throws Exception {
        String sql = """
                SELECT
                q.no1, q.text, q.op1, q.op2,
                                    q.op3, q.op4, q.op5, q.answer,
                                    sa.checked_num, sa.correct
                                FROM
                                    tbl_question q
                                JOIN
                                    (select * from tbl_student_answer where eno=? AND sno =?) sa ON q.no1 = sa.qno
                                WHERE
                                    q.eno = ? AND sa.sno = ?;
                """;
        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, eno);
        ps.setInt(3, eno);
        ps.setInt(2,sno);
        ps.setInt(4,sno);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<ResultDetailVO> list = new ArrayList<>();

        while (rs.next()) {
            ResultDetailVO result = ResultDetailVO.builder()
                    .no(rs.getInt("no1"))
                    .question(rs.getString("text"))
                    .op1(rs.getString("op1"))
                    .op2(rs.getString("op2"))
                    .op3(rs.getString("op3"))
                    .op4(rs.getString("op4"))
                    .op5(rs.getString("op5"))
                    .answer(rs.getInt("answer"))
                    .checked(rs.getInt("checked_num"))
                    .correct(rs.getBoolean("correct"))
                    .build();
            list.add(result);
        }

        return list;
    }

}