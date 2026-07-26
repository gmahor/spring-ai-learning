CREATE TABLE SPRING_AI_CHAT_MEMORY
(
    conversation_id VARCHAR(36)                         NOT NULL,
    content         LONGTEXT                            NOT NULL,
    type            VARCHAR(10)                         NOT NULL,
    timestamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_INDEX ON SPRING_AI_CHAT_MEMORY (conversation_id, timestamp DESC );

ALTER TABLE spring_ai_chat_memory
    ADD CONSTRAINT TYPE_CHECK CHECK ( type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));
