package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.example.miniproject_5.dao.StudentAnswerDAO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/check/answer")
@Slf4j
public class CheckAnswerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String examno = req.getParameter("examno");
        String qnoStr = req.getParameter("qno");
        String answer = req.getParameter("answer");

        HttpSession session = req.getSession();
        StudentVO studentVO = (StudentVO) session.getAttribute("student");

        if (studentVO == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Student not logged in.");
            return;
        }

        Integer sno = studentVO.getSno();
        int qno = Integer.parseInt(qnoStr);
        boolean isCorrect = false;

        try {
            int checkedNum = Integer.parseInt(answer);
            isCorrect = StudentAnswerDAO.INSTANCE.isAnswerCorrect(qno, checkedNum, Integer.parseInt(examno));

            // 저장할 답안을 쿠키에서 가져오기
            Cookie answerCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("answer"))
                    .findFirst()
                    .orElse(new Cookie("answer", ""));
            String[] answers = answerCookie.getValue().split("&");
            answers = Arrays.stream(answers)
                    .filter(str -> !str.startsWith(qno + ":")) // 기존 값을 제거
                    .toArray(String[]::new);

            // 새로운 답안 추가
            String newAnswer = qno + ":" + answer;
            String cookieValue = String.join("&", answers) + (answers.length > 0 ? "&" : "") + newAnswer;

            answerCookie.setValue(cookieValue);
            answerCookie.setPath("/");
            answerCookie.setMaxAge(60 * 60 * 24);
            resp.addCookie(answerCookie);

        } catch (SQLException e) {
            log.error("Error checking answer: ", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error checking answer.");
            return;
        }

        // 시험 결과 페이지로 리다이렉트
        resp.sendRedirect("/exam/answerSheet?count=" + req.getParameter("count"));
    }
}
