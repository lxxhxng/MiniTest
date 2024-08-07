package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.miniproject_5.dao.ExamDAO;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.vo.ExamVO;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/teacher/examList")
@Log4j2
public class ExamListController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        HttpSession session = req.getSession(false);

        // 세션이 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("teacher") == null) {
            resp.sendRedirect("/teacher/login");
            return;
        }

        try {
            // 시험 목록을 데이터베이스에서 조회
            List<List<ExamVO>> exams = ExamDAO.INSTANCE.getAllExams();
            List<ExamVO> finishedExams = exams.get(0);
            List<ExamVO> ongoingExams = exams.get(1);

            // 시험 목록을 요청 속성에 추가
            req.setAttribute("finishedExams", finishedExams);
            req.setAttribute("ongoingExams", ongoingExams);

            // JSP 페이지로 포워딩
            req.getRequestDispatcher("/WEB-INF/teacher/examList.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/teacher/examReg.jsp").forward(req, resp);
    }
}