package org.simelabs.catelog.application.service.controller;

import org.simelabs.catelog.application.service.dto.CreateBrandCommand;
import org.simelabs.catelog.application.service.dto.CreateCategoryCommand;
import org.simelabs.catelog.application.service.exception.BrandException;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<?> geAllCategory(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryCommand createCategoryCommand){
        if (categoryRepository.findByName(createCategoryCommand.getName()).isPresent()) {
            throw new BrandException("category already exist with this name ", HttpStatus.BAD_REQUEST);
        }
        Category category = categoryRepository.save(
                Category.builder()
                        .name(createCategoryCommand.getName())
                        .build()
        );
       return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Category brand = categoryRepository.findById(id).orElseThrow(() -> new BrandException("Not found category with this id " + id, HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(brand);
    }

}
