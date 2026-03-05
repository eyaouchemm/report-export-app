package com.reportexport.infrastructure.reconciliation;

import com.opencsv.CSVReader;
import com.reportexport.application.usecase.port.CsvParserPort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class CsvParserAdapter implements CsvParserPort {

    @Override
    public Map<String, String> parse(byte[] csvContent) {
        Map<String, String> result = new HashMap<>();

        try (CSVReader reader = new CSVReader( //lit le CSV ligne par ligne
                new InputStreamReader(        //lit le flux
                    new ByteArrayInputStream(csvContent), // transforme les bytes en flux de lecture
                    StandardCharsets.UTF_8))) {

            String[] headers = reader.readNext();
            if (headers == null) return result;

            int idIndex    = findIndex(headers, "ID");
            int titleIndex = findIndex(headers, "Title");
            int maxIndex   = Math.max(idIndex, titleIndex);

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length > maxIndex) {
                    // trim() supprime espaces ET guillemets residuels
                    String id    = clean(line[idIndex]);
                    String title = clean(line[titleIndex]);
                    if (!id.isEmpty()) {
                        result.put(id, title);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse external CSV: " + e.getMessage(), e);
        }

        return result;
    }

    private String clean(String value) {
        if (value == null) return "";
        return value.trim().replace("\"", "").replace("\u00ef\u00bb\u00bf", ""); // enleve le BOM UTF-8
    }

    private int findIndex(String[] headers, String name) {
        for (int i = 0; i < headers.length; i++) {
            String h = clean(headers[i]);
            if (h.equalsIgnoreCase(name)) return i;
        }
        throw new RuntimeException("Column '" + name + "' not found. Headers: "
            + java.util.Arrays.toString(headers));
    }
}