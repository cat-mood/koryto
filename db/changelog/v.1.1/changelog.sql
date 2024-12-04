ALTER TABLE users
ADD role VARCHAR(30);

UPDATE users
SET role = 'ROLE_ADMIN'
WHERE username = 'admin';
