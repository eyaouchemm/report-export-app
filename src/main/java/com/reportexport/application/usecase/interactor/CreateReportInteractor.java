package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.CreateReportCommand;
import com.reportexport.application.dto.ReportDto;
import com.reportexport.application.usecase.port.CreateReportUseCase;
import com.reportexport.domain.model.Report;
import com.reportexport.domain.model.ReportType;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateReportInteractor implements CreateReportUseCase {

    private final ReportRepository reportRepository;

    public CreateReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportDto execute(CreateReportCommand command) {
        ReportType type;
        try {
            type = ReportType.valueOf(command.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid type: " + command.getType() +
                ". Accepted values: FINANCIAL, AUDIT, COMPLIANCE, OPERATIONAL");
        }
        Report saved = reportRepository.save(
            Report.create(command.getTitle(), command.getContent(), type)
        );
        return ReportMapper.toDto(saved);
    }
}