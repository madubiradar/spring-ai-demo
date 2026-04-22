package com.spring.ai.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRecommendation {

    String title;
    String description;
    String genre;
    String rating;
    String year;
    List<String> actors;

}