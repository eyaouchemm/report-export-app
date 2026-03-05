package com.reportexport.application.usecase.interactor;

import com.reportexport.application.dto.ReconciliationReportDto;
import com.reportexport.application.dto.ReconciliationResultDto;
import com.reportexport.application.usecase.port.CsvParserPort;
import com.reportexport.application.usecase.port.ReconcileReportsUseCase;
import com.reportexport.domain.model.Report;
import com.reportexport.domain.model.reconciliation.ReconciliationResult;
import com.reportexport.domain.model.reconciliation.ReconciliationSummary;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReconcileReportsInteractor implements ReconcileReportsUseCase {

    private final ReportRepository reportRepository;
    private final CsvParserPort    csvParser;

    public ReconcileReportsInteractor(ReportRepository reportRepository,
                                      CsvParserPort csvParser) {
        this.reportRepository = reportRepository;
        this.csvParser        = csvParser;
    }

    @Override
    public ReconciliationReportDto reconcile(byte[] externalCsvContent) {

        // 1. Charger nos rapports depuis la DB
        Map<String, Report> internal = reportRepository.findAll()
            .stream()
            .collect(Collectors.toMap(
                r -> String.valueOf(r.getId()),
                r -> r
            ));

        // 2. Parser le CSV externe uploade
        Map<String, String> external = csvParser.parse(externalCsvContent);

        // 3. Comparer les deux
        List<ReconciliationResult> results = new ArrayList<>();

        // Parcourir notre DB
        for (Map.Entry<String, Report> entry : internal.entrySet()) {
            String id       = entry.getKey();
            Report report   = entry.getValue();

            if (!external.containsKey(id)) {
                // Rapport present en DB mais absent du CSV externe
                results.add(ReconciliationResult.missing(id, report.getTitle()));
            } else {
                String externalTitle = external.get(id);
                if (report.getTitle().equals(externalTitle)) {
                    // Identique des deux cotes
                    results.add(ReconciliationResult.matched(id, report.getTitle()));
                } else {
                    // Present des deux cotes mais titre different
                    results.add(ReconciliationResult.conflict(
                        id, report.getTitle(),
                        report.getTitle(),
                        externalTitle
                    ));
                }
            }
        }

        // Rapports presents dans le CSV externe mais absents de notre DB
        for (String externalId : external.keySet()) {
            if (!internal.containsKey(externalId)) {
                results.add(ReconciliationResult.surplus(externalId, external.get(externalId)));
            }
        }

        // 4. Construire le summary
        ReconciliationSummary summary = new ReconciliationSummary(results);

        return toDto(summary);
    }

    private ReconciliationReportDto toDto(ReconciliationSummary summary) {
        List<ReconciliationResultDto> details = summary.getResults().stream()
            .map(r -> new ReconciliationResultDto(
                r.getReportId(),
                r.getTitle(),
                r.getStatus().name(),
                r.getInternalValue(),
                r.getExternalValue()
            ))
            .collect(Collectors.toList());

        return new ReconciliationReportDto(
            summary.getReconciledAt(),
            summary.isClean(),
            summary.countMatched(),
            summary.countMissing(),
            summary.countSurplus(),
            summary.countConflicts(),
            details
        );
    }
}