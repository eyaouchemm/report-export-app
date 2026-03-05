package com.reportexport.interfaces.rest;

import com.reportexport.application.dto.ReconciliationReportDto;
import com.reportexport.application.usecase.port.ReconcileReportsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    private final ReconcileReportsUseCase reconcileReports;

    public ReconciliationController(ReconcileReportsUseCase reconcileReports) {
        this.reconcileReports = reconcileReports;
    }


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> reconcile(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty())
            return ResponseEntity.badRequest().body("File is empty");

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".csv"))
            return ResponseEntity.badRequest().body("Only CSV files are accepted");

        try {
            ReconciliationReportDto report = reconcileReports.reconcile(file.getBytes());
            return ResponseEntity.ok(report);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to read file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Reconciliation failed: " + e.getMessage());
        }
    }
}