-- Products
INSERT INTO products (product_id, name, price, description) VALUES (1, 'Stella 25', 2.9, 'Kleine Stella van''t vat')
INSERT INTO products (product_id, name, price, description) VALUES (2, 'Fanta', 3.0, 'Fanta Orange')
INSERT INTO products (product_id, name, price, description) VALUES (3, 'Vol-au-vent', 19.5, 'Vol-au-vent met sla en frietjes')
INSERT INTO products (product_id, name, price, description) VALUES (4, 'Dagsoep', 6.5, 'Soep van de dag')

-- Categories
INSERT INTO categories (category_id, name) VALUES (1, 'Bier')
INSERT INTO categories (category_id, name) VALUES (2, 'Frisdrank')
INSERT INTO categories (category_id, name) VALUES (3, 'Snack')
INSERT INTO categories (category_id, name) VALUES (4, 'Soep')

-- Add products to categories

INSERT INTO category_product (product_id, category_id) VALUES (1, 1)
INSERT INTO category_product (product_id, category_id) VALUES (2, 1)