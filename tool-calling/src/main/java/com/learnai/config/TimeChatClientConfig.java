package com.learnai.config;

import com.learnai.advisors.TokenUsageAuditAdvisor;
import com.learnai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TimeChatClientConfig {


    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    @Bean("timeChatClient")
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory, TimeTools timeTools) {
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(loggerAdvisor, tokenUsageAdvisor, memoryAdvisor)).build();
    }


}

