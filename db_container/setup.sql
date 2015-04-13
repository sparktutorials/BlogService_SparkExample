CREATE USER blog_owner WITH PASSWORD 'sparkforthewin';
CREATE DATABASE blog;
\connect blog
GRANT ALL PRIVILEGES ON DATABASE blog TO blog_owner;
