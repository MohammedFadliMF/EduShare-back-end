package com.edushare.edushare_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class QuizAttempt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizAttemptId;

    @OneToOne
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<StudentAnswer> answers=new HashSet<>();

    private Double score;
    private LocalDateTime attemptDate;

    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Set<StudentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<StudentAnswer> answers) {
        this.answers = answers;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }
}
