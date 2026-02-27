package com.reportexport.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getTitle()                  { return title; }
    public void setTitle(String v)            { this.title = v; }
    public String getContent()                { return content; }
    public void setContent(String v)          { this.content = v; }
    public String getType()                   { return type; }
    public void setType(String v)             { this.type = v; }
    public String getStatus()                 { return status; }
    public void setStatus(String v)           { this.status = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
}