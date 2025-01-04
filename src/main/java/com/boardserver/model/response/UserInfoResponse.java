package com.boardserver.model.response;

import com.boardserver.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UserEntity userEntity;
}
