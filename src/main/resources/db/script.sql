DROP TABLE orders IF EXISTS;
DROP TABLE products IF EXISTS;
DROP TABLE category IF EXISTS;
DROP TABLE recipes IF EXISTS;
DROP TABLE carts IF EXISTS;
DROP TABLE token IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255),
                       date_created DATETIME,
                       last_updated DATETIME,
                       status VARCHAR(255)
);

CREATE TABLE token (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       access_token VARCHAR(500),
                       refresh_token VARCHAR(500),
                       expired BOOLEAN,
                       revoked BOOLEAN,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE category (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255)
);

CREATE TABLE  orders (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         uuid VARCHAR(255),
                         name VARCHAR(255),
                         email VARCHAR(255),
                         payment_method VARCHAR(255),
                         total_price DECIMAL(10, 2),
                         product_detail JSON,
                         date_created DATETIME
);

CREATE TABLE products (
                          uuid INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          category_id INT,
                          description TEXT,
                          price DECIMAL(10, 2),
                          status VARCHAR(10),
                          date_created DATETIME
);

CREATE TABLE recipes (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255),
                         ingredients VARCHAR(255),
                         category VARCHAR(20),
                         preparation LONGTEXT,
                         people INTEGER,
                         date_created DATETIME
);

CREATE TABLE carts (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       date_created TIMESTAMP,
                       product_id INTEGER,
                       quantity INTEGER,
                       status VARCHAR(255),
                       total_cost DECIMAL(10, 2)
);

INSERT INTO category (name) VALUES ('vegetables'),('sauces'),('meat');

INSERT INTO products (name, category_id, description,price, status)
VALUES ('Egg', 1, 'Description 1', 50.00, 'true'), ('Mayonnaise', 2, 'Description 2', 75.00, 'true');

INSERT INTO users  (first_name,last_name, email, password, status, role, date_created, last_updated)
VALUES ('user@example.com', 'Johnson',  'user@example.com', '123456789','active', 'USER', NOW(), NOW());

INSERT INTO users ( first_name,last_name, email, password, status, role, date_created, last_updated)
VALUES ('admin@example.com', 'Kevin ', 'admin@example.com', '987654321', 'active', 'USER', NOW(), NOW());

INSERT INTO token (access_token, expired, revoked, user_id)
VALUES ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEwNzY5NzU3LCJleHAiOjE3MTA4NTYxNTd9.u-2x9YrmYwdbN8qOjqrzEheRV3fhQPwjt0VLQOmfnss',
        false, false, 1);

INSERT INTO token (access_token, expired, revoked, user_id)
VALUES ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcxMDc2OTcxOSwiZXhwIjoxNzEwODU2MTE5fQ.zpNwlj9f67w-h-JTSsFLpm8zfgVLZ2A_AKksXnMelQs',
        false, false, 2);

INSERT INTO orders (uuid, name, email, payment_method, total_price, product_detail, date_created)
VALUES ('345632323', 'Order 1', 'user@example.com', 'Credit Card', 100.00, '{"product_id": 1}','2024-03-11 14:30:00');

INSERT INTO orders (uuid, name, email, payment_method, total_price, product_detail, date_created)
VALUES ('4535476888', 'Order 2', 'test2@example.com', 'PayPal', 50.00, '{"product_id": 2}','2024-03-11 14:30:00');

INSERT INTO recipes (name, category, preparation, date_created)
VALUES ('Cake', 2, 'Step 1: Mix the ingredients. Step 2: Bake in the oven', '2024-03-11 14:30:00');

INSERT INTO recipes (name, category, preparation, date_created)
VALUES ('Tomato soup', 1, 'Step 1: Cut the tomatoes. Step 2: Cook with spices.', '2024-03-11 18:45:00');

INSERT INTO carts (id, date_created, status, total_cost, quantity, product_id)
VALUES ('1', '2024-01-01', 'active', 100.50, 2, 1);

INSERT INTO carts (id, date_created, status, total_cost, quantity, product_id)
VALUES ('2', '2024-02-15', 'pending', 75.25, 1, 2);

