package com.reportexport.application.usecase.port;

import java.util.Map;

public interface CsvParserPort {
    // Retourne une map : reportId -> title
    // extrait du fichier CSV uploade par l'utilisateur
    Map<String, String> parse(byte[] csvContent);
}