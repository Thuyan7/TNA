package com.example.backend.backend.repository;

import com.example.backend.backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT DISTINCT SUBSTRING_INDEX(address, ',', -1) AS city FROM locations", nativeQuery = true)
    List<String> findDistinctCities();

    @Query(value = "SELECT DISTINCT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(address, ',', -2), ',', 1)) AS district FROM locations", nativeQuery = true)
    List<String> findDistinctDistricts();

    @Query(value = "SELECT DISTINCT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(address, ',', -3), ',', 1)) AS district FROM locations", nativeQuery = true)
    List<String> findDistinctWards();

    Location findByAddress(String address);


}
