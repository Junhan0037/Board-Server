package com.boardserver.mapper;


import com.boardserver.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {
    UserEntity getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int updateUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int deleteUserProfile(@Param("id") String id);

    int register(UserEntity userDTO);

    UserEntity findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(String id);

    int updatePassword(UserEntity userDTO);

    int updateAddress(UserEntity userDTO);
}
