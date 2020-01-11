DELETE FROM user_roles;
DELETE FROM products;
DELETE FROM recipes;
DELETE FROM users;
ALTER SEQUENCE user_seq RESTART WITH 100000;
ALTER SEQUENCE recipe_seq RESTART WITH 100;
ALTER SEQUENCE product_seq RESTART WITH 1000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO recipes (date, description, servings, user_id)
VALUES ('2019-03-29 01:00', 'Завтрак', 4, 100000),
       ('2019-03-30 01:00', 'Обед', 3, 100000),
       ('2019-03-31 01:00', 'Админ ужин', 4, 100001);


INSERT INTO products (name, volumeMeasure, volume, recipe_id)
VALUES ('Картошка', 'кг', 0.5, 100),
       ('Яйцо', 'шт', 3, 100),
       ('Морковь', 'кг', 0.2, 100),
       ('Мясо', 'кг', 1, 101),
       ('Картошка', 'кг', 0.6, 101),
       ('Лук', 'кг', 0.2, 101),
       ('Говядина', 'кг', 1, 102),
       ('Макароны', 'г', 300, 102),
       ('Томаты', 'кг', 0.3, 102);
