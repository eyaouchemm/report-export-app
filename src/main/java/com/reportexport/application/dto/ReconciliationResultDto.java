package com.reportexport.application.dto;

public class ReconciliationResultDto {

    private final String reportId;
    private final String title;
    private final String status;
    private final String internalValue;
    private final String externalValue;

    public ReconciliationResultDto(String reportId, String title,
                                    String status,
                                    String internalValue, String externalValue) {
        this.reportId      = reportId;
        this.title         = title;
        this.status        = status;
        this.internalValue = internalValue;
        this.externalValue = externalValue;
    }

    public String getReportId()      { return reportId; }
    public String getTitle()         { return title; }
    public String getStatus()        { return status; }
    public String getInternalValue() { return internalValue; }
    public String getExternalValue() { return externalValue; }
}