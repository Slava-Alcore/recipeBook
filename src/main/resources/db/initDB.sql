DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS recipe_seq;
DROP SEQUENCE IF EXISTS product_seq;

CREATE SEQUENCE user_seq START WITH 100000;
CREATE SEQUENCE recipe_seq START WITH 100;
CREATE SEQUENCE product_seq START WITH 1000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE recipes (
  id          INTEGER PRIMARY KEY DEFAULT nextval('recipe_seq'),
  user_id     INTEGER   NOT NULL,
  date   TIMESTAMP DEFAULT now() NOT NULL,
  description TEXT      NOT NULL,
  servings    INT       NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE products (
  id          INTEGER PRIMARY KEY DEFAULT nextval('product_seq'),
  recipe_id     INTEGER   NOT NULL,
  name TEXT      NOT NULL,
  volumeMeasure TEXT      NOT NULL,
  volume    decimal       NOT NULL,
  FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);