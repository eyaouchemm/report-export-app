package com.reportexport.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateReportCommand {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 5000)
    private String content;

    @NotNull(message = "Type is required")
    private String type;

    public String getTitle()         { return title; }
    public String getContent()       { return content; }
    public String getType()          { return type; }
    public void setTitle(String t)   { this.title = t; }
    public void setContent(String c) { this.content = c; }
    public void setType(String t)    { this.type = t; }
}