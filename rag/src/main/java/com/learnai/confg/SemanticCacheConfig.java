package com.learnai.confg;

import io.qdrant.client.QdrantClient;
import org.springframework.ai.chat.cache.semantic.SemanticCache;
import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.ai.vectorstore.redis.cache.semantic.DefaultSemanticCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticCacheConfig {

//    This is using redis cache
//    @Bean
//    RedisClient redisClient(@Value("${spring.data.redis.host:localhost}") String host,
//                            @Value("${spring.data.redis.port:6379}") int port) {
//        return RedisClient.builder().hostAndPort(host, port).build();
//    }

    // This is using qdrant cache
    @Bean("cacheVectorStore")
    VectorStore cacheVectorStore(
            QdrantClient qdrantClient,
            EmbeddingModel embeddingModel
    ) {
        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName("ai-semantic-cache")
                .initializeSchema(true)
                .build();
    }

    //    This is using redis cache
//    @Bean
//    public SemanticCache semanticCache(RedisClient redisClient, EmbeddingModel embeddingModel) {
//        return DefaultSemanticCache.builder()
//                .jedisClient(redisClient)
//                .embeddingModel(embeddingModel)
//                .similarityThreshold(0.9)
//                .indexName("ai-semantic-cache")
//                .prefix("cache:")
//                .build();
//    }

    @Bean
    public SemanticCache semanticCache(@Qualifier("cacheVectorStore") VectorStore vectorStore, EmbeddingModel embeddingModel) {
        return DefaultSemanticCache.builder()
                .vectorStore(vectorStore)
                .embeddingModel(embeddingModel)
                .similarityThreshold(0.8)
                .build();
    }

    @Bean
    public SemanticCacheAdvisor semanticCacheAdvisor(SemanticCache semanticCache) {
        return SemanticCacheAdvisor.builder().cache(semanticCache).build();
    }


}
