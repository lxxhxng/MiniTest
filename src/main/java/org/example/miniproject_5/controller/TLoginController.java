package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.TeacherDAO;
import org.example.miniproject_5.vo.TeacherVO;

import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/teacher/login")
@Log4j2
public class TLoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/teacher/tLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("tid");
        String tpw = req.getParameter("tpw");

        try {
            Optional<TeacherVO> result = TeacherDAO.INSTANCE.get(tid, tpw);
            result.ifPresentOrElse(teacherVO -> {
                // 로그인 성공 시 세션에 강사 정보 저장
                HttpSession session = req.getSession();
                session.setAttribute("teacher", teacherVO);

                // 로그인 쿠키 설정
                Cookie loginCookie = new Cookie("teacher", tid);
                loginCookie.setPath("/");
                loginCookie.setMaxAge(60 * 60 * 24); // 1 day
                resp.addCookie(loginCookie);

                try {
                    resp.sendRedirect("/teacher/examList");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, () -> {
                try {
                    resp.sendRedirect("/teacher/login?error=invalid");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}