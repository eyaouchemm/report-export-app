package com.reportexport.infrastructure.export;

import com.opencsv.CSVWriter;
import com.reportexport.application.usecase.port.ReportExporterPort;
import com.reportexport.domain.model.Report;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvReportExporterAdapter implements ReportExporterPort {

    @Override
    public String getFormat() { return "CSV"; }

    @Override
    public byte[] export(List<Report> reports) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(
                 new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

            writer.writeNext(new String[]{
                "ID", "Title", "Type", "Status", "Created at", "Updated at"
            });

            for (Report r : reports) {
                writer.writeNext(new String[]{
                    String.valueOf(r.getId()),
                    r.getTitle(),
                    r.getType().name(),
                    r.getStatus().name(),
                    r.getCreatedAt().toString(),
                    r.getUpdatedAt().toString()
                });
            }

            writer.flush();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("CSV generation failed", e);
        }
    }
}