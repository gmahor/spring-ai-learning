package com.learnai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class TimeController {

    private final ChatClient chatClient;

    public TimeController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/local-time")
    public ResponseEntity<String> localTime(@RequestHeader("username") String username,
                                            @RequestParam("msg") String msg) {
        String answer = chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(msg)
                .call().content();
        return ResponseEntity.ok(answer);
    }
}
