package com.learnai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {


    @Bean
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
        return MessageWindowChatMemory.builder().maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository).build();
    }

    @Bean("ChatMemoryChatClient")
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        return builder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
                .build();
    }
}
