package com.boardserver.mapper;

import com.boardserver.model.entity.PostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    int register(PostEntity postDTO);

    List<PostEntity> selectMyProducts(int accountId);

    void updateProducts(PostEntity postDTO);

    void deleteProduct(int postId);
}
