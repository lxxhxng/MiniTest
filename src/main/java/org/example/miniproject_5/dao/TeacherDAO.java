package org.example.miniproject_5.dao;

import lombok.Cleanup;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.TeacherVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public enum TeacherDAO {
    INSTANCE;

    public Optional<TeacherVO> get(String tid, String tpw) throws Exception {

        String query = """
                SELECT * FROM tbl_teacher
                WHERE tid = ?
                  AND tpw = ?
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, tid);
        ps.setString(2, tpw);

        @Cleanup ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            return Optional.empty();
        }

        TeacherVO teacherVO = TeacherVO.builder()
                .tno(rs.getInt("tno"))
                .tid(rs.getString("tid"))
                .tpw(rs.getString("tpw"))
                .build();

        return Optional.of(teacherVO);

    }

}