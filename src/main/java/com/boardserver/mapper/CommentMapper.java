package com.boardserver.mapper;

import com.boardserver.model.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int register(CommentEntity commentDTO);

    void updateComments(CommentEntity commentDTO);

    void deletePostComment(int commentId);
}
