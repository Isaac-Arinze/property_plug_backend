package com.backend.property_plug.dto;

import java.util.UUID;

public class PropertyImageDto {
    private UUID id;
    private String url;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
} 