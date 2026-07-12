CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_telegram BIGINT NOT NULL,
    pedido VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_id_telegram ON pedidos(id_telegram);