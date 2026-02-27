package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.ReportDto;
import com.reportexport.application.usecase.port.ValidateReportUseCase;
import com.reportexport.domain.model.Report;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateReportInteractor implements ValidateReportUseCase {

    private final ReportRepository reportRepository;

    public ValidateReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportDto execute(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("Report not found: id=" + reportId));
        report.validate();
        return ReportMapper.toDto(reportRepository.save(report));
    }
}