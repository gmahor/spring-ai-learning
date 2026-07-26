package com.learnai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("ChatMemoryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestHeader("username") String username, @RequestParam("msg") String message) {
        return ResponseEntity.ok(
                chatClient.prompt()
                        .user(message)
                        .advisors(advisorSpec ->
                                advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                        .call()
                        .content()
        );
    }


}
