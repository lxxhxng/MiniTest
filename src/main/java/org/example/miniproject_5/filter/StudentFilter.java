package org.example.miniproject_5.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebFilter(urlPatterns = {"/student/examList","/student/viewResult", "/student/examList","/check/answer"})
@Log4j2
public class StudentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        // 세션이 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect("/student/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
