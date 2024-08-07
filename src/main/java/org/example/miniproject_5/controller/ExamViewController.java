package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.dao.QuizDAO;
import org.example.miniproject_5.vo.ExamVO;
import org.example.miniproject_5.vo.QuizVO;
import org.example.miniproject_5.vo.StudentVO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(value = "/exam/view")
@Log4j2
public class ExamViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("examNum") != null) {
            Integer examNum = (Integer) session.getAttribute("examNum");
            StudentVO studentVO = (StudentVO) session.getAttribute("student");
            Integer studentNum = studentVO.getSno();

            try {
                // 시험 정보 조회
                Optional<ExamVO> examOpt = ExamDAO.INSTANCE.getExamById(examNum);
                if (examOpt.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Exam not found.");
                    return;
                }
                ExamVO examVO = examOpt.get();

                // 시험 문제 조회
                List<QuizVO> quizList = QuizDAO.INSTANCE.getQuizzesByExamNum(examNum);

                // JSP 페이지에 전달
                req.setAttribute("exam", examVO);
                req.setAttribute("quizzes", quizList);

                // 초기답안 (문제번호:0) 형식으로 쿠키를 설정
                String initialAnswers = quizList.stream()
                        .map(quiz -> quiz.getQno() + ":0")
                        .collect(Collectors.joining("&"));

                Cookie answerCookie = new Cookie("answer", initialAnswers);
                answerCookie.setPath("/");
                answerCookie.setMaxAge(60 * 60 * 24);

                resp.addCookie(answerCookie);

                req.getRequestDispatcher("/WEB-INF/student/examView.jsp").forward(req, resp);
            } catch (Exception e) {
                log.error("Error retrieving exam or quizzes: ", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve exam details.");
            }
        } else {
            resp.sendRedirect("/student/login?error=notloggedin");
        }
    }

}
