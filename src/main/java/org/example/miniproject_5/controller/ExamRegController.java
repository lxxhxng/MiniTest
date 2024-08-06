package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.util.ExcelReader;
import org.example.miniproject_5.vo.QuizVO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(value = "/examreg")
@Log4j2
public class ExamRegController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("examregister GET ");
        req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 시험 이름과 날짜를 가져옵니다
        String title = req.getParameter("title");
        LocalTime startTime = LocalTime.parse(req.getParameter("startTime"), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime endTime = LocalTime.parse(req.getParameter("endTime"), DateTimeFormatter.ofPattern("HH:mm:ss"));
        Integer tno = null;
        Integer eno = null;

        // 쿠키에서 tno 값을 가져옵니다
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("tid".equals(cookie.getName())) {
                    tno = Integer.valueOf(cookie.getValue());
                }
            }
        }

        try {
            // 시험을 데이터베이스에 등록합니다
            eno = ExamDAO.INSTANCE.insertExam(Time.valueOf(startTime), Time.valueOf(endTime), tno, title);

            // 파일을 처리합니다
            Part filePart = req.getPart("examFile");
            @Cleanup InputStream in = filePart.getInputStream();
            List<QuizVO> quizVOList = ExcelReader.readInputStream(in);
            log.info(quizVOList);

            // 문제를 데이터베이스에 등록합니다
            boolean check = ExamDAO.INSTANCE.insertQuiz(quizVOList, eno);

            // 성공적으로 등록되었으면 examlist로 리다이렉트합니다
            resp.sendRedirect("/examlist");

        } catch (Exception e) {
            log.error("Error processing exam registration", e);
            req.setAttribute("errorMessage", "시험 등록 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
        }
    }
}
