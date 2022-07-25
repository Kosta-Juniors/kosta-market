DROP DATABASE IF EXISTS kosta_market;
CREATE DATABASE kosta_market;
ALTER DATABASE kosta_market DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE kosta_market;

CREATE TABLE TBL_USER (
  user_id int PRIMARY KEY AUTO_INCREMENT,
  username varchar(255) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  contact varchar(255) NOT NULL
);

CREATE TABLE TBL_ADDRESS (
  address_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int NOT NULL,
  delivery_place varchar(255) NOT NULL,
  is_default_address char(1) NOT NULL,
  FOREIGN KEY (user_id) references TBL_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE TBL_SELLER (
  seller_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int NOT NULL,
  business_reg_no varchar(255) NOT NULL,
  FOREIGN KEY (user_id) references TBL_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE TBL_PRODUCT (
  product_id int PRIMARY KEY AUTO_INCREMENT,
  product_name varchar(255) NOT NULL,
  product_price int NOT NULL,
  product_img_file_name varchar(255) NOT NULL,
  product_img_path varchar(255) NOT NULL,
  product_description varchar(255) NOT NULL,
  product_quantity int NOT NULL,
  product_date datetime NOT NULL DEFAULT current_timestamp
);

CREATE TABLE TBL_SELLER_PRODUCT (
  seller_product_id int PRIMARY KEY AUTO_INCREMENT,
  seller_id int NOT NULL,
  product_id int NOT NULL,
  FOREIGN KEY (seller_id) references TBL_SELLER(seller_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE
);

CREATE TABLE TBL_CATEGORY (
  category_id int PRIMARY KEY AUTO_INCREMENT,
  category_name varchar(255) UNIQUE NOT NULL
);

CREATE TABLE TBL_PRODUCT_CATEGORY (
  product_category_id int PRIMARY KEY AUTO_INCREMENT,
  product_id int NOT NULL,
  category_id int NOT NULL,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) references TBL_CATEGORY(category_id) ON DELETE CASCADE
);

CREATE TABLE TBL_ORDER (
  order_id int PRIMARY KEY AUTO_INCREMENT,
  product_id int NOT NULL,
  order_quantity int NOT NULL,
  order_date datetime NOT NULL DEFAULT current_timestamp,
  order_state char(1) NOT NULL,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id)
);

CREATE TABLE TBL_PAYMENT (
  payment_id int PRIMARY KEY AUTO_INCREMENT,
  payment_price int NOT NULL,
  payment_method char(2) NOT NULL,
  payment_date datetime NOT NULL DEFAULT current_timestamp
);

CREATE TABLE TBL_ORDER_PAYMENT (
  order_payment_id int PRIMARY KEY AUTO_INCREMENT,
  order_id int NOT NULL,
  payment_id int NOT NULL,
  FOREIGN KEY (order_id) references TBL_ORDER(order_id),
  FOREIGN KEY (payment_id) references TBL_PAYMENT(payment_id)
);

CREATE TABLE TBL_USER_ORDER (
  user_order_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int NOT NULL,
  order_id int NOT NULL,
  address_id int NOT NULL,
  FOREIGN KEY (user_id) references TBL_USER(user_id),
  FOREIGN KEY (order_id) references TBL_ORDER(order_id),
  FOREIGN KEY (address_id) references TBL_ADDRESS(address_id) ON DELETE CASCADE
);