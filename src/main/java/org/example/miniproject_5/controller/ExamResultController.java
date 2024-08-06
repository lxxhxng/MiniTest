package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/examResult")
public class ExamResultController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청에서 시험 번호를 가져옴
        String examNo = request.getParameter("eno");

        // 시험 번호에 대한 로직을 처리 (예: 데이터베이스에서 결과 가져오기)

        // 결과를 request에 설정
        request.setAttribute("examNo", examNo);
        // 필요한 다른 데이터도 설정

        // 결과 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/examResult.jsp").forward(request, response);
    }
}
