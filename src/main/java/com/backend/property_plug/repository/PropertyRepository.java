package com.backend.property_plug.repository;

import com.backend.property_plug.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Custom query methods if needed
} 