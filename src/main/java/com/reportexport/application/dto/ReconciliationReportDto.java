package com.reportexport.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReconciliationReportDto {

    private final LocalDateTime reconciledAt;
    private final boolean clean;
    private final long matched;
    private final long missing;
    private final long surplus;
    private final long conflicts;
    private final List<ReconciliationResultDto> details;

    public ReconciliationReportDto(LocalDateTime reconciledAt, boolean clean,
                                    long matched, long missing,
                                    long surplus, long conflicts,
                                    List<ReconciliationResultDto> details) {
        this.reconciledAt = reconciledAt;
        this.clean        = clean;
        this.matched      = matched;
        this.missing      = missing;
        this.surplus      = surplus;
        this.conflicts    = conflicts;
        this.details      = details;
    }

    public LocalDateTime getReconciledAt() { return reconciledAt; }
    public boolean isClean()               { return clean; }
    public long getMatched()               { return matched; }
    public long getMissing()               { return missing; }
    public long getSurplus()               { return surplus; }
    public long getConflicts()             { return conflicts; }
    public List<ReconciliationResultDto> getDetails() { return details; }
}