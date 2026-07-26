package com.learnai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Value("classpath:/promptTemplate/systemPromptTemplate.st")
    Resource systemPromptTemplate;


    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("msg") String msg) {
        return chatClient.prompt()
                .options(OllamaChatOptions.builder().model(OllamaModel.LLAMA3_2).temperature(0.8))
                .system(systemPromptTemplate)
                .user(msg)
                .call()
                .content();
    }


}
