package com.learnai.controller;

import com.learnai.tool.WebSearchTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final ChatClient chatClient;

    private final ChatClient webSearchChatClient;

//        private final VectorStore vectorStore;

    private final WebSearchTool webSearchTool;

    @Value("classpath:promptTemplate/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Value("classpath:promptTemplate/systemPromptTemplate.st")
    Resource hrSystemTemplate;

    public RAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
                         @Qualifier("webSearchRAGChatClient") ChatClient webSearchChatClient,
//                ,          VectorStore vectorStore
                         WebSearchTool webSearchTool
    ) {
        this.chatClient = chatClient;
        this.webSearchChatClient = webSearchChatClient;
//                this.vectorStore = vectorStore;
        this.webSearchTool = webSearchTool;
    }

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
                                             @RequestParam("msg") String msg) {
//                SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3)
//                                .similarityThreshold(0.5).build();
//                List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//                String similarContext = similarDocs.stream()
//                                .map(doc -> doc.getText() != null ? doc.getText() : "")
//                                .collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient.prompt()
//                                .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate)
//                                                .param("documents", similarContext))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(msg).call().content();
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/document/chat")
    public ResponseEntity<String> documentChat(@RequestHeader("username") String username,
                                               @RequestParam("msg") String msg) {
//                SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(3)
//                                .similarityThreshold(0.5).build();
//                List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//                String similarContext = similarDocs.stream()
//                                .map(doc -> doc.getText() != null ? doc.getText() : "")
//                                .collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient.prompt()
//                                .system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate)
//                                                .param("documents", similarContext))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(msg)
                .call().content();
        return ResponseEntity.ok(answer);
    }


    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearch(@RequestHeader("username") String username,
                                            @RequestParam("msg") String msg) {
        String answer = webSearchChatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(msg)
                .tools(webSearchTool)
                .call().content();
        return ResponseEntity.ok(answer);
    }

}
