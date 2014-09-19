package com.web;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by R500 on 18.8.2014 г..
 */
public class PDFGenerator {

//    generateVacationRequestPDF is called when a vacation request is approved by a manager
//    the method generates a pdf file with data given to it
//    the pdf file is sent to someone via email who can print the document...
    public static void generateVacationRequestPDF(String employeeName, String vacationDaysLeft, String beginDate, String endDate, String allDays, String vacationType){
        Document document = new Document();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("src/main/webapp/documents/VacationRequest.pdf"));

            document.open();

            Image logo = Image.getInstance("src/main/webapp/images/logo.png");
            logo.setAlignment(Element.ALIGN_RIGHT);

            BaseFont baseFont = BaseFont.createFont("src/main/webapp/documents/font/calibri.ttf" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font header = new Font(baseFont, 18);
            Font tableText = new Font(baseFont, 11);
            Font tableTextBold = new Font(baseFont, 11, Font.BOLD);
            Paragraph paragraph = new Paragraph("МОЛБА ЗА ОТПУСК", header);
            paragraph.setSpacingBefore(40f);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(2);
            table.setSpacingBefore(50f);
            float[] columnWidths = {450f, 600f};
            table.getTotalWidth();
            table.setWidths(columnWidths);
            PdfPCell defaultCell = table.getDefaultCell();
            defaultCell.setMinimumHeight(50f);
            defaultCell.setIndent(5f);
            defaultCell.setFollowingIndent(5f);
            defaultCell.setRightIndent(5f);
            defaultCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(new Paragraph("От /трите имена/", tableText));
            table.addCell(new Paragraph(employeeName, tableText));
            table.addCell(new Paragraph("До", tableText));
            table.addCell(new Paragraph("Новарто ООД", tableText));
            table.addCell(new Paragraph("Оставащи дни от год. отпуска преди молбата", tableText));
            table.addCell(new Paragraph(vacationDaysLeft, tableText));
            Paragraph paragraph1 = new Paragraph();
            Chunk chunk1 = new Chunk("Моля да ми бъде разрешена отпуска     ", tableText);
            Chunk chunk2 = new Chunk("от:", tableTextBold);
            paragraph1.add(chunk1);
            paragraph1.add(chunk2);
            table.addCell(paragraph1);
            table.addCell(new Paragraph(beginDate, tableTextBold));
            table.addCell(new Paragraph("                   до:", tableTextBold));
            table.addCell(new Paragraph(endDate, tableTextBold));
            table.addCell(new Paragraph("Всичко дни", tableText));
            table.addCell(new Paragraph(allDays, tableText));
            table.addCell(new Paragraph("Основание за отпуската", tableText));
            table.addCell(new Paragraph(vacationType, tableText));
            table.addCell(new Paragraph("Разрешено от /подпис/", tableText));
            table.addCell(new Paragraph("", tableText));
            table.addCell(new Paragraph("Дата", tableText));
            table.addCell(new Paragraph(today, tableText));
            table.addCell(new Paragraph("Подпис на служителя", tableText));
            table.addCell(new Paragraph("", tableText));

            document.add(logo);
            document.add(paragraph);
            document.add(table);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
