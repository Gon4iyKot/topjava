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

INSERT INTO meals (user_id, date_time, description, calories) VALUES
('100000', '2019-06-26 12:00:00', 'ехали собаки', '2000'),
('100000', '2019-06-26 13:00:00', 'на волшебном раке', '1000'),
('100000', '2019-06-25 14:00:00', 'а за ними кот', '2000'),
('100001', '2019-06-26 15:00:00', 'нюхал водород', '500'),
('100001', '2019-06-26 16:00:00', 'еще один', '500');