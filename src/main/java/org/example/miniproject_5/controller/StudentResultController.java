package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentExamDAO;
import org.example.miniproject_5.vo.StudentResultVO;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/studentResult")
@Log4j2
public class StudentResultController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String snoParam = req.getParameter("sno");
        String qnoParam = req.getParameter("qno");

        Integer sno = null;
        Integer qno = null;

        // 파라미터가 null이 아니고, 정수로 파싱 가능한 경우에만 파싱
        if (snoParam != null && !snoParam.isEmpty()) {
            try {
                sno = Integer.parseInt(snoParam);
            } catch (NumberFormatException e) {
                log.error("Invalid student number format: " + snoParam, e);
            }
        }

        if (qnoParam != null && !qnoParam.isEmpty()) {
            try {
                qno = Integer.parseInt(qnoParam);
            } catch (NumberFormatException e) {
                log.error("Invalid question number format: " + qnoParam, e);
            }
        }

        if (sno == null || qno == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameters.");
            return;
        }

        try {

            List<StudentResultVO> results = StudentExamDAO.INSTANCE.getStudentResult(sno, qno);
            req.setAttribute("results", results);
            req.getRequestDispatcher("/WEB-INF/student/examResult.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error retrieving student results", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving student results.");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // POST 요청 처리 (예: 데이터 삽입, 업데이트 등)
        // 요청 파라미터에서 필요한 데이터 추출
        Integer sno = Integer.parseInt(req.getParameter("sno"));
        Integer qno = Integer.parseInt(req.getParameter("qno"));

        // 예를 들어, GET 요청과 동일하게 처리할 수 있습니다.
        // 데이터 생성/업데이트 처리를 원하면 해당 로직을 추가하세요.
        resp.sendRedirect(req.getContextPath() + "/student/examResult?sno=" + sno + "&qno=" + qno);
    }
}
