package org.zerock.demo1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet("/check/answer")
@Log4j2
public class CheckAnswerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 모든 답안 정보를 가져옴
        Map<String, String[]> parameterMap = req.getParameterMap();

        // 답안 배열 생성 (질문 수에 맞게 조절해야 함)
        String[] answers = new String[parameterMap.size()];
        Arrays.fill(answers, "0"); // 기본값으로 0으로 초기화

        // 폼 파라미터에서 답안 정보 추출
        parameterMap.forEach((key, value) -> {
            if (key.startsWith("answers[")) {
                int qno = Integer.parseInt(key.substring("answers[".length(), key.length() - 1));
                if (qno <= answers.length) {
                    answers[qno - 1] = value[0];
                }
            }
        });

        // 답안을 쿠키에 저장
        String cookieValue = String.join("&", IntStream.range(0, answers.length)
                .mapToObj(i -> (i + 1) + ":" + answers[i])
                .collect(Collectors.toList()));

        Cookie answerCookie = new Cookie("answer", cookieValue);
        answerCookie.setPath("/");
        answerCookie.setMaxAge(60 * 60 * 24);

        resp.addCookie(answerCookie);

        // 제출 후 결과 페이지로 리다이렉트
        resp.sendRedirect("/exam/answerSheet");
    }
}
