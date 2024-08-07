package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.vo.ResultVO;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/examResult")
@Log4j2
public class ExamResultController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청에서 시험 번호를 가져옴
        String examNoStr = req.getParameter("eno");

        if (examNoStr != null) {
            try {
                int examNo = Integer.parseInt(examNoStr);

                // 데이터베이스에서 결과 목록을 가져옴
                List<ResultVO> resultList = ExamDAO.INSTANCE.getResult(examNo);

                if (resultList != null && !resultList.isEmpty()) {
                    // 결과 목록을 request에 설정
                    req.setAttribute("examNo", examNo);
                    req.setAttribute("resultList", resultList);
                } else {
                    req.setAttribute("error", "No results found for exam number: " + examNo);
                }

                // 결과 페이지로 포워딩
                req.getRequestDispatcher("/WEB-INF/examResult.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                // 시험 번호가 정수가 아닌 경우
                req.setAttribute("error", "Invalid exam number format.");
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            } catch (Exception e) {
                // 기타 예외 처리
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            }
        } else {
            // 시험 번호가 제공되지 않은 경우
            req.setAttribute("error", "Exam number is missing.");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("post");
        Integer eno = Integer.valueOf(req.getParameter("examNo"));
        Integer sno = Integer.valueOf(req.getParameter("studentNo"));
        log.info("eno: " + eno);
        log.info("sno: " + sno);
        resp.sendRedirect("/resultDetail?param1="+eno+"&param2="+sno);
        //resp.sendRedirect(req.getContextPath()
    }
}
