package com.reportexport.domain.model.valueobject;

public final class ReportContent {

    private final String value;

    public ReportContent(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Content must not be blank");
        if (value.trim().length() > 5000)
            throw new IllegalArgumentException("Content must not exceed 5000 characters");
        this.value = value.trim();
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReportContent)) return false;
        return value.equals(((ReportContent) o).value);
    }

    @Override public int hashCode() { return value.hashCode(); }
    @Override public String toString() { return value; }
}