CREATE TABLE chat_telegram (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_chat BIGINT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_chat_telegram_id_chat (id_chat)


);