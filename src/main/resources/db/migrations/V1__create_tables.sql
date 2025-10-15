CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_value  NUMERIC NOT NULL
);


CREATE TABLE IF NOT EXISTS purchased_items (
    purchased_item_id SERIAL PRIMARY KEY,
    price NUMERIC NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    order_id BIGINT REFERENCES orders(order_id) ON DELETE CASCADE
);