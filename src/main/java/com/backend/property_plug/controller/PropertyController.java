package com.backend.property_plug.controller;

import com.backend.property_plug.Services.PropertyService;
import com.backend.property_plug.Services.UserService;
import com.backend.property_plug.dto.PropertyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/properties")
@Tag(name = "Property API", description = "Endpoints for property listing management")
@SecurityRequirement(name = "bearerAuth")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

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
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @Operation(summary = "Get properties for the authenticated owner")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @GetMapping("/my")
    public ResponseEntity<List<PropertyDto>> getMyProperties(Principal principal) {
        Long ownerId = userService.getUserByEmail(principal.getName()).getId();
        return ResponseEntity.ok(propertyService.getPropertiesByOwner(ownerId));
    }

    @Operation(summary = "Create a new property (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PropertyDto> createProperty(
            @RequestPart("property") PropertyDto propertyDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Principal principal) {
        Long ownerId = userService.getUserByEmail(principal.getName()).getId();
        PropertyDto created = propertyService.createProperty(propertyDto, ownerId, images);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Delete a property (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a property (owner only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_OWNER')")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<PropertyDto> updateProperty(
            @PathVariable Long id,
            @RequestPart("property") PropertyDto propertyDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Principal principal) {
        Long ownerId = userService.getUserByEmail(principal.getName()).getId();
        PropertyDto updated = propertyService.updateProperty(id, propertyDto, ownerId, images);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Express interest in a property (tenant only)")
    @PreAuthorize("hasAuthority('ROLE_PROPERTY_TENANT')")
    @PostMapping("/{id}/interest")
    public ResponseEntity<String> expressInterest(@PathVariable Long id, Principal principal) {
        propertyService.expressInterest(id, principal.getName());
        return ResponseEntity.ok("Interest expressed successfully");
    }
} 