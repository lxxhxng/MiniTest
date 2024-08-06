package org.example.miniproject_5.dao;

import lombok.Cleanup;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.vo.StudentVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public enum StudentDAO {
    INSTANCE;

    public Optional<StudentVO> get(String sid, String spw) throws Exception {

        String query = """
                SELECT * FROM tbl_student
                WHERE sid = ?
                  AND spw = ?
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, sid);
        ps.setString(2, spw);

        @Cleanup ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            return Optional.empty();
        }

        StudentVO studentVO = StudentVO.builder()
                .sno(rs.getInt("sno"))
                .sid(rs.getString("sid"))
                .spw(rs.getString("spw"))
                .build();

        return Optional.of(studentVO);

    }

}