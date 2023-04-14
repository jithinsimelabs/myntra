package org.simelabs.catelog.application.service.controller;

import org.simelabs.catelog.application.service.dto.CreateBrandCommand;
import org.simelabs.catelog.application.service.exception.BrandNotFoundException;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/brand")
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("")
    public ResponseEntity<?> geAllBrands(){
        return ResponseEntity.ok(brandRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBrand(@RequestBody @Valid CreateBrandCommand createBrandCommand){
        if (brandRepository.findByName(createBrandCommand.getName()).isPresent()) {
            throw new BrandNotFoundException("user already exist with this name ", HttpStatus.BAD_REQUEST);
        }
        Brand brand = brandRepository.save(
                Brand.builder().name(createBrandCommand.getName()).build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(brand);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Not found brand with this id " + id, HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(brand);
    }

}
