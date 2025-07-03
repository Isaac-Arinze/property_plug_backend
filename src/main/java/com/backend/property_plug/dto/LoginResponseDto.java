package com.backend.property_plug.dto;

public class LoginResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private String fullName;
    private String userType;

    public LoginResponseDto(String accessToken, String email, String fullName, String userType) {
        this.accessToken = accessToken;
        this.email = email;
        this.fullName = fullName;
        this.userType = userType;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
} 