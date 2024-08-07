package org.example.miniproject_5.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.miniproject_5.dao.ExamDAO;
import org.example.miniproject_5.vo.ResultDetailVO;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/resultDetail")
@Log4j2
public class ResultDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String enoStr = req.getParameter("param1");
        String snoStr = req.getParameter("param2");

        Integer eno = Integer.parseInt(enoStr);
        Integer sno = Integer.parseInt(snoStr);

        try {
            List<ResultDetailVO> resultDetail = ExamDAO.INSTANCE.getResultDetail(eno,sno);
            req.setAttribute("resultDetail", resultDetail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        req.getRequestDispatcher("/WEB-INF/resultDetail.jsp").forward(req, resp);
    }
}
