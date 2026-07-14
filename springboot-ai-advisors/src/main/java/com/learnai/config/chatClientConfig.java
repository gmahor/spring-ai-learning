package com.learnai.config;

import com.learnai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class chatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){

        return builder
                .defaultOptions(OllamaChatOptions.builder()
                        .model("llama3.2")
                        .temperature(0.8)
                        .maxTokens(100))
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor()))
//                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("""
                        You are in internal HR assistant. Your role is to help\s
                        employee with questions related to HR policies, such  as\s
                        leave policies, salary, benefits, code of conduct, etc.\s
                        If a user asks for help with something else,\s
                        kindly inform them that you can only assist with quires related to\s
                        HR policies.
                        """)
                .defaultUser("How can you help me?")
                .build();
    }
}
