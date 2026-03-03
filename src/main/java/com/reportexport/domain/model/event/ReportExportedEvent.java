package com.reportexport.domain.model.event;

import java.time.LocalDateTime;

public final class ReportExportedEvent {

    private final Long reportId;
    private final String format;
    private final LocalDateTime occurredAt;

    public ReportExportedEvent(Long reportId, String format) {
        this.reportId   = reportId;
        this.format     = format;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getReportId()            { return reportId; }
    public String getFormat()            { return format; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}