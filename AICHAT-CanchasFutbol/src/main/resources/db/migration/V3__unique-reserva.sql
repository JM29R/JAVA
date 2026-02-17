ALTER TABLE reservas
ADD CONSTRAINT uk_reserva_unica
UNIQUE (cancha_id, fecha, hora);
