package com.boardserver.service;

import com.boardserver.exception.DuplicateIdException;
import com.boardserver.mapper.UserProfileMapper;
import com.boardserver.model.entity.UserEntity;
import com.boardserver.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserProfileMapper userProfileMapper;

    public UserEntity getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    public void register(UserEntity userDTO) {
        boolean duplIdResult = isDuplicatedId(userDTO.getUserId());

        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userDTO.setCreateTime(new Date());
        userDTO.setPassword(SHA256Util.encryptSHA256(userDTO.getPassword()));
        int insertCount = userProfileMapper.register(userDTO);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDTO);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }

    public UserEntity login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        return userProfileMapper.findByIdAndPassword(id, cryptoPassword);
    }

    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserEntity memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserEntity memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    private boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

}
