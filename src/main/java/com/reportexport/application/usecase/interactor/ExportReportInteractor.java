package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.ExportResult;
import com.reportexport.application.usecase.port.ExportReportUseCase;
import com.reportexport.application.usecase.port.ReportExporterPort;
import com.reportexport.domain.model.Report;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExportReportInteractor implements ExportReportUseCase {

    private final ReportRepository reportRepository;
    private final Map<String, ReportExporterPort> exporters;

    public ExportReportInteractor(ReportRepository reportRepository,
                                  List<ReportExporterPort> exporterList) {
        this.reportRepository = reportRepository;
        this.exporters = exporterList.stream()
            .collect(Collectors.toMap(
                e -> e.getFormat().toUpperCase(),
                Function.identity()
            ));
    }

    @Override
    public ExportResult exportAll(String format) {
        List<Report> reports = reportRepository.findAllValidated();
        return buildResult(reports, format);
    }

    @Override
    public ExportResult exportById(Long id, String format) {
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found: id=" + id));
        return buildResult(List.of(report), format);
    }

    private ExportResult buildResult(List<Report> reports, String format) {
        String key = format.toUpperCase();
        ReportExporterPort exporter = exporters.get(key);
        if (exporter == null)
            throw new IllegalArgumentException(
                "Unsupported format: " + format + ". Accepted: PDF, CSV");

        reports.forEach(r -> {
            try {
                r.markAsExported(format);
                reportRepository.save(r);
            } catch (IllegalStateException ignored) {}
        });

        String filename    = "reports." + format.toLowerCase();
        String contentType = key.equals("PDF") ? "application/pdf" : "text/csv; charset=UTF-8";
        return new ExportResult(exporter.export(reports), filename, contentType);
    }
}