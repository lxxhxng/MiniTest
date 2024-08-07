package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.util.ConnectionUtil;
import org.example.miniproject_5.util.CookieUtil;
import org.example.miniproject_5.util.ExcelReader;
import org.example.miniproject_5.vo.ExamVO;
import org.example.miniproject_5.vo.QuizVO;
import org.example.miniproject_5.vo.TeacherVO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;

@WebServlet(value = "/teacher/examReg")
@Log4j2
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)
public class ExamRegController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("examregister GET ");

        req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("examregister POST ");

        // 시험 이름과 날짜를
        String title = req.getParameter("title");
        String stimeStr = req.getParameter("stime");
        String etimeStr = req.getParameter("etime");

        log.info("stimeStr: " + stimeStr);
        log.info("etimeStr: " + etimeStr);

        if (title == null || stimeStr == null || etimeStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
            return;
        }

        LocalDateTime stime;
        LocalDateTime etime;

        try {
            stime = LocalDateTime.parse(stimeStr);
            etime = LocalDateTime.parse(etimeStr);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            return;
        }

        HttpSession session = req.getSession(false);
        TeacherVO teacher = (TeacherVO) session.getAttribute("teacher");

        Integer tno = (teacher != null) ? teacher.getTno() : null;

        if (tno == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid teacher information");
            return;
        }

        ExamVO examVO = ExamVO.builder()
                .startTime(stime)
                .endTime(etime)
                .tno(tno)
                .examName(title)
                .build();

        Part filePart = req.getPart("examFile");

        if (filePart == null || filePart.getInputStream() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
            return;
        }

        @Cleanup InputStream in = filePart.getInputStream();
        Connection con = null;

        try {
            // DB 연결 및 트랜잭션 시작
            @Cleanup Connection connection = ConnectionUtil.INSTANCE.getDs().getConnection();
            connection.setAutoCommit(false);

            // 시험 데이터 삽입
            Integer eno = ExamDAO.INSTANCE.insertExam(examVO, connection);

            // 엑셀 파일로부터 문제 데이터 읽기
            List<QuizVO> quizVOList = ExcelReader.readInputStream(in);

            // 문제 데이터 삽입
            Boolean check = ExamDAO.INSTANCE.insertQuiz(quizVOList, eno, connection);

            if (!check) {
                throw new RuntimeException("Error inserting quizzes");
            }

            // 모든 작업이 성공적으로 완료되면 트랜잭션 커밋
            connection.commit();
            resp.sendRedirect("/teacher/examList");

        } catch (Exception e) {
            log.error("Error processing exam and quizzes", e);

            // 오류 발생 시 트랜잭션 롤백
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException se) {
                    log.error("Error during transaction rollback", se);
                }
            }
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        } finally {
            // 자동 커밋을 다시 활성화
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException se) {
                    log.error("Error setting auto-commit back to true", se);
                }
            }
        }
    }

}

