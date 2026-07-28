package com.learnai.confg;

import com.learnai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSearchRAGChatClientConfig {

    @Bean("webSearchRAGChatClient")
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder.defaultAdvisors(List.of(loggerAdvisor, tokenUsageAdvisor, memoryAdvisor))
                .build();
    }


}
