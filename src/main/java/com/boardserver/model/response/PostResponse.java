package com.boardserver.model.response;

import com.boardserver.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {
    private List<PostEntity> postDTO;
}
