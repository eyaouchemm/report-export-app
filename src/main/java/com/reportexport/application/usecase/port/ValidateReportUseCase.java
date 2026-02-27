package com.reportexport.application.usecase.port;

import com.reportexport.application.dto.ReportDto;

public interface ValidateReportUseCase {
    ReportDto execute(Long reportId);
}