package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentExamDAO;
import org.example.miniproject_5.vo.StudentExamVO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/student/examList")
@Log4j2
public class StudentExamListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("student") != null) {
            StudentVO studentVO = (StudentVO) session.getAttribute("student");
            Integer sno = studentVO.getSno();

            try {
                List<StudentExamVO> exams = StudentExamDAO.INSTANCE.getExams(sno);
                List<StudentExamVO> attendedExams = new ArrayList<>();
                List<StudentExamVO> notAttendedExams = new ArrayList<>();

                for (StudentExamVO exam : exams) {
                    if (exam.isAttended()) {
                        attendedExams.add(exam);
                    } else {
                        notAttendedExams.add(exam);
                    }
                }

                req.setAttribute("attendedExams", attendedExams);
                req.setAttribute("notAttendedExams", notAttendedExams);
                req.getRequestDispatcher("/WEB-INF/student/examList.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new ServletException("Error retrieving exam list", e);
            }
        } else {
            resp.sendRedirect("/student/login?error=notloggedin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String examId = req.getParameter("examId");

        if (action != null && examId != null) {
            HttpSession session = req.getSession(false);

            if (session != null && session.getAttribute("student") != null) {
                if ("viewResult".equals(action)) {
                    resp.sendRedirect("/student/viewResult?examId=" + examId);
                } else if ("takeExam".equals(action)) {
                    // 응시할 시험 번호를 세션에 저장하고 응시 페이지로 리다이렉트
                    session.setAttribute("examNum", Integer.parseInt(examId));
                    resp.sendRedirect("/exam/view");
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                }
            } else {
                resp.sendRedirect("/student/login?error=notloggedin");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
        }
    }
}
