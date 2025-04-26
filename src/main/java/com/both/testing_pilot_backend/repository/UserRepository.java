package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

	@Results(id = "appUserMapper", value = {
					@Result(property = "userId", column = "user_id"),
					@Result(property = "username", column = "username"),
					@Result(property = "roles", column = "user_id", many = @Many(select = "getAllRolesByUserId"))
	})
	@Select("""
					SELECT * FROM users WHERE email = #{email}
					""")
	User getUserByEmail(String email);

	@Select("""
					SELECT name FROM user_role ur INNER JOIN roles ar ON ur.role_id = ar.role_id WHERE user_id = #{userId}
					""")
	List<String> getAllRolesByUserId(Long userId);

}
