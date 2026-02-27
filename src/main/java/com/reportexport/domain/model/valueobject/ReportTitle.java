package com.reportexport.domain.model.valueobject;

public final class ReportTitle {

    private final String value;

    public ReportTitle(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Title must not be blank");
        if (value.trim().length() < 3)
            throw new IllegalArgumentException("Title must be at least 3 characters");
        if (value.trim().length() > 100)
            throw new IllegalArgumentException("Title must not exceed 100 characters");
        this.value = value.trim();
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReportTitle)) return false;
        return value.equals(((ReportTitle) o).value);
    }

    @Override public int hashCode() { return value.hashCode(); }
    @Override public String toString() { return value; }
}