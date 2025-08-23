package com.backend.property_plug.dto;

import java.util.List;
import java.util.UUID;

public class PropertyDto {
    private UUID id;
    private String title;
    private String description;
    private String location;
    private Double price;
    private String type; // "Sale" or "Rent"
    private Double rating; // 0-5 rating
    private Integer reviewsCount; // number of reviews
    private UUID ownerId;
    private String ownerName;
    private String ownerContactInfo;
    private List<PropertyImageDto> images;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }
    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID ownerId) { this.ownerId = ownerId; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getOwnerContactInfo() {
        return ownerContactInfo;
    }

    public void setOwnerContactInfo(String ownerContactInfo) {
        this.ownerContactInfo = ownerContactInfo;
    }
    public List<PropertyImageDto> getImages() { return images; }
    public void setImages(List<PropertyImageDto> images) { this.images = images; }
} 