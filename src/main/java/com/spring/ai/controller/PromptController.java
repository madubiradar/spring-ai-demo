package com.spring.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prompt")
public class PromptController {

    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/zero-shot")
    public String zeroShot(@RequestParam(value = "message") String message) {
        return chatClient.prompt()
                .user(u ->u.text("""
                        classify the sentiment of following text as exactly one of : positive, negative, or neutral.
                        Only respond with sentiment label, nothing else.
                        
                        Text: {message}
                        """).param("message", message))
                .call()
                .content();
    }

    @GetMapping("/few-shot")
    public String fewShot(@RequestParam(value = "message") String message) {
        return chatClient.prompt()
                .user(u ->u.text("""
                       Tell me time complexity of algorithm, based on algorithm name given.
                        only respond with time complexity, nothing else.
                        
                        Examples:
                        
                        Text: Mergesort
                        Time Complexity: O(n logn)
                        Text: Quicksort
                        Time Complexity: O(n logn)
                        Text: Bubble sort
                        Time Complexity: O(n^2)
                        
                        """).param("message", message))
                .call()
                .content();
    }

}
