DELETE FROM user_roles;
DELETE from meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

insert into meals (user_id, description, calories) values
('100000', 'ехали собаки', '2000'),
('100000', 'на волшебном раке', '1000'),
('100001', 'а за ними кот', '2000'),
('100001', 'нюхал водород', '500');