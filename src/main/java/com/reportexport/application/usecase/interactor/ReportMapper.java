package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.ReportDto;
import com.reportexport.domain.model.Report;

class ReportMapper {

    private ReportMapper() {}

    static ReportDto toDto(Report r) {
        return new ReportDto(
            r.getId(),
            r.getTitle(),
            r.getContent(),
            r.getType().name(),
            r.getStatus().name(),
            r.getCreatedAt(),
            r.getUpdatedAt()
        );
    }
}