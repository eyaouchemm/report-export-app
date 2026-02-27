package com.reportexport.application.dto;

import java.time.LocalDateTime;

public class ReportDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String type;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReportDto(Long id, String title, String content,
                     String type, String status,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id        = id;
        this.title     = title;
        this.content   = content;
        this.type      = type;
        this.status    = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId()                 { return id; }
    public String getTitle()            { return title; }
    public String getContent()          { return content; }
    public String getType()             { return type; }
    public String getStatus()           { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}