package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.VerificationToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VerificationTokenRepository {
    @Results(id = "emailTokenMapper", value = {
            @Result(property = "expireAt", column = "expire_at")
    })
    @Select("""
        SELECT  * FROM  verification_token where token = #{token};
    """)
    VerificationToken getByToken(String token);

    @ResultMap("emailTokenMapper")
    @Select("""
    INSERT INTO  verification_token (email, token, expire_at) 
    VALUES (#{request.email}, #{request.token}, #{request.expireAt})
    RETURNING *
""")
    VerificationToken saveEmailToken(@Param("request") VerificationToken request);

    @Delete("""
        DELETE FROM verification_token WHERE token = #{token} and email = #{email};
    """)
    void removeByTokenAndEmail(String token, String email);
}
