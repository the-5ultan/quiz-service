package com.quizora.quiz_service.feign;

import com.quizora.quiz_service.enums.Category;
import com.quizora.quiz_service.models.QuestionWrapper;
import com.quizora.quiz_service.models.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping(path = "generate")
    public ResponseEntity<List<Integer>> generateQuestion(@RequestParam Category category, @RequestParam Integer numQ);

    @PostMapping(path="getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds);

    @PostMapping(path="calculateScore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses);
}
