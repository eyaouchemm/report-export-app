package com.reportexport.application.usecase.port;

import com.reportexport.application.dto.ExportResult;

public interface ExportReportUseCase {
    ExportResult exportAll(String format);
    ExportResult exportById(Long id, String format);
}