package com.backend.property_plug.repository;

import com.backend.property_plug.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    // Custom query methods if needed
} 