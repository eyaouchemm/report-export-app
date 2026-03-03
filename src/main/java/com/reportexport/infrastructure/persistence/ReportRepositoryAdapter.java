package com.reportexport.infrastructure.persistence;

import com.reportexport.domain.model.Report;
import com.reportexport.domain.model.ReportStatus;
import com.reportexport.domain.model.ReportType;
import com.reportexport.domain.repository.ReportRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReportRepositoryAdapter implements ReportRepository {

    private final ReportJpaRepository jpa;

    public ReportRepositoryAdapter(ReportJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Report save(Report report) {
        return toDomain(jpa.save(toEntity(report)));
    }

    @Override
    public Optional<Report> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Report> findAll() {
        return jpa.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Report> findAllValidated() {
        return jpa.findByStatus(ReportStatus.VALIDATED.name()).stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private ReportEntity toEntity(Report r) {
        ReportEntity e = new ReportEntity();
        e.setId(r.getId());
        e.setTitle(r.getTitle());
        e.setContent(r.getContent());
        e.setType(r.getType().name());
        e.setStatus(r.getStatus().name());
        e.setCreatedAt(r.getCreatedAt());
        e.setUpdatedAt(r.getUpdatedAt());
        return e;
    }

    private Report toDomain(ReportEntity e) {
        return Report.reconstitute(
            e.getId(), e.getTitle(), e.getContent(),
            ReportType.valueOf(e.getType()),
            ReportStatus.valueOf(e.getStatus()),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}