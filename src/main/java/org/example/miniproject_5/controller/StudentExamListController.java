package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentExamDAO;
import org.example.miniproject_5.vo.StudentExamVO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/student/examList")
@Log4j2
public class StudentExamListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect("/student/login");
            return;
        }

        Integer sno = ((StudentVO) session.getAttribute("student")).getSno();

        try {
            List<StudentExamVO> exams = StudentExamDAO.INSTANCE.getExams(sno);

            // 응시한 시험과 미응시 시험을 분리
            List<StudentExamVO> attendedExams = exams.stream()
                    .filter(StudentExamVO::isAttended)
                    .toList();
            List<StudentExamVO> notAttendedExams = exams.stream()
                    .filter(exam -> !exam.isAttended())
                    .toList();

            req.setAttribute("attendedExams", attendedExams);
            req.setAttribute("notAttendedExams", notAttendedExams);

            req.getRequestDispatcher("/WEB-INF/student/examList.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error fetching exams", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
