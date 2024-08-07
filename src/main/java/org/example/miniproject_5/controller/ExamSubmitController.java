package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentAnswerDAO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/exam/submit")
@Log4j2
public class ExamSubmitController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        StudentVO studentVO = (StudentVO) session.getAttribute("student");

        if (studentVO == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Student not logged in.");
            return;
        }

        Integer sno = studentVO.getSno();
        Integer examNum = Integer.parseInt(req.getParameter("examNum"));

        // 모든 답안 가져오기
        Map<String, String[]> parameterMap = req.getParameterMap();

        try {
            // 각 문제에 대해 답안을 데이터베이스에 저장
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                if (entry.getKey().startsWith("answers[")) {
                    int qno = Integer.parseInt(entry.getKey().replaceAll("[^0-9]", ""));
                    String answer = entry.getValue()[0];
                    int checkedNum = Integer.parseInt(answer);
                    boolean isCorrect = StudentAnswerDAO.INSTANCE.isAnswerCorrect(qno, checkedNum, examNum);
                    StudentAnswerDAO.INSTANCE.saveStudentAnswer(sno, qno, checkedNum, isCorrect, examNum);
                }
            }

            // 점수 계산
            int score = StudentAnswerDAO.INSTANCE.calculateScore(sno, examNum);

            // tbl_result에 점수 저장
            StudentAnswerDAO.INSTANCE.saveResult(sno, examNum, score);

            // 시험 결과 페이지로 리다이렉트
            resp.sendRedirect("/exam/answerSheet?count=" + parameterMap.size() + "&score=" + score);
        } catch (SQLException e) {
            log.error("Error saving student answers or calculating score: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing exam submission.");
        }
    }
}
