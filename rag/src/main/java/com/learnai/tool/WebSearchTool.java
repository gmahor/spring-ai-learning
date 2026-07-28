package com.learnai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WebSearchTool {

    @Tool(description = "Search the web for current information")
    public String searchWeb(String query) {

        // Call a search API here
        return "Search results for: " + query;
    }
}