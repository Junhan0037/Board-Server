package com.boardserver.mapper;

import com.boardserver.model.entity.PostEntity;
import com.boardserver.model.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostEntity> selectPosts(PostSearchRequest postSearchRequest);
}
