CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT true,
    role VARCHAR(50) NOT NULL
);


CREATE INDEX idx_usuarios_nombre ON usuarios(nombre);