SELECT name
FROM user_role ur
         INNER JOIN app_roles ar ON ur.role_id = ar.role_id
WHERE user_id = 2;
