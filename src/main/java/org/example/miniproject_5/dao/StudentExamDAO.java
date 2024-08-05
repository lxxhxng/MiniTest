package org.example.miniproject_5.dao;

import lombok.Cleanup;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.StudentExamVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public enum StudentExamDAO {
    INSTANCE;

    public List<StudentExamVO> getExams(Integer sno) throws Exception {
        String attendedQuery = """
                SELECT e.eno, e.ename, e.stime, e.etime, e.tno, TRUE AS attended
                FROM tbl_e e
                JOIN tbl_r r ON e.eno = r.eno
                WHERE r.sno = ?
                """;

        String notAttendedQuery = """
                SELECT e.eno, e.ename, e.stime, e.etime, e.tno, FALSE AS attended
                FROM tbl_e e
                LEFT JOIN tbl_r r ON e.eno = r.eno AND r.sno = ?
                WHERE r.sno IS NULL
                """;

        List<StudentExamVO> exams = new ArrayList<>();

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();

        // Get attended exams
        @Cleanup PreparedStatement psAttended = con.prepareStatement(attendedQuery);
        psAttended.setInt(1, sno);
        @Cleanup ResultSet rsAttended = psAttended.executeQuery();

        while (rsAttended.next()) {
            StudentExamVO examVO = StudentExamVO.builder()
                    .eno(rsAttended.getInt("eno"))
                    .ename(rsAttended.getString("ename"))
                    .stime(rsAttended.getTimestamp("stime").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .etime(rsAttended.getTimestamp("etime").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .tno(rsAttended.getInt("tno"))
                    .attended(rsAttended.getBoolean("attended"))
                    .build();
            exams.add(examVO);
        }

        // Get not attended exams
        @Cleanup PreparedStatement psNotAttended = con.prepareStatement(notAttendedQuery);
        psNotAttended.setInt(1, sno);
        @Cleanup ResultSet rsNotAttended = psNotAttended.executeQuery();

        while (rsNotAttended.next()) {
            StudentExamVO examVO = StudentExamVO.builder()
                    .eno(rsNotAttended.getInt("eno"))
                    .ename(rsNotAttended.getString("ename"))
                    .stime(rsNotAttended.getTimestamp("stime").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .etime(rsNotAttended.getTimestamp("etime").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .tno(rsNotAttended.getInt("tno"))
                    .attended(rsNotAttended.getBoolean("attended"))
                    .build();
            exams.add(examVO);
        }

        return exams;
    }
}
