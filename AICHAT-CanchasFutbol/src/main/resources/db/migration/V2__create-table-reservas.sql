CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora TIME,
    nombre_cliente VARCHAR(100),
    telefono VARCHAR(20),
    cancha_id BIGINT,
    CONSTRAINT fk_cancha
        FOREIGN KEY (cancha_id)
        REFERENCES canchas(id)
);
