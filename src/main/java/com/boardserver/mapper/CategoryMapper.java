package com.boardserver.mapper;

import com.boardserver.model.entity.CategoryEntity;

public interface CategoryMapper {
    int register(CategoryEntity productDTO);

    void updateCategory(CategoryEntity categoryDTO);

    void deleteCategory(int categoryId);
}
