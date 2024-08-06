package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentDAO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/student/login")
@Log4j2
public class SLoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/student/sLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String spw = req.getParameter("spw");

        try {
            Optional<StudentVO> result = StudentDAO.INSTANCE.get(sid, spw);
            result.ifPresentOrElse(studentVO -> {
                // 로그인 성공 시 세션에 학생 정보 저장
                HttpSession session = req.getSession();
                session.setAttribute("student", studentVO);

                // 로그인 쿠키 설정
                Cookie loginCookie = new Cookie("student", sid);
                loginCookie.setPath("/");
                loginCookie.setMaxAge(60 * 60 * 24); // 1 day
                resp.addCookie(loginCookie);

                try {
                    resp.sendRedirect("/student/examList");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, () -> {
                try {
                    resp.sendRedirect("/student/login?error=invalid");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}