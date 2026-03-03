package com.reportexport.application.dto;

public class ExportResult {

    private final byte[] data;
    private final String filename;
    private final String contentType;

    public ExportResult(byte[] data, String filename, String contentType) {
        this.data        = data;
        this.filename    = filename;
        this.contentType = contentType;
    }

    public byte[] getData()        { return data; }
    public String getFilename()    { return filename; }
    public String getContentType() { return contentType; }
}