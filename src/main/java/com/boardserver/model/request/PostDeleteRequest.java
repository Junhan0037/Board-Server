package com.boardserver.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDeleteRequest {
    private int id;
    private int accountId;
}
