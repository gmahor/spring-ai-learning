package com.learnai.controller;

import org.springframework.ai.chat.client.ChatClient;
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
public class PromptTemplateController {

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

//    String promptTemplate = """
//            A customer named {customerName} sent the following message:
//            "{customerMsg}"
//
//            Write a polite and helpful response addressing the issue.
//            Maintain a professional tone and provide reassurance.
//
//            Response as if you're writing the email body only. Don't include subject,
//            signature
//            """;

    @Value("classpath:/promptTemplate/userPromptTemplate.st")
    private Resource promptTemplate;

    @GetMapping("/email")
    public String emailResp(@RequestParam("customerName") String customerName,
                       @RequestParam("customerMsg") String customerMsg) {
        return chatClient
                .prompt()
                .system("""
                        You are a professional customer service which help drafting email response\s
                        response to improve the productivity of the customer support team
                        """)
                .user(promptUserSpec -> promptUserSpec.text(promptTemplate)
                       .params(paramsData(customerName, customerMsg)))
                .call().content();
    }

    private Map <String,Object> paramsData(String customerName, String customerMsg){
        Map <String,Object> params = new HashMap<>();
        params.put("customerName", customerName);
        params.put("customerMsg", customerMsg);
        return params;
    }

}
