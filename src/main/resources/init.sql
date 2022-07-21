DROP DATABASE IF EXISTS kosta_market;
CREATE DATABASE kosta_market;
ALTER DATABASE kosta_market DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE kosta_market;

CREATE TABLE TBL_USER (
  user_id int PRIMARY KEY AUTO_INCREMENT,
  username varchar(255) UNIQUE,
  password varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  contact varchar(255) NOT NULL
);

CREATE TABLE TBL_ADDRESS (
  address_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int,
  delivery_place varchar(255) NOT NULL,
  is_default_address char(1) NOT NULL,
  FOREIGN KEY (user_id) references TBL_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE TBL_SELLER (
  seller_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int,
  business_id varchar(255) NOT NULL,
  FOREIGN KEY (user_id) references TBL_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE TBL_PRODUCT (
  product_id int PRIMARY KEY AUTO_INCREMENT,
  product_name varchar(255) NOT NULL,
  product_price int NOT NULL,
  product_img_file_name varchar(255) NOT NULL,
  product_img_path varchar(255) NOT NULL,
  product_description varchar(255) NOT NULL,
  product_quantity int NOT NULL
);

CREATE TABLE TBL_SELLER_PRODUCT (
  seller_product_id int PRIMARY KEY AUTO_INCREMENT,
  seller_id int,
  product_id int,
  FOREIGN KEY (seller_id) references TBL_SELLER(seller_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE
);

CREATE TABLE TBL_CATEGORY (
  category_id int PRIMARY KEY AUTO_INCREMENT,
  category_name varchar(255) UNIQUE
);

CREATE TABLE TBL_PRODUCT_CATEGORY (
  product_category_id int PRIMARY KEY AUTO_INCREMENT,
  product_id int,
  category_id int,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) references TBL_CATEGORY(category_id) ON DELETE CASCADE
);

CREATE TABLE TBL_ORDER (
  order_id int PRIMARY KEY AUTO_INCREMENT,
  product_id int,
  order_quantity int NOT NULL,
  order_price int NOT NULL,
  order_date date NOT NULL,
  payment_date date,
  payment_method int NOT NULL,
  order_state int NOT NULL,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE
);

CREATE TABLE TBL_USER_ORDER (
  user_order_id int PRIMARY KEY AUTO_INCREMENT,
  user_id int,
  product_id int,
  address_id int,
  FOREIGN KEY (user_id) references TBL_USER(user_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) references TBL_PRODUCT(product_id) ON DELETE CASCADE,
  FOREIGN KEY (address_id) references TBL_ADDRESS(address_id) ON DELETE CASCADE
);