package com.example.backend.backend.repository;

import com.example.backend.backend.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityReponsitory extends JpaRepository<Amenity,Integer> {
    List<Amenity> findAllById(Iterable<Integer> ids);
}
