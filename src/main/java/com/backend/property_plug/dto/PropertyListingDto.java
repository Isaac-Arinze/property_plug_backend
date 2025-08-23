package com.backend.property_plug.dto;

import java.util.UUID;

public class PropertyListingDto {
    private String id; // UUID as string
    private String title;
    private String location;
    private String price; // number or string
    private String type; // "Sale" or "Rent"
    private Double rating; // 0-5 rating
    private Integer reviewsCount; // number of reviews
    private PropertyImageDto image; // only the first image

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }

    public PropertyImageDto getImage() { return image; }
    public void setImage(PropertyImageDto image) { this.image = image; }
} 