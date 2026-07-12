
ALTER TABLE pedidos
ADD COLUMN estado ENUM('Pendiente', 'Preparado', 'Entregado') NOT NULL DEFAULT 'Pendiente';
CREATE INDEX idx_pedidos_estado ON pedidos(estado);