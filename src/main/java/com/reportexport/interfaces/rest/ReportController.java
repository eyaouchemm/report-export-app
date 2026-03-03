package com.reportexport.interfaces.rest;

import com.reportexport.application.dto.CreateReportCommand;
import com.reportexport.application.dto.ExportResult;
import com.reportexport.application.dto.ReportDto;
import com.reportexport.application.usecase.port.CreateReportUseCase;
import com.reportexport.application.usecase.port.ExportReportUseCase;
import com.reportexport.application.usecase.port.GetReportUseCase;
import com.reportexport.application.usecase.port.ValidateReportUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final CreateReportUseCase   createReport;
    private final GetReportUseCase      getReport;
    private final ValidateReportUseCase validateReport;
    private final ExportReportUseCase   exportReport;

    public ReportController(CreateReportUseCase   createReport,
                            GetReportUseCase      getReport,
                            ValidateReportUseCase validateReport,
                            ExportReportUseCase   exportReport) {
        this.createReport   = createReport;
        this.getReport      = getReport;
        this.validateReport = validateReport;
        this.exportReport   = exportReport;
    }

    @PostMapping
    public ResponseEntity<ReportDto> create(@Valid @RequestBody CreateReportCommand cmd) {
        return ResponseEntity.ok(createReport.execute(cmd));
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> findAll() {
        return ResponseEntity.ok(getReport.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(getReport.findById(id));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<ReportDto> validate(@PathVariable Long id) {
        return ResponseEntity.ok(validateReport.execute(id));
    }

    @GetMapping("/export/{format}")
    public ResponseEntity<byte[]> exportAll(@PathVariable String format) {
        return toFileResponse(exportReport.exportAll(format));
    }

    @GetMapping("/{id}/export/{format}")
    public ResponseEntity<byte[]> exportById(@PathVariable Long id,
                                             @PathVariable String format) {
        return toFileResponse(exportReport.exportById(id, format));
    }

    private ResponseEntity<byte[]> toFileResponse(ExportResult result) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + result.getFilename())
            .contentType(MediaType.parseMediaType(result.getContentType()))
            .body(result.getData());
    }
}