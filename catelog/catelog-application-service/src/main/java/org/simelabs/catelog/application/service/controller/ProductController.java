package org.simelabs.catelog.application.service.controller;

import org.simelabs.catelog.application.service.dto.CreateProductCommand;
import org.simelabs.catelog.application.service.exception.BrandNotFoundException;
import org.simelabs.catelog.application.service.exception.CategoryNotFoundException;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.models.ProductEntity;
import org.simelabs.catelog.application.service.repository.BrandRepository;
import org.simelabs.catelog.application.service.repository.CategoryRepository;
import org.simelabs.catelog.application.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductCommand createProductCommand){

        String categoryName = createProductCommand.getCategory_name();
        String brandName = createProductCommand.getBrand_name();

        Category category = categoryRepository.findByName(categoryName).orElseThrow(
                () -> new CategoryNotFoundException("No category with this name ", HttpStatus.NOT_FOUND)
        );
        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new BrandNotFoundException("No brand with this name ", HttpStatus.NOT_FOUND));

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .name(createProductCommand.getName())
                        .category(category)
                        .brand(brand)
                        .color(createProductCommand.getColor())
                        .price(createProductCommand.getPrice())
                        .description(createProductCommand.getDescription())
                        .size(createProductCommand.getSize())
                        .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }



}
