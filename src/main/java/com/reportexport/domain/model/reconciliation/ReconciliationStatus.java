package com.reportexport.domain.model.reconciliation;

public enum ReconciliationStatus {
    MATCHED,   // rapport present dans les deux fichiers, donnees identiques
    MISSING,   // rapport present en DB mais absent du fichier externe
    SURPLUS,   // rapport present dans le fichier externe mais absent en DB
    CONFLICT   // rapport present des deux cotes mais donnees differentes
}