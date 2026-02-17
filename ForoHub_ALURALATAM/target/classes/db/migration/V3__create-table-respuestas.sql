CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    curso VARCHAR(255),
    fecha DATETIME NOT NULL,
    status BOOLEAN,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_respuesta_topico
        FOREIGN KEY (topico_id) REFERENCES topicos(id),

    CONSTRAINT fk_respuesta_autor
        FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

CREATE INDEX idx_respuestas_topico ON respuestas(topico_id);
CREATE INDEX idx_respuestas_autor ON respuestas(autor_id);
