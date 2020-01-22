package com.xmchx.vote.controller.api;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Category;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.payload.CategoryRequest;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.CategoryRepository;
import com.xmchx.vote.service.CategoryService;
import com.xmchx.vote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 17:57
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public PagedResponse<?> list(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sidx", defaultValue = "createTime") String sidx,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return categoryService.getAllCategories(page, size, sidx, order);
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "name is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setCreateTime(new Date());
        category.setRank(categoryRequest.getRank());
        categoryRepository.save(category);
        return new ResponseEntity(new ApiResponse(true, "category save successfully"),
                HttpStatus.OK);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new ResponseEntity(new ApiResponse(false, "ids is null!"),
                    HttpStatus.BAD_REQUEST);
        }
        categoryRepository.deleteCategoriesByIdIn(Arrays.asList(ids));

        return new ResponseEntity(new ApiResponse(true, "delete categories successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody CategoryRequest categoryRequest) {
        Category c =
                categoryRepository.findById(categoryRequest.getId()).orElseThrow(() -> new AppException(
                        "Category not exist."));
        c.setName(categoryRequest.getName());
        c.setRank(categoryRequest.getRank());
        categoryRepository.save(c);
        return new ResponseEntity(new ApiResponse(true, "Category update successfully"),
                HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> info(@PathVariable("id") Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(
                "category not exist."));
        return new ResponseEntity(category, HttpStatus.OK);
    }

}
