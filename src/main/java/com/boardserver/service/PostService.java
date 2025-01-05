package com.boardserver.service;

import com.boardserver.exception.BoardServerException;
import com.boardserver.mapper.CommentMapper;
import com.boardserver.mapper.PostMapper;
import com.boardserver.mapper.TagMapper;
import com.boardserver.mapper.UserProfileMapper;
import com.boardserver.model.entity.CommentEntity;
import com.boardserver.model.entity.PostEntity;
import com.boardserver.model.entity.TagEntity;
import com.boardserver.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;
    private final UserProfileMapper userProfileMapper;

    @CacheEvict(value = "getProducts", allEntries = true)
    public void register(String id, PostEntity postDTO) {
        UserEntity userEntity = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(userEntity.getId());
        postDTO.setCreateTime(new Date());

        if (userEntity != null) {
            try {
                postMapper.register(postDTO);
            } catch (RuntimeException e) {
                log.error("register 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    public List<PostEntity> getMyProducts(int accountId) {
        List<PostEntity> postDTOList;

        try {
            postDTOList = postMapper.selectMyProducts(accountId);
        } catch (RuntimeException e) {
            log.error("getMyProducts 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return postDTOList;
    }

    public void updateProducts(PostEntity postDTO) {
        if (postDTO != null && postDTO.getId() != 0 && postDTO.getUserId() != 0) {
            try {
                postMapper.updateProducts(postDTO);
            } catch (RuntimeException e) {
                log.error("updateProducts 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("updateProducts ERROR! {}", postDTO);
            throw new RuntimeException("updateProducts ERROR! 물품 변경 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    public void deleteProduct(int userId, int productId) {
        if (userId != 0 && productId != 0) {
            try {
                postMapper.deleteProduct(productId);
            } catch (RuntimeException e) {
                log.error("deleteProduct 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("deleteProudct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }

    public void registerComment(CommentEntity commentDTO) {
        if (commentDTO.getPostId() != 0) {
            try {
                commentMapper.register(commentDTO);
            } catch (RuntimeException e) {
                log.error("register 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment ERROR! 댓글 추가 메서드를 확인해주세요\n" + "Params : " + commentDTO);
        }
    }

    public void updateComment(CommentEntity commentDTO) {
        if (commentDTO != null) {
            try {
                commentMapper.updateComments(commentDTO);
            } catch (RuntimeException e) {
                log.error("updateComments 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("updateComment ERROR! {}", commentDTO);
            throw new RuntimeException("updateComment ERROR! 댓글 변경 메서드를 확인해주세요\n" + "Params : " + commentDTO);
        }
    }

    public void deletePostComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            try {
                commentMapper.deletePostComment(commentId);
            } catch (RuntimeException e) {
                log.error("deletePostComment 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("deletePostComment ERROR! {}", commentId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요\n" + "Params : " + commentId);
        }
    }

    public void registerTag(TagEntity tagDTO) {
        if (tagDTO.getPostId() != 0) {
            try {
                tagMapper.register(tagDTO);
            } catch (RuntimeException e) {
                log.error("register 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("registerTag ERROR! {}", tagDTO);
            throw new RuntimeException("registerTag ERROR! 태그 추가 메서드를 확인해주세요\n" + "Params : " + tagDTO);
        }
    }

    public void updateTag(TagEntity tagDTO) {
        if (tagDTO != null) {
            try {
                tagMapper.updateTags(tagDTO);
            } catch (RuntimeException e) {
                log.error("updateTags 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("updateTag ERROR! {}", tagDTO);
            throw new RuntimeException("updateTag ERROR! 태그 변경 메서드를 확인해주세요\n" + "Params : " + tagDTO);
        }
    }

    public void deletePostTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            try {
                tagMapper.deletePostTag(tagId);
            } catch (RuntimeException e) {
                log.error("deletePostTag 실패");
                throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요\n" + "Params : " + tagId);
        }
    }

}
