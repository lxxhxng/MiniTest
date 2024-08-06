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
                req.getRequestDispatcher("/WEB-INF/student/examView.jsp").forward(req, resp);
            } catch (Exception e) {
                log.error("Error retrieving exam or quizzes: ", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve exam details.");
            }
        } else {
            resp.sendRedirect("/student/login?error=notloggedin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("student") != null) {
            // 시험 번호 가져오기
            Integer examNum = (Integer) session.getAttribute("examNum");

            if (examNum == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Exam number not found in session.");
                return;
            }

            // 학생 정보 가져오기
            StudentVO studentVO = (StudentVO) session.getAttribute("student");
            Integer studentNum = studentVO.getSno();

            // 폼에서 제출된 답안 받기
            String[] answers = req.getParameterValues("answers");

            // 답안 저장
            try {
                // 정답 체크 및 결과 저장
                boolean isSuccess = ExamDAO.INSTANCE.saveAnswers(studentNum, examNum, answers);
                if (isSuccess) {
                    // 시험 목록 페이지로 리다이렉트
                    resp.sendRedirect(req.getContextPath() + "/student/examList");
                } else {
                    // 오류 페이지로 리다이렉트
                    resp.sendRedirect(req.getContextPath() + "/student/error?message=submit-failed");
                }
            } catch (Exception e) {
                log.error("Error saving answers: ", e);
                resp.sendRedirect(req.getContextPath() + "/student/error?message=server-error");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/student/login?error=notloggedin");
        }
    }

}
