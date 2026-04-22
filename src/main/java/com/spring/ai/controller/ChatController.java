package com.spring.ai.controller;

import com.spring.ai.dtos.MovieRecommendation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatClient chatClient;

    @Autowired
    ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/simple")
    public String simpleChat(@RequestParam(value = "message") String message){
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();


        return response;
    }

    @GetMapping("/movies")
    public List<MovieRecommendation> getMovieRecommendations(@RequestParam String genre, @RequestParam int count){

        return (List<MovieRecommendation>) chatClient.prompt()
                .user(u -> u.text("""
                 Recommend exactly {count} movies in the {genre} genre. For each movie, provide the accurate title, description, genre, rating, year, and actors.
                The rating should be a number between 0 and 10, and the year should be the year of the movie released. The data should be absolutely accurate.
                Everytime even the genre is same, the movies should be different.
                """).param("count", count).param("genre", genre)
                ).call()
                .entity(List.class);

    }
}
