CREATE TABLE message_telegram (

    id BIGINT NOT NULL AUTO_INCREMENT,

    chat_id BIGINT NOT NULL,

    message_user TEXT,

    message_response TEXT,

    message_time DATETIME NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_message_chat
        FOREIGN KEY (chat_id)
        REFERENCES chat_telegram(id)
        ON DELETE CASCADE

);