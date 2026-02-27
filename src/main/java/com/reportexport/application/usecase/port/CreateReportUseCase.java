package com.reportexport.application.usecase.port;

import com.reportexport.application.dto.CreateReportCommand;
import com.reportexport.application.dto.ReportDto;

public interface CreateReportUseCase {
    ReportDto execute(CreateReportCommand command);
}