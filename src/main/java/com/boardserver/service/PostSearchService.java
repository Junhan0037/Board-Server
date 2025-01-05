package com.boardserver.service;

import com.boardserver.exception.BoardServerException;
import com.boardserver.mapper.PostSearchMapper;
import com.boardserver.model.entity.PostEntity;
import com.boardserver.model.request.PostSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostSearchService {

    private final PostSearchMapper postSearchMapper;
    private final SlackService slackService;

    /**
     * 비동기로 게시물을 검색
     * 결과는 캐시로 저장하여 동일 요청에 대해 성능을 최적화
     */
    @Async // 비동기로 메서드를 실행하도록 설정
    @Cacheable(value = "getProducts", key = "'getProducts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    public List<PostEntity> getProducts(PostSearchRequest postSearchRequest) {
        List<PostEntity> postList;

        try {
            postList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            slackService.sendSlackMessage("selectPosts 실패" + e.getMessage(), "error");
            log.error("selectPosts 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return postList;
    }

}
