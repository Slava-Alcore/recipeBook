DROP TABLE user_roles IF EXISTS;
DROP TABLE products IF EXISTS;
DROP TABLE recipes IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE user_seq IF EXISTS;
DROP SEQUENCE recipe_seq IF EXISTS;
DROP SEQUENCE product_seq IF EXISTS;

CREATE SEQUENCE user_seq AS INTEGER  START WITH 100000;
CREATE SEQUENCE recipe_seq AS INTEGER  START WITH 100;
CREATE SEQUENCE product_seq AS INTEGER  START WITH 1000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE user_seq PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE recipes
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE recipe_seq PRIMARY KEY,
  date   TIMESTAMP DEFAULT now()    NOT NULL,
  description VARCHAR(255) NOT NULL,
  servings    INT          NOT NULL,
  user_id     INTEGER      NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE products
(
    id          INTEGER GENERATED BY DEFAULT AS SEQUENCE product_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    volumeMeasure VARCHAR(255) NOT NULL,
    volume    DECIMAL          NOT NULL,
    recipe_id     INTEGER      NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);