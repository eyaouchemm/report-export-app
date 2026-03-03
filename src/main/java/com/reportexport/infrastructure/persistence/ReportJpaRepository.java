package com.reportexport.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByStatus(String status);
}