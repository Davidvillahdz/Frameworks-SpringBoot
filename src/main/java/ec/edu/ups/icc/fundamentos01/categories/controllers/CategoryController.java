package ec.edu.ups.icc.fundamentos01.categories.controllers;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> create(@Valid @RequestBody CreateCategoryDto dto) {
        CategoryEntity created = categoryService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}