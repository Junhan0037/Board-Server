package com.boardserver.model.response;

import com.boardserver.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    @NonNull
    private LoginStatus result;
    private UserEntity userEntity;

    public enum LoginStatus { SUCCESS, FAIL, DELETED }

    private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);

    public static LoginResponse success(UserEntity userEntity) {
        return new LoginResponse(LoginStatus.SUCCESS, userEntity);
    }
}
