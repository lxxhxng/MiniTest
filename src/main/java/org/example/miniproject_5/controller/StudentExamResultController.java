package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentAnswerDAO;
import org.example.miniproject_5.vo.StudentAnswerVO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = "/exam/result")
@Log4j2
public class StudentExamResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        StudentVO studentVO = (StudentVO) session.getAttribute("student");

        if (studentVO == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Student not logged in.");
            return;
        }

        Integer sno = studentVO.getSno();
        Integer examNum = Integer.parseInt(req.getParameter("examNum"));

        try {
            // 학생의 답안 목록 조회
            List<StudentAnswerVO> answerList = StudentAnswerDAO.INSTANCE.getStudentAnswers(sno, examNum);
            int score = StudentAnswerDAO.INSTANCE.calculateScore(sno, examNum);

            req.setAttribute("answerList", answerList);
            req.setAttribute("score", score);

            // 결과 페이지로 포워드
            req.getRequestDispatcher("/WEB-INF/student/result.jsp").forward(req, resp);
        } catch (SQLException e) {
            log.error("Error retrieving student answers or calculating score: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving exam result.");
        }
    }
}