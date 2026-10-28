package com.quizora.quiz_service.services;

import com.quizora.quiz_service.enums.Category;
import com.quizora.quiz_service.feign.QuizInterface;
import com.quizora.quiz_service.models.QuestionWrapper;
import com.quizora.quiz_service.models.Quiz;
import com.quizora.quiz_service.models.Response;
import com.quizora.quiz_service.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> create(Category category, int NumQ, String title){
        try {
            List<Integer> questions = quizInterface.generateQuestion(category, NumQ).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionIds(questions);
            quizRepository.save(quiz);
            return new  ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new  ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(int id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<QuestionWrapper> questions = quiz.get().getQuestions();
            List<QuestionWrapper> questionWrappers = new ArrayList<>();
            for (Question question : questions) {
                QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),question.getQuestionTitle(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4());
                questionWrappers.add(questionWrapper);
            }
            return new ResponseEntity<>(questionWrappers,HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> calculateResult(int id, List<Response> responses) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<Question> questions = quiz.get().getQuestions();
            int score = 0;
            for(int i = 0; i < responses.size(); i++){
                if(questions.get(i).getRightOption().equals(responses.get(i).getAnswer())){
                    score++;
                }
            }
            return new ResponseEntity<>(score+"",HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new  ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> removeQuiz(int id){
        try {
            quizRepository.deleteById(id);
            return new ResponseEntity<>("Quiz removed successfully", HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}
