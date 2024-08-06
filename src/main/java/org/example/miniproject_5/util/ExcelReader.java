package org.example.miniproject_5.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.miniproject_5.dao.TeacherDAO;
import org.example.miniproject_5.vo.QuizVO;
import org.example.miniproject_5.vo.TeacherVO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Log4j2
public class ExcelReader {

    public static List<QuizVO> readInputStream(InputStream in) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(in);

//Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

//Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();


        log.info(rowIterator);

        //1번행은 제목이므로 skip
        rowIterator.next();

        List<QuizVO> quizVOList = new ArrayList<>();


        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getCell(0) == null) {
                break;
            }

            int idx = 0;

            QuizVO vo = QuizVO.builder()
                    .qno((int) (int) row.getCell(idx++).getNumericCellValue())
                    .question(row.getCell(idx++).getStringCellValue())
                    .op1(row.getCell(idx++).getStringCellValue())
                    .op2(row.getCell(idx++).getStringCellValue())
                    .op3(row.getCell(idx++).getStringCellValue())
                    .op4(row.getCell(idx++).getStringCellValue())
                    .op5(row.getCell(idx++).getStringCellValue())
                    .answer((int) (row.getCell(idx++).getNumericCellValue()))
                    .build();

            log.info("---------------------");
            log.info(vo);

            quizVOList.add(vo);
        }


        return quizVOList;

    }


}