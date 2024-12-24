package com.example.backend.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;


public interface LocationService {
    List<String> getAllWards();
    List<String> getAllDistricts();
    List<String> getAllCities();
}
