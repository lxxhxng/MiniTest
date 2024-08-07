package org.example.miniproject_5.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.StudentExamVO;
import org.example.miniproject_5.vo.StudentResultVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum StudentExamDAO {
    INSTANCE;

    public List<StudentExamVO> getExams(Integer sno) throws Exception {

        // 응시한 시험
        String attendedQuery = """
                SELECT e.eno, e.exam_name, e.start_time, e.end_time, e.tno, TRUE AS attended
                FROM tbl_exam e
                JOIN tbl_result r ON e.eno = r.eno
                WHERE r.sno = ?
                """;

        // 미응시한 시험
        String notAttendedQuery = """
                SELECT e.eno, e.exam_name, e.start_time, e.end_time, e.tno, FALSE AS attended
                FROM tbl_exam e
                LEFT JOIN tbl_result r ON e.eno = r.eno AND r.sno = ?
                WHERE r.sno IS NULL
                """;

        List<StudentExamVO> exams = new ArrayList<>();

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();

        // 응시한 시험 목록
        @Cleanup PreparedStatement psAttended = con.prepareStatement(attendedQuery);
        psAttended.setInt(1, sno);
        @Cleanup ResultSet rsAttended = psAttended.executeQuery();

        while (rsAttended.next()) {
            StudentExamVO examVO = StudentExamVO.builder()
                    .eno(rsAttended.getInt("eno"))
                    .examName(rsAttended.getString("exam_name"))
                    .startTime(rsAttended.getTimestamp("start_time").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .endTime(rsAttended.getTimestamp("end_time").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .tno(rsAttended.getInt("tno"))
                    .attended(rsAttended.getBoolean("attended"))
                    .build();
            exams.add(examVO);
        }

        // 미응시한 시험 목록
        @Cleanup PreparedStatement psNotAttended = con.prepareStatement(notAttendedQuery);
        psNotAttended.setInt(1, sno);
        @Cleanup ResultSet rsNotAttended = psNotAttended.executeQuery();

        while (rsNotAttended.next()) {
            StudentExamVO examVO = StudentExamVO.builder()
                    .eno(rsNotAttended.getInt("eno"))
                    .examName(rsNotAttended.getString("exam_name"))
                    .startTime(rsNotAttended.getTimestamp("start_time").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .endTime(rsNotAttended.getTimestamp("end_time").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .tno(rsNotAttended.getInt("tno"))
                    .attended(rsNotAttended.getBoolean("attended"))
                    .build();
            exams.add(examVO);
        }
        log.info("Exams retrieved: {}", exams);
        return exams;
    }

    public List<StudentResultVO> getStudentResult(Integer sno, Integer qno) throws Exception {

        String query = """
                select *
                from tbl_student_answer
                where qno = ?
                  and sno = ?
                """;

        List<StudentResultVO> result = new ArrayList<>();

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();

        @Cleanup PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, qno);
        ps.setInt(2, sno);

        @Cleanup ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            StudentResultVO studentResultVO = StudentResultVO.builder()
                    .std(rs.getInt("std"))
                    .correct(rs.getBoolean("correct"))
                    .checkedNum(rs.getInt("checked_num"))
                    .qno(rs.getInt("qno"))
                    .sno(rs.getInt("sno"))
                    .build();
            result.add(studentResultVO);
        }

        return result;

    }
}
