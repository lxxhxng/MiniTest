package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.util.CookieUtil;
import org.example.miniproject_5.util.ExcelReader;
import org.example.miniproject_5.vo.ExamVO;
import org.example.miniproject_5.vo.QuizVO;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(value = "/teacher/examReg")
@Log4j2
public class ExamRegController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("examregister GET ");

        req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //시험이름과 날짜를
        String title = req.getParameter("title");
        LocalDateTime stime = LocalDateTime.parse(req.getParameter("stime"));
        LocalDateTime etime = LocalDateTime.parse(req.getParameter("etime"));
        Cookie tidcks = CookieUtil.getCookie(req,"tid");
        Integer tno = Integer.valueOf(tidcks.getValue());

        ExamVO examVO = ExamVO.builder()
                .start_time(stime)
                .end_time(etime)
                .tno(tno)
                .exam_name(title)
                .build();

        Integer eno = null;

        try {
            Integer makeexam = ExamDAO.INSTANCE.insertExam(examVO);
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

            resp.sendRedirect("/teacher/examlist");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
