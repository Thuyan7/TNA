package com.example.backend.backend.service.imp;

import com.example.backend.backend.repository.LocationRepository;
import com.example.backend.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImp implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImp(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<String> getAllWards() {
        return locationRepository.findDistinctWards();
    }

    @Override
    public List<String> getAllDistricts() {
        return locationRepository.findDistinctDistricts();
    }

    @Override
    public List<String> getAllCities() {
        return locationRepository.findDistinctCities();
    }
}
