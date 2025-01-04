package com.boardserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 property 가 JSON 데이터에 있어도 에러를 내뱉지 않고 무시
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Integer id;
    private String userId;
    private String password;
    private String nickName;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;

    public enum Status { DEFAULT, ADMIN, DELETED }

    public static boolean hasNullDateBeforeSignUp(UserEntity userEntity) {
        return userEntity.getUserId() == null || userEntity.getPassword() == null || userEntity.getNickName() == null;
    }
}
