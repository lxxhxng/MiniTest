package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentExamDAO;
import org.example.miniproject_5.vo.StudentResultVO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/viewResult")
@Log4j2
public class StudentResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String examIdParam = req.getParameter("examId");

        if (examIdParam != null) {
            HttpSession session = req.getSession(false);

            if (session != null && session.getAttribute("student") != null) {
                Integer examId;
                try {
                    examId = Integer.parseInt(examIdParam);
                } catch (NumberFormatException e) {
                    log.error("Invalid examId format: " + examIdParam, e);
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid examId format.");
                    return;
                }

                Integer sno = ((StudentVO) session.getAttribute("student")).getSno();

                try {
                    List<StudentResultVO> results = StudentExamDAO.INSTANCE.getStudentResult(sno, examId);
                    req.setAttribute("results", results);
                    req.getRequestDispatcher("/WEB-INF/student/examResult.jsp").forward(req, resp);
                } catch (Exception e) {
                    log.error("Error retrieving student results", e);
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving student results.");
                }
            } else {
                resp.sendRedirect("/student/login?error=notloggedin");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing examId parameter.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/student/examList");
    }
}
