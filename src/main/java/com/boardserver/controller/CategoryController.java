package com.boardserver.controller;

import com.boardserver.aop.LoginCheck;
import com.boardserver.model.constant.SortStatus;
import com.boardserver.model.entity.CategoryEntity;
import com.boardserver.model.request.CategoryRequest;
import com.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryEntity category) {
        categoryService.register(accountId, category);
    }

    @PatchMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId, @PathVariable(name = "categoryId") int categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryEntity category = new CategoryEntity(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(category);
    }

    @DeleteMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void deleteCategories(String accountId, @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
    }

}
