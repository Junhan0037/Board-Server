package com.boardserver.controller;

import com.boardserver.model.entity.PostEntity;
import com.boardserver.model.request.PostSearchRequest;
import com.boardserver.model.response.PostSearchResponse;
import com.boardserver.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Log4j2
public class PostSearchController {

    private final PostSearchService postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostEntity> postDTOList = postSearchService.getProducts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }


}
