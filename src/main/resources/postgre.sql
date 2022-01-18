CREATE SEQUENCE IF NOT EXISTS customer_id_seq;
CREATE TABLE IF NOT EXISTS customers (id int8 NOT NULL PRIMARY KEY, customer_name VARCHAR(60) NOT NULL);
ALTER SEQUENCE customer_id_seq OWNED BY customers.id;
ALTER TABLE customers ALTER COLUMN id SET DEFAULT nextval('customer_id_seq');

CREATE SEQUENCE IF NOT EXISTS product_id_seq;
CREATE TABLE IF NOT EXISTS products (id int8 NOT NULL PRIMARY KEY, product_name VARCHAR(60) NOT NULL, product_description VARCHAR(200) NOT NULL);
ALTER SEQUENCE product_id_seq OWNED BY products.id;
ALTER TABLE products ALTER COLUMN id SET DEFAULT nextval('product_id_seq');

INSERT INTO customers (customer_name) VALUES ('Roman'), ('Sveta'), ('Natasha'), ('David');
INSERT INTO products (product_name, product_description) VALUES ('Printer', 'to print'), ('Charger', 'to charge'), ('Pen', 'to write'),
 ('Phone', 'to call');

CREATE TABLE IF NOT EXISTS customer_product (customer_id int8 REFERENCES customers (id), product_id int8 REFERENCES products (id));

INSERT INTO customer_product (customer_id, product_id) VALUES (1, 2), (2, 1), (4, 4), (1, 4), (2, 2), (3, 1), (2, 4), (4, 1);
