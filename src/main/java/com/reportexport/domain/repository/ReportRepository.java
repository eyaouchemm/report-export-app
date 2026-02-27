package com.reportexport.domain.repository;

import com.reportexport.domain.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findById(Long id);
    List<Report> findAll();
}