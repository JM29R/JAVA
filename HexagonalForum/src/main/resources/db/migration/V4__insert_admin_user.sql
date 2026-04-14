INSERT INTO usuarios (nombre, password, activo, role)
SELECT 'admin', '$2a$10$bD7S5EzKbW6VRpc0d1W9xe9hOe3eqRAkfSPk4QV.vUEjovpo4uvK.', true, 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE nombre = 'admin'
);