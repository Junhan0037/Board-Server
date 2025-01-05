package com.boardserver.service;

import com.boardserver.exception.BoardServerException;
import com.boardserver.mapper.CategoryMapper;
import com.boardserver.model.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public void register(String accountId, CategoryEntity categoryEntity) {
        if (accountId != null) {
            try {
                categoryMapper.register(categoryEntity);
            } catch (RuntimeException e) {
                log.error("register 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("register ERROR! {}", categoryEntity);
            throw new RuntimeException("register ERROR! 상품 카테고리 등록 메서드를 확인해주세요\n" + "Params : " + categoryDTO);
        }
    }

    public void update(CategoryEntity categoryEntity) {
        if (categoryEntity != null) {
            try {
                categoryMapper.updateCategory(categoryEntity);
            } catch (RuntimeException e) {
                log.error("update 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("update ERROR! {}", categoryEntity);
            throw new RuntimeException("update ERROR! 물품 카테고리 변경 메서드를 확인해주세요\n" + "Params : " + categoryEntity);
        }
    }

    public void delete(int categoryId) {
        if (categoryId != 0) {
            try {
                categoryMapper.deleteCategory(categoryId);
            } catch (RuntimeException e) {
                log.error("delete 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("deleteCategory ERROR! {}", categoryId);
            throw new RuntimeException("deleteCategory ERROR! 물품 카테고리 삭제 메서드를 확인해주세요\n" + "Params : " + categoryId);
        }
    }

}
