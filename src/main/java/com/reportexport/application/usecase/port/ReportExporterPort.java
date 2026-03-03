package com.reportexport.application.usecase.port;

import com.reportexport.domain.model.Report;

import java.util.List;

public interface ReportExporterPort {
    byte[] export(List<Report> reports);
    String getFormat();
}