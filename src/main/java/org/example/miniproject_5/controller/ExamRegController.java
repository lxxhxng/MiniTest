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
import java.util.List;

@WebServlet(value = "/examreg")
@Log4j2
public class ExamRegController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("examregister GET ");


        try {
            Integer makeexam = ExamDAO.INSTANCE.insertExam(stime, etime, tno,title);
            req.setAttribute("eno",makeexam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //시험이름과 날짜를
        String title = req.getParameter("title");
        String time = req.getParameter("time");
        Integer tno = null;
        Integer eno = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 쿠키의 이름이 "tid"인지 확인
                if ("tid".equals(cookie.getName())) {
                    // "tid" 쿠키의 값을 반환
                    tno = Integer.valueOf(cookie.getValue());
                }
            }
        }
        try {
            Integer makeexam = ExamDAO.INSTANCE.insertExam(time,tno,title);
            eno = makeexam;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Part filePart = req.getPart("examFile");


        @Cleanup InputStream in = filePart.getInputStream();


        try {
            List<QuizVO> quizVOList = ExcelReader.readInputStream(in);
            log.info(quizVOList);
            Boolean check = ExamDAO.INSTANCE.insertQuiz(quizVOList, eno);

            resp.sendRedirect("/examlist");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
