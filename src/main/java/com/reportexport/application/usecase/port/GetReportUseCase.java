package com.reportexport.application.usecase.port;

import com.reportexport.application.dto.ReportDto;

import java.util.List;

public interface GetReportUseCase {
    List<ReportDto> findAll();
    ReportDto findById(Long id);
}