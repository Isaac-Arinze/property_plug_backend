package com.backend.property_plug.Services;

import com.backend.property_plug.dto.PropertyDto;
import com.backend.property_plug.dto.PropertyImageDto;
import com.backend.property_plug.entity.Property;
import com.backend.property_plug.entity.PropertyImage;
import com.backend.property_plug.entity.User;
import com.backend.property_plug.repository.PropertyRepository;
import com.backend.property_plug.repository.PropertyImageRepository;
import com.backend.property_plug.repository.UserRepository;
import com.backend.property_plug.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<PropertyDto> getAllProperties(int page, int size, String location, Double minPrice, Double maxPrice, String title) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAll().stream()
            .filter(p -> location == null || p.getLocation().toLowerCase().contains(location.toLowerCase()))
            .filter(p -> minPrice == null || (p.getPrice() != null && p.getPrice() >= minPrice))
            .filter(p -> maxPrice == null || (p.getPrice() != null && p.getPrice() <= maxPrice))
            .filter(p -> title == null || p.getTitle().toLowerCase().contains(title.toLowerCase()))
            .skip((long) page * size)
            .limit(size)
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public PropertyDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        return toDto(property);
    }

    public List<PropertyDto> getPropertiesByOwner(Long ownerId) {
        return propertyRepository.findAll().stream()
            .filter(p -> p.getOwner() != null && p.getOwner().getId().equals(ownerId))
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public PropertyDto createProperty(PropertyDto dto, Long ownerId, List<MultipartFile> images) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setLocation(dto.getLocation());
        property.setPrice(dto.getPrice());
        property.setOwner(owner);
        Property saved = propertyRepository.save(property);
        if (images != null) {
            for (MultipartFile file : images) {
                try {
                    String url = cloudinaryService.uploadFile(file);
                    PropertyImage img = new PropertyImage();
                    img.setUrl(url);
                    img.setProperty(saved);
                    propertyImageRepository.save(img);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload image", e);
                }
            }
        }
        return toDto(saved);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    public PropertyDto updateProperty(Long propertyId, PropertyDto dto, Long ownerId, List<MultipartFile> images) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
        if (property.getOwner() == null || !property.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("You are not authorized to update this property");
        }
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setLocation(dto.getLocation());
        property.setPrice(dto.getPrice());
        Property saved = propertyRepository.save(property);
        if (images != null && !images.isEmpty()) {
            // Remove old images
            propertyImageRepository.deleteAll(property.getImages());
            for (MultipartFile file : images) {
                try {
                    String url = cloudinaryService.uploadFile(file);
                    PropertyImage img = new PropertyImage();
                    img.setUrl(url);
                    img.setProperty(saved);
                    propertyImageRepository.save(img);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload image", e);
                }
            }
        }
        return toDto(saved);
    }

    public void expressInterest(Long propertyId, String tenantEmail) {
        // TODO: Implement actual Interest entity and persistence
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
        // For now, just log or print
        System.out.println("Tenant " + tenantEmail + " expressed interest in property " + propertyId);
    }

    private PropertyDto toDto(Property property) {
        PropertyDto dto = new PropertyDto();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setLocation(property.getLocation());
        dto.setPrice(property.getPrice());
        dto.setOwnerId(property.getOwner() != null ? property.getOwner().getId() : null);
        dto.setOwnerName(property.getOwner() != null ? property.getOwner().getFullName() : null);
        if (property.getImages() != null) {
            dto.setImages(property.getImages().stream().map(this::toImageDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private PropertyImageDto toImageDto(PropertyImage image) {
        PropertyImageDto dto = new PropertyImageDto();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        return dto;
    }
} 