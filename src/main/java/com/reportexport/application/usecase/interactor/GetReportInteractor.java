package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.ReportDto;
import com.reportexport.application.usecase.port.GetReportUseCase;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetReportInteractor implements GetReportUseCase {

    private final ReportRepository reportRepository;

    public GetReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportDto> findAll() {
        return reportRepository.findAll()
            .stream()
            .map(ReportMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public ReportDto findById(Long id) {
        return reportRepository.findById(id)
            .map(ReportMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Report not found: id=" + id));
    }
}