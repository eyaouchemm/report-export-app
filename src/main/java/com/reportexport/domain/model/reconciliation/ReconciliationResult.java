package com.reportexport.domain.model.reconciliation;

/**
 * Represente le resultat de la comparaison d'un rapport
 * entre notre systeme (DB) et le fichier externe (CSV uploaded).
 */
public class ReconciliationResult {

    private final String reportId;
    private final String title;
    private final ReconciliationStatus status;
    private final String internalValue;
    private final String externalValue;

    private ReconciliationResult(String reportId, String title,
                                  ReconciliationStatus status,
                                  String internalValue, String externalValue) {
        this.reportId      = reportId;
        this.title         = title;
        this.status        = status;
        this.internalValue = internalValue;
        this.externalValue = externalValue;
    }

    public static ReconciliationResult matched(String reportId, String title) {
        return new ReconciliationResult(reportId, title,
            ReconciliationStatus.MATCHED, null, null);
    }

    public static ReconciliationResult missing(String reportId, String title) {
        return new ReconciliationResult(reportId, title,
            ReconciliationStatus.MISSING, title, null);
    }

    public static ReconciliationResult surplus(String reportId, String title) {
        return new ReconciliationResult(reportId, title,
            ReconciliationStatus.SURPLUS, null, title);
    }

    public static ReconciliationResult conflict(String reportId, String title,
                                                 String internalValue, String externalValue) {
        return new ReconciliationResult(reportId, title,
            ReconciliationStatus.CONFLICT, internalValue, externalValue);
    }

    public String getReportId()          { return reportId; }
    public String getTitle()             { return title; }
    public ReconciliationStatus getStatus() { return status; }
    public String getInternalValue()     { return internalValue; }
    public String getExternalValue()     { return externalValue; }
}