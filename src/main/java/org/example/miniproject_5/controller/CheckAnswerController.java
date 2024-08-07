package org.example.miniproject_5.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.StudentAnswerDAO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/check/answer")
@Log4j2
public class CheckAnswerController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String examno = req.getParameter("examno");
        String qnoStr = req.getParameter("qno");
        String answer = req.getParameter("answer");

        HttpSession session = req.getSession();
        StudentVO studentVO = (StudentVO) session.getAttribute("student");

        // 세션에서 학생 번호를 가져오기
        Integer sno = studentVO.getSno();

        Cookie answerCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("answer"))
                .findFirst().orElse(null);

        String[] answers = answerCookie.getValue().split("&");
        int qno = Integer.parseInt(qnoStr);

        // 문제의 답안 정답 여부 확인
        boolean isCorrect = false;
        try {
            int checkedNum = Integer.parseInt(answer);
            isCorrect = StudentAnswerDAO.INSTANCE.isAnswerCorrect(qno, checkedNum, Integer.parseInt(examno));
            log.info("isCorrect: " + isCorrect);
            answers[qno - 1] = qno + ":" + answer;
            String cookieValue = String.join("&", answers);

            Cookie answerCookie2 = new Cookie("answer", cookieValue);
            answerCookie2.setPath("/");
            answerCookie2.setMaxAge(60 * 60 * 24);

            resp.addCookie(answerCookie2);
        } catch (SQLException e) {
            log.error("Error checking answer: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error checking answer.");
            return;
        }

        // 학생의 답안을 DB에 저장
        try {
            StudentAnswerDAO.INSTANCE.saveStudentAnswer(sno, qno, Integer.parseInt(answer), isCorrect, Integer.parseInt(examno));
        } catch (SQLException e) {
            log.error("Error saving student answer: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving answer.");
            return;
        }

        // 시험 결과 페이지로 리다이렉트
        resp.sendRedirect("/exam/answerSheet?count=" + req.getParameter("count"));
    }
}