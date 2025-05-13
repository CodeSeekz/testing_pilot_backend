package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface UserRepository {

    @Results(id = "appUserMapper", value = {@Result(property = "userId", column = "user_id"), @Result(property = "isVerified", column = "is_verify"),})
    @Select("""
            SELECT * FROM users WHERE email = #{email}
            """)
    User getUserByEmail(String email);

    @ResultMap("appUserMapper")
    @Select("""
                SELECT * FROM  users where user_id = #{userId}
            """)
    User findById(UUID userId);

    @ResultMap("appUserMapper")
    @Select("""
                    INSERT INTO users (username, email, password
                )
                values (#{request.username}, #{request.email}, #{request.password})
                RETURNING *;
            """)
    User saveUser(@Param("request") User request);

    @Update("""
                UPDATE users SET
                is_verify = #{isVerified}
                WHERE user_id = #{userId};
            """)
    void updateIsVerified(UUID userId, boolean isVerified);

    @Update("""
                UPDATE users SET
                password = #{newPassword}
                WHERE user_id = #{userId}
                """)
    void updatePassword(UUID userId, String newPassword);
}
