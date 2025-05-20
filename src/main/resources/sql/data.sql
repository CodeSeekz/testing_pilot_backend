

INSERT INTO otp_tokens (hash_otp, expire_date, purpose, user_id)
VALUES
    (
        'hashed_otp_value_1',
        NOW() + INTERVAL '10 minutes',
        'REGISTER',
        'aa16f542-717e-480d-b349-7f3f2392ddf0' -- replace with a real user_id from your `users` table
    );


SELECT * FROM  users where user_id = 'd33a79b2-f5a2-46e6-80e1-64095974c5fa';