package com.reportexport.domain.model.event;

import java.time.LocalDateTime;

public final class ReportValidatedEvent {

    private final Long reportId;
    private final LocalDateTime occurredAt;

    public ReportValidatedEvent(Long reportId) {
        this.reportId   = reportId;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getReportId()            { return reportId; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}