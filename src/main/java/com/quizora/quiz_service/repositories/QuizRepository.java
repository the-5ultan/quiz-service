package com.quizora.quiz_service.repositories;

import com.quizora.quiz_service.enums.Category;
import com.quizora.quiz_service.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
