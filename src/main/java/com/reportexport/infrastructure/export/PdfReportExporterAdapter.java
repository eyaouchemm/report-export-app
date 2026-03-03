package com.reportexport.infrastructure.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.reportexport.application.usecase.port.ReportExporterPort;
import com.reportexport.domain.model.Report;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfReportExporterAdapter implements ReportExporterPort {

    @Override
    public String getFormat() { return "PDF"; }

    @Override
    public byte[] export(List<Report> reports) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph heading = new Paragraph("Reports", titleFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            heading.setSpacingAfter(20);
            doc.add(heading);

            if (reports.isEmpty()) {
                doc.add(new Paragraph("No reports available."));
                doc.close();
                return out.toByteArray();
            }

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3f, 2f, 2f, 3f});
            table.setSpacingBefore(10);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(63, 81, 181);
            for (String h : new String[]{"ID", "Title", "Type", "Status", "Created at"}) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setPadding(8);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            Font rowFont = new Font(Font.FontFamily.HELVETICA, 9);
            boolean alt = false;
            for (Report r : reports) {
                BaseColor bg = alt ? new BaseColor(240, 244, 255) : BaseColor.WHITE;
                for (String v : new String[]{
                    String.valueOf(r.getId()),
                    r.getTitle(),
                    r.getType().name(),
                    r.getStatus().name(),
                    r.getCreatedAt().toString()
                }) {
                    PdfPCell cell = new PdfPCell(new Phrase(v, rowFont));
                    cell.setBackgroundColor(bg);
                    cell.setPadding(6);
                    table.addCell(cell);
                }
                alt = !alt;
            }

            doc.add(table);
            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}