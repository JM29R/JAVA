INSERT INTO usuarios (user, pass)
SELECT 'admin', '$2a$12$cWw1IG1/LrkPLUdB86nen.kMBCZ8HZWg3rBI1SuULhNcn2p6aPsTm'
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE user = 'admin'
);