package com.backend.property_plug.repository;

import com.backend.property_plug.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    // Custom query methods if needed
} 