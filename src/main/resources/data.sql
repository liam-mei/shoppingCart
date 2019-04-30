ALTER TABLE products ADD FULLTEXT(product_name);

INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id, order_date_time) VALUES (1, 1, 2, 'Mastercard1', '2019-01-20 20:50:50', true, 3, '2019-01-20 20:50:50');
INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id, order_date_time) VALUES (2, 1, 2, 'Mastercard2', '2019-01-20 20:50:50', false, 3, '2019-01-20 20:50:50');
INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id) VALUES (3, 1, 3, 'Mastercard3', '2019-01-20 20:50:50', true, 3);
INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id) VALUES (4, 4, 5, 'Mastercard4', '2019-01-20 20:50:50', false, 4);
INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id) VALUES (5, 4, 5, 'Mastercard5', '2019-01-20 20:50:50', true, 4);
INSERT INTO orders (order_id, billing_address_id, shipping_address_id, payment_details, ship_date_time, shipped_status, user_id) VALUES (6, 4, 6, 'Mastercard6', '2019-01-20 20:50:50', false, 4);

INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (1, 5, 1, 1);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (2, 5, 1, 2);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (3, 5, 1, 3);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (4, 5, 2, 1);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (5, 5, 2, 5);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (6, 5, 2, 6);
INSERT INTO order_items (order_item_id, quantity, order_id, product_id) VALUES (7, 5, 2, 7);

INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (1, 'description1', 'brazil-coffee-beans', 10.50, 'brazil coffee', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (2, 'description2', 'assorted-bean-pack-1', 11.50, 'assorted bean pack', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (3, 'description3', 'black-bean', 12.50, 'black bean', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (4, 'description4', 'black-bean', 13.50, 'red coffee of hell', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (5, 'description5', 'black-bean', 14.50, 'Ethiopia Sidamo', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (6, 'description6', 'black-bean', 15.50, 'Sumatra', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (7, 'description7', 'black-bean', 15.50, 'Sulawesi', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (8, 'description8', 'black-bean', 15.50, 'Verona', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (9, 'description9', 'black-bean', 15.50, 'Veranda', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (10, 'description10', 'black-bean', 15.50, 'Lemonade Blueberry Beans', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (11, 'description11', 'black-bean', 15.50, 'French', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (12, 'description12', 'black-bean', 15.50, 'Italian', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (13, 'description13', 'black-bean', 15.50, 'Dark roast', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (14, 'description14', 'black-bean', 15.50, 'Very Dark Roast', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (15, 'description15', 'black-bean', 15.50, 'Black', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (16, 'description16', 'black-bean', 15.50, 'mediocre cheap beans', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (17, 'description16', 'black-bean', 150.50, 'Kopi Luwak', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (18, 'description18', 'black-bean', 15.50, 'Dark Matter name', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (19, 'description19', 'black-bean', 15.50, 'Unicorn blood', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (20, 'description20', 'black-bean', 15.50, 'Juice of Tree', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (21, 'description21', 'black-bean', 15.50, 'Death', 10);
INSERT INTO products (product_id, description, image, price, product_name, inventory) VALUES (22, 'description22', 'black-bean', 15.50, 'Random name', 10);

INSERT INTO suppliers (supplier_id, supplier_name, supplier_phone) VALUES (1, 'supplier1', '111-111-1111');
INSERT INTO suppliers (supplier_id, supplier_name, supplier_phone) VALUES (2, 'supplier2', '111-111-1112');
INSERT INTO suppliers (supplier_id, supplier_name, supplier_phone) VALUES (3, 'supplier3', '111-111-1113');

INSERT INTO user (user_id, role, username, password, email, receive_emails, cart_id) VALUES (1, 'admin', 'username1', '$2a$10$nRfvJ7epiIt8SJ9SIiiIdePm7GcV/mP6kJ.e7X3TeGh2swehrYnw2', 'email1@email.com', false, 1);
INSERT INTO user (user_id, role, username, password, email, receive_emails, cart_id) VALUES (2, 'admin', 'username2', '$2a$10$nRfvJ7epiIt8SJ9SIiiIdePm7GcV/mP6kJ.e7X3TeGh2swehrYnw2', 'email2@email.com', false, 2);
INSERT INTO user (user_id, role, username, password, email, first_name, middle_name, last_name, customer_phone, receive_emails, cart_id)
VALUES (3, 'user', 'username3', '$2a$10$nRfvJ7epiIt8SJ9SIiiIdePm7GcV/mP6kJ.e7X3TeGh2swehrYnw2', 'email3@email.com', 'firstname3', 'middlename3', 'lastname3', '111-111-1113', true, 3);
INSERT INTO user (user_id, role, username, password, email, first_name, middle_name, last_name, customer_phone, receive_emails, cart_id)
VALUES (4, 'user', 'username4', '$2a$10$nRfvJ7epiIt8SJ9SIiiIdePm7GcV/mP6kJ.e7X3TeGh2swehrYnw2', 'email4@email.com', 'firstname4', 'middlename4', 'lastname4', '111-111-1114', true, 4);

INSERT INTO carts (cart_id) VALUES (1);
INSERT INTO carts (cart_id) VALUES (2);
INSERT INTO carts (cart_id) VALUES (3);
INSERT INTO carts (cart_id) VALUES (4);

INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (1, 20, 3, 1);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (2, 3, 3, 2);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (3, 3, 3, 3);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (4, 3, 4, 4);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (5, 3, 4, 5);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (6, 3, 4, 6);
INSERT INTO cart_items (cart_item_id, quantity, cart_id, product_id) VALUES (7, 3, 3, 6);

INSERT INTO supplier_product (supplier_id, product_id) VALUES (1, 1);
INSERT INTO supplier_product (supplier_id, product_id) VALUES (1, 2);
INSERT INTO supplier_product (supplier_id, product_id) VALUES (1, 3);
INSERT INTO supplier_product (supplier_id, product_id) VALUES (2, 1);
INSERT INTO supplier_product (supplier_id, product_id) VALUES (2, 4);
INSERT INTO supplier_product (supplier_id, product_id) VALUES (3, 1);

 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('1', 'best coffee ever', 'omg I\'m so awake', '5', '1', '1');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('2', 'best coffee ever', 'omg I\'m so awake', '4', '1', '2');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('3', 'best coffee ever', 'omg I\'m so awake', '4', '1', '3');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('4', 'best coffee ever', 'omg I\'m so awake', '5', '1', '4');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('5', 'best coffee ever', 'omg I\'m so awake', '1', '2', '1');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('6', 'best coffee ever', 'omg I\'m so awake', '2', '2', '2');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('7', 'best coffee ever', 'omg I\'m so awake', '1', '2', '3');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('8', 'best coffee ever', 'omg I\'m so awake', '3', '2', '4');
 INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('9', 'best coffee ever', 'omg I\'m so awake', '3', '3', '1');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('10', 'best coffee ever', 'omg I\'m so awake', '4', '3', '2');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('11', 'best coffee ever', 'omg I\'m so awake', '4', '3', '3');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('12', 'best coffee ever', 'omg I\'m so awake', '2', '3', '4');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('13', 'best coffee ever', 'omg I\'m so awake', '5', '4', '1');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('14', 'best coffee ever', 'omg I\'m so awake', '1', '4', '2');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('15', 'best coffee ever', 'omg I\'m so awake', '1', '4', '3');
INSERT INTO reviews (review_id, headline, review_body, stars, product_id, user_id) VALUES ('16', 'best coffee ever', 'omg I\'m so awake', '4', '4', '4');

INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (1, 3, '1 Main St', 'Chicago', 'IL', '60654', 1);
INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (2, 3, '2 Main St', 'Chicago', 'IL', '60654', 1);
INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (3, 3, '3 Main St', 'Chicago', 'IL', '60654', 1);
INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (4, 4, '4 Main St', 'Chicago', 'IL', '60654', 1);
INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (5, 4, '5 Main St', 'Chicago', 'IL', '60654', 1);
INSERT INTO addresses (address_id, user_id, street, city, state, zipcode, display) VALUES (6, 4, '6 Main St', 'Chicago', 'IL', '60654', 1);