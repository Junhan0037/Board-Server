package com.boardserver.model.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {
    private int id;
    private String name;
    private String url;
    private int postId;
}
