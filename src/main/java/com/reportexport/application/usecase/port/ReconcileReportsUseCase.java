package com.reportexport.application.usecase.port;

import com.reportexport.application.dto.ReconciliationReportDto;

public interface ReconcileReportsUseCase {
    ReconciliationReportDto reconcile(byte[] externalCsvContent);
}