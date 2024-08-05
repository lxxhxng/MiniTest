package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(value = "/teacher/examList")
@Log4j2
public class ExamListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("teacher") == null) {
            resp.sendRedirect("/teacher/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/teacher/examList.jsp").forward(req, resp);

    }
}
