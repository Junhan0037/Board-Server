package com.boardserver.model.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    private int id;
    private int postId;
    private String contents;
    private int subCommentId;
}
