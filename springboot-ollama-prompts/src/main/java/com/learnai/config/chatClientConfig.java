package com.learnai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class chatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
//                .defaultSystem("""
//                        You are in internal HR assistant. Your role is to help\s
//                        employee with questions related to HR policies, such  as\s
//                        leave policies, salary, benefits, code of conduct, etc.\s
//                        If a user asks for help with something else,\s
//                        kindly inform them that you can only assist with quires related to\s
//                        HR policies.
//                        """)
//                .defaultUser("How can you help me?")
                .build();
    }
}
