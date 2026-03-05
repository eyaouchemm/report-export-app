package com.reportexport.domain.model.reconciliation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aggregate : resume complet d'une operation de reconciliation.
 * Contient tous les resultats et les statistiques.
 */
public class ReconciliationSummary {

    private final LocalDateTime reconciledAt;
    private final List<ReconciliationResult> results;

    public ReconciliationSummary(List<ReconciliationResult> results) {
        this.results       = results;
        this.reconciledAt  = LocalDateTime.now();
    }

    public long countMatched() {
        return count(ReconciliationStatus.MATCHED);
    }

    public long countMissing() {
        return count(ReconciliationStatus.MISSING);
    }

    public long countSurplus() {
        return count(ReconciliationStatus.SURPLUS);
    }

    public long countConflicts() {
        return count(ReconciliationStatus.CONFLICT);
    }

    public boolean isClean() {
        return countMissing() == 0 && countSurplus() == 0 && countConflicts() == 0;
    }

    private long count(ReconciliationStatus status) {
        return results.stream()
            .filter(r -> r.getStatus() == status)
            .count();
    }

    public List<ReconciliationResult> getResults()  { return results; }
    public LocalDateTime getReconciledAt()           { return reconciledAt; }

    public List<ReconciliationResult> getMissing() {
        return filter(ReconciliationStatus.MISSING);
    }

    public List<ReconciliationResult> getSurplus() {
        return filter(ReconciliationStatus.SURPLUS);
    }

    public List<ReconciliationResult> getConflicts() {
        return filter(ReconciliationStatus.CONFLICT);
    }

    private List<ReconciliationResult> filter(ReconciliationStatus status) {
        return results.stream()
            .filter(r -> r.getStatus() == status)
            .collect(Collectors.toList());
    }
}