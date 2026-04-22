package com.spring.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guardrail")
public class GuardRailController {

    private final ChatClient chatClient;

    public GuardRailController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultSystem("you are helpful and safe AI that does not promote violence and frauds")
                .defaultAdvisors(SafeGuardAdvisor.builder()
                        .sensitiveWords(List.of("kill","exploit","hate","attack"))

                .build())
                .build();
    }

    @GetMapping("/chat")
    public String simpleChat(@RequestParam(value = "message") String message){
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();


        return response;
    }

}
