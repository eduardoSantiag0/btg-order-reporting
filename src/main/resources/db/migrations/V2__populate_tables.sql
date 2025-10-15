-- Inserindo 5 orders
INSERT INTO orders (order_id, customer_id, order_value) VALUES
(1011, 1, 110.00),
(1012, 2, 55.50),
(1013, 1, 200.00),
(1014, 3, 75.25),
(1015, 2, 180.00);

-- Inserindo itens para cada order
INSERT INTO purchased_items (order_id, product_name, quantity, price) VALUES

(1011, 'Lápis', 100, 1.10),
(1011, 'Caderno', 10, 1.00),

(1012, 'Borracha', 50, 0.50),
(1012, 'Caneta', 20, 1.25),

(1013, 'Mochila', 2, 50.00),
(1013, 'Estojo', 5, 10.00),

(1014, 'Caderno', 25, 1.50),
(1014, 'Caneta', 10, 1.25),

(1015, 'Calculadora', 1, 120.00),
(1015, 'Agenda', 3, 20.00);