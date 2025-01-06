package com.boardserver.controller;

import com.boardserver.aop.LoginCheck;
import com.boardserver.model.entity.CommentEntity;
import com.boardserver.model.entity.PostEntity;
import com.boardserver.model.entity.TagEntity;
import com.boardserver.model.entity.UserEntity;
import com.boardserver.model.request.PostDeleteRequest;
import com.boardserver.model.request.PostRequest;
import com.boardserver.model.response.CommonResponse;
import com.boardserver.service.PostService;
import com.boardserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostEntity>> registerPost(String accountId, @RequestBody PostEntity postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostEntity>>> myPostInfo(String accountId) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        List<PostEntity> postDTOList = postService.getMyProducts(memberInfo.getId());
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest>> updatePosts(String accountId,
                                                                   @PathVariable(name = "postId") int postId,
                                                                   @RequestBody PostRequest postRequest) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        PostEntity postDTO = PostEntity.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updateProducts(postDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts(String accountId,
                                                                         @PathVariable(name = "postId") int postId,
                                                                         @RequestBody PostDeleteRequest postDeleteRequest) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        postService.deleteProduct(memberInfo.getId(), postId);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteposts", postDeleteRequest);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentEntity>> registerPostComment(String accountId, @RequestBody CommentEntity commentDTO) {
        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentEntity>> updatePostComment(String accountId, @PathVariable(name = "commentId") int commentId, @RequestBody CommentEntity commentDTO) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentEntity>> deletePostComment(String accountId, @PathVariable(name = "commentId") int commentId, @RequestBody CommentEntity commentDTO) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null) {
            postService.deletePostComment(memberInfo.getId(), commentId);
        }
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagEntity>> registerPostTag(String accountId, @RequestBody TagEntity tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("/tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagEntity>> updatePostTag(String accountId, @PathVariable(name = "tagId") int tagId, @RequestBody TagEntity tagDTO) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null) {
            postService.updateTag(tagDTO);
        }
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagEntity>> deletePostTag(String accountId, @PathVariable(name = "tagId") int tagId, @RequestBody TagEntity tagDTO) {
        UserEntity memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null) {
            postService.deletePostTag(memberInfo.getId(), tagId);
        }
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

}
