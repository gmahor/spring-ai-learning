package com.learnai.confg;

import com.learnai.advisors.TokenUsageAuditAdvisor;
import com.learnai.ragconfig.PIIMaskingDocumentPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {


    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    @Bean("chatMemoryChatClient")
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory,
            RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder.defaultAdvisors(List.of(loggerAdvisor, tokenUsageAdvisor, memoryAdvisor,
                retrievalAugmentationAdvisor)).build();
    }

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder) {
        return RetrievalAugmentationAdvisor.builder()
//                This Query transformer will translate the msg/or query (hindi, telugu, kannada, tamil, english ) to english
                .queryTransformers(TranslationQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder.clone()).targetLanguage("english")
                        .build())
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(3).similarityThreshold(0.5).build())
//                This will hide the sensitive information from the response
                .documentPostProcessors(PIIMaskingDocumentPostProcessor.builder())
                .build();
    }
}

