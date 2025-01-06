package com.boardserver.controller;

import com.boardserver.aop.LoginCheck;
import com.boardserver.model.entity.UserEntity;
import com.boardserver.model.request.UserDeleteId;
import com.boardserver.model.request.UserLoginRequest;
import com.boardserver.model.request.UserUpdatePasswordRequest;
import com.boardserver.model.response.LoginResponse;
import com.boardserver.model.response.UserInfoResponse;
import com.boardserver.service.UserService;
import com.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    private static LoginResponse loginResponse;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserEntity userDTO) {
        if (UserEntity.hasNullDateBeforeSignUp(userDTO)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }

        userService.register(userDTO);
    }

    @PostMapping("/sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest loginRequest, HttpSession session) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        UserEntity userInfo = userService.login(userId, password);
        String id = userInfo.getId().toString();

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.isAdmin())
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);
        }

        return HttpStatus.OK;
    }

    @GetMapping("/my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);

        if (id == null) {
            id = SessionUtil.getLoginAdminId(session);
        }

        UserEntity memberInfo = userService.getUserInfo(id);

        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("/logout")
    public void logout(String accountId, HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("/password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = accountId;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(Id, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity;
        String Id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(Id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.info("deleteID 실패");
            responseEntity = FAIL_RESPONSE;
        }

        return responseEntity;
    }

}
