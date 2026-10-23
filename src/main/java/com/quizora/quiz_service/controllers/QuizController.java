package com.quizora.quiz_service.controllers;

import com.quizora.quiz_service.enums.Category;
import com.quizora.quiz_service.models.QuestionWrapper;
import com.quizora.quiz_service.models.Response;
import com.quizora.quiz_service.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping(path="create")// The catagory down bellow can be changed to String this will also reduce some lines of code
    public ResponseEntity<String> createQuiz(@RequestParam Category category, @RequestParam int numQ, @RequestParam String title){
        return quizService.create(category, numQ, title);
    }

    @GetMapping(path="get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id){
        return quizService.getQuizById(id);
    }

    @PostMapping(path="submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }

    @GetMapping(path = "delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable int id){
        return quizService.removeQuiz(id);
    }
}
