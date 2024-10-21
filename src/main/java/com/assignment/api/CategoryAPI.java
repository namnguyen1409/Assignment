package com.assignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.repositories.shop.product.CategoryRepo;

import jakarta.transaction.Transactional;


@RestController
@Transactional
@RequestMapping("/api/category")
public class CategoryAPI {
    
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/parent/{id}")
    public ResponseEntity<?> getCategoriesByParentId(@PathVariable("id") Long parentId) {
        var result = categoryRepo.findAllByParentId(parentId).stream().map(c -> c.toDTO()).toList();
        return ResponseEntity.ok(result);
    }

}
