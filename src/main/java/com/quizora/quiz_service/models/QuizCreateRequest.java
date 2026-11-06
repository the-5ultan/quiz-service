package com.quizora.quiz_service.models;

import com.quizora.quiz_service.enums.Category;
import lombok.Data;

@Data
public class QuizCreateRequest {

    private Category category;
    private int numQ;
    private String title;
}