CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contenido TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,

    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_respuesta_topico
        FOREIGN KEY (topico_id)
        REFERENCES topicos(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_respuesta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_respuestas_topico_id ON respuestas(topico_id);
CREATE INDEX idx_respuestas_usuario_id ON respuestas(usuario_id);