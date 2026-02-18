CREATE TABLE mensajes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    telefono VARCHAR(255),
    mensaje_usuario TEXT,
    respuesta_sistema TEXT,
    intencion VARCHAR(255),
    fecha_hora TIMESTAMP
);