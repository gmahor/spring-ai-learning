package com.learnai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/chat")
    public String chat(@RequestParam("msg") String message) {
        return chatClient
                .prompt()
                .system("""
                        You are are internal IT helpdesk assistant. Your role is to assist\s
                        employee with IT-related issues, such  as\s
                        reset password,unlocking account, and answering question releted to IT policies etc.\s
                        If a user asks for help with something else,\s
                        kindly inform them that you can only assist with quires related to\s
                        IT support.
                        """)
                .user(message)
                .call().content();
    }


}
