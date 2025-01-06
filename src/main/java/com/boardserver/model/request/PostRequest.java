package com.boardserver.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostRequest {
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;
}
