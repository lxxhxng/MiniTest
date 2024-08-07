package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.util.CookieUtil;

import java.io.IOException;
import java.util.Map;

@WebServlet("/exam/answerSheet")
@Log4j2
public class AnswerSheetController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<Integer,String> answerMap = CookieUtil.parseStr(req);
        req.setAttribute("answerMap", answerMap);
        req.getRequestDispatcher("/WEB-INF/student/answerSheet.jsp").forward(req, resp);
    }
}