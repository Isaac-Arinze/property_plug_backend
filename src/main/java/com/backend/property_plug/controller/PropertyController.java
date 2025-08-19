package com.backend.property_plug.controller;

import com.backend.property_plug.Services.PropertyService;
import com.backend.property_plug.Services.UserService;
import com.backend.property_plug.dto.PropertyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
@Tag(name = "Property API", description = "Endpoints for property listing management")
@SecurityRequirement(name = "bearerAuth")
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "Get all properties with pagination and filtering")
    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String title
    ) {
        return ResponseEntity.ok(propertyService.getAllProperties(page, size, location, minPrice, maxPrice, title));
    }

    @Operation(summary = "Get property by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @Operation(summary = "Get properties for the authenticated owner")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @GetMapping("/my")
    public ResponseEntity<List<PropertyDto>> getMyProperties(Principal principal) {
        UUID ownerId = userService.getUserByEmail(principal.getName()).getId();
        return ResponseEntity.ok(propertyService.getPropertiesByOwner(ownerId));
    }

    @Operation(summary = "Get all properties with owner info (tenant view)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_TENANT')")
    @GetMapping("/tenant-view")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesForTenant() {
        return ResponseEntity.ok(propertyService.getAllPropertiesWithOwnerInfo());
    }

    // NEW: JSON-only endpoint for testing without images
    @Operation(summary = "Create a new property (JSON only - for testing)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPropertyJson(
            @RequestBody PropertyDto propertyDto,
            Principal principal
    ) {
        try {
            if (propertyDto == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Property data is required"));
            }

            UUID ownerId = userService.getUserByEmail(principal.getName()).getId();
            PropertyDto created = propertyService.createProperty(propertyDto, ownerId, null);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create property: " + e.getMessage()));
        }
    }

    // UPDATED: Multipart endpoint with better error handling
    @Operation(summary = "Create a new property with images (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPropertyWithImages(
            @Parameter(
                    description = "Property details as JSON string",
                    required = true
            )
            @RequestPart("property") String propertyJson,

            @Parameter(
                    description = "Property images (jpeg, png, svg, etc.)",
                    required = false
            )
            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            Principal principal
    ) {
        try {
            // Parse JSON string to PropertyDto
            PropertyDto propertyDto;
            try {
                propertyDto = objectMapper.readValue(propertyJson, PropertyDto.class);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid JSON format: " + e.getMessage()));
            }

            if (propertyDto == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Property data is required"));
            }

            // Validate images if provided
            if (images != null && !images.isEmpty()) {
                for (MultipartFile file : images) {
                    String contentType = file.getContentType();
                    String originalFilename = file.getOriginalFilename();
                    
                    System.out.println("Uploaded file: " + originalFilename + ", content type: " + contentType);
                    
                    if (contentType == null || (!isValidImageType(contentType) && !contentType.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE))) {
                        return ResponseEntity.badRequest().body(Map.of("error", "Unsupported file type: " + contentType + " for file: " + originalFilename));
                    }
                }
            }

            UUID ownerId = userService.getUserByEmail(principal.getName()).getId();
            PropertyDto created = propertyService.createProperty(propertyDto, ownerId, images);
            return ResponseEntity.ok(created);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create property: " + e.getMessage()));
        }
    }

    // ALTERNATIVE: Individual field approach
    @Operation(summary = "Create a new property (individual fields)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PostMapping(value = "/create-fields", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPropertyFields(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("price") Double price,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Principal principal
    ) {
        try {
            // Create PropertyDto from individual fields
            PropertyDto propertyDto = new PropertyDto();
            propertyDto.setTitle(title);
            propertyDto.setDescription(description);
            propertyDto.setLocation(location);
            propertyDto.setPrice(price);

            // Validate images if provided
            if (images != null && !images.isEmpty()) {
                for (MultipartFile file : images) {
                    String contentType = file.getContentType();
                    String originalFilename = file.getOriginalFilename();
                    
                    System.out.println("Uploaded file: " + originalFilename + ", content type: " + contentType);
                    
                    if (contentType == null || (!isValidImageType(contentType) && !contentType.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE))) {
                        return ResponseEntity.badRequest().body(Map.of("error", "Unsupported file type: " + contentType + " for file: " + originalFilename));
                    }
                }
            }

            UUID ownerId = userService.getUserByEmail(principal.getName()).getId();
            PropertyDto created = propertyService.createProperty(propertyDto, ownerId, images);
            return ResponseEntity.ok(created);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create property: " + e.getMessage()));
        }
    }

    @Operation(summary = "Delete a property (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a property (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProperty(
            @PathVariable UUID id,
            @RequestPart("property") String propertyJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Principal principal) {
        try {
            PropertyDto propertyDto = objectMapper.readValue(propertyJson, PropertyDto.class);
            UUID ownerId = userService.getUserByEmail(principal.getName()).getId();
            PropertyDto updated = propertyService.updateProperty(id, propertyDto, ownerId, images);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update property: " + e.getMessage()));
        }
    }

    @Operation(summary = "Express interest in a property (tenant only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_TENANT')")
    @PostMapping("/{id}/interest")
    public ResponseEntity<String> expressInterest(@PathVariable UUID id, Principal principal) {
        String tenantEmail = principal.getName();
        propertyService.expressInterest(id, tenantEmail);
        return ResponseEntity.ok("Interest expressed successfully");
    }

    // Helper method to validate image types
    private boolean isValidImageType(String contentType) {
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
               contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
               contentType.equals("image/jpg") ||
               contentType.equals("image/svg+xml") ||
               contentType.equals("image/gif") ||
               contentType.equals("image/webp") ||
               contentType.equals("image/bmp") ||
               contentType.equals("image/tiff") ||
               contentType.equals("image/x-icon") ||
               contentType.equals("image/vnd.microsoft.icon");
    }
}