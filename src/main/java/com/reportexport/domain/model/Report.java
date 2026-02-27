package com.reportexport.domain.model;

import com.reportexport.domain.model.event.ReportValidatedEvent;
import com.reportexport.domain.model.valueobject.ReportContent;
import com.reportexport.domain.model.valueobject.ReportTitle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Report {

    private Long id;
    private ReportTitle title;
    private ReportContent content;
    private ReportType type;
    private ReportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private final List<Object> domainEvents = new ArrayList<>();

    private Report() {}

    public static Report create(String title, String content, ReportType type) {
        Report r    = new Report();
        r.title     = new ReportTitle(title);
        r.content   = new ReportContent(content);
        r.type      = type;
        r.status    = ReportStatus.PENDING;
        r.createdAt = LocalDateTime.now();
        r.updatedAt = LocalDateTime.now();
        return r;
    }

    public static Report reconstitute(Long id, String title, String content,
                                      ReportType type, ReportStatus status,
                                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        Report r    = new Report();
        r.id        = id;
        r.title     = new ReportTitle(title);
        r.content   = new ReportContent(content);
        r.type      = type;
        r.status    = status;
        r.createdAt = createdAt;
        r.updatedAt = updatedAt;
        return r;
    }

    public void validate() {
        if (status != ReportStatus.PENDING)
            throw new IllegalStateException(
                "Cannot validate report with status: " + status);
        this.status    = ReportStatus.VALIDATED;
        this.updatedAt = LocalDateTime.now();
        domainEvents.add(new ReportValidatedEvent(this.id));
    }

    public List<Object> getDomainEvents()  { return Collections.unmodifiableList(domainEvents); }
    public void clearDomainEvents()        { domainEvents.clear(); }

    public Long getId()                 { return id; }
    public void setId(Long id)          { this.id = id; }
    public String getTitle()            { return title.getValue(); }
    public String getContent()          { return content.getValue(); }
    public ReportType getType()         { return type; }
    public ReportStatus getStatus()     { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}