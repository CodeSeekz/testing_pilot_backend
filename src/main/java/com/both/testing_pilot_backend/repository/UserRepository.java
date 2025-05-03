package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.User;
import com.both.testing_pilot_backend.model.request.RegisterRequestDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "isVerified", column = "is_verified"),
    })
    @Select("""
            SELECT * FROM users WHERE email = #{email}
            """)
    User getUserByEmail(String email);

    @Insert("""
        INSERT INTO users (username, email, password)
        values (#{request.username}, #{request.email}, #{password})
    """)
    void saveUser(@Param("request") RegisterRequestDTO request, String password);
}
