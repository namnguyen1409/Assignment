package com.assignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Province;
import com.assignment.models.repositories.location.DistrictRepo;
import com.assignment.models.repositories.location.ProvinceRepo;
import com.assignment.models.repositories.location.WardRepo;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/api/location")
public class LocationAPI {

    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private WardRepo wardRepo;


    @GetMapping("/provinces")
    public ResponseEntity<?> getProvinces() {
        return ResponseEntity.ok(provinceRepo.findAll());
    }

    @GetMapping("/districts/{provinceId}")
    public ResponseEntity<?> getDistrictsByProvinceId(@PathVariable("provinceId") Long provinceId) {
        Province province = provinceRepo.findById(provinceId);
        if (province == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(districtRepo.findAllByProvince(province));
    }

    @GetMapping("/wards/{districtId}")
    public ResponseEntity<?> getWardsByDistrictId(@PathVariable("districtId") Long districtId) {
        District district = districtRepo.findById(districtId);
        if (district == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wardRepo.findAllByDistrict(district));
    }

}
