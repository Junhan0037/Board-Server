package com.boardserver.mapper;

import com.boardserver.model.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    int register(TagEntity tagDTO);

    void updateTags(TagEntity tagDTO);

    void deletePostTag(int tagId);
}
