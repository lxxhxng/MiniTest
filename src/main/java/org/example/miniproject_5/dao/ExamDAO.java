package org.example.miniproject_5.dao;

import lombok.Cleanup;

import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.ExamVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum ExamDAO {

    INSTANCE;


    // JDBC 연결 및 데이터베이스 작업
    public List<ExamVO> getAllExams() throws Exception {

        String query = """
                select
                    *
                from tbl_e
                where eno > 0""";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<ExamVO> list = new ArrayList<>();

        while (rs.next()) {
            ExamVO vo = ExamVO.builder()
                    .eno(rs.getInt("eno"))
                    .stime(rs.getTimestamp("stime").toLocalDateTime())
                    .etime(rs.getTimestamp("etime").toLocalDateTime())
                    .tno(rs.getInt("tno"))
                    .ename(rs.getString("ename"))
                    .build();
            list.add(vo);
        }//end while

        return list;
    }


}
