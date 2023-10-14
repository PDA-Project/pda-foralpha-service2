package com.example.legendfive.overall.Service;

import com.example.legendfive.overall.Entity.Quiz;
import com.example.legendfive.overall.Entity.QuizRecord;
import com.example.legendfive.overall.Entity.User;
import com.example.legendfive.overall.dto.QuizDto;
import com.example.legendfive.overall.repository.QuizRecordRepository;
import com.example.legendfive.overall.repository.QuizRepository;
import com.example.legendfive.overall.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class QuizService {

    @Autowired
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizRecordRepository quizRecordRepository;

    private final Random random = new Random();
    public QuizDto.RandomQuizResponseDto getRandomQuiz() {
        Long randomQuizId = generateRandomIdInRange(1L, 2L);
        Quiz randomQuiz = quizRepository.findById(randomQuizId)
                .orElseThrow(() -> new NoSuchElementException("No quiz found with ID: " + randomQuizId));

        QuizDto.RandomQuizResponseDto randomResponseDto = QuizDto.RandomQuizResponseDto.builder()
                .id(randomQuiz.getId())
                .quizQuestion(randomQuiz.getQuizQuestion())
                .build();

        return randomResponseDto;
    }

    // 1에서 15 사이의 랜덤한 Long 값 생성 메서드
    private Long generateRandomIdInRange(Long min, Long max) {
        return min + (long) (Math.random() * (max - min + 1));
    }


    public QuizDto.QuizRecordResponseDto recordQuizResponse(UUID user, UUID quiz, boolean quizAnswer){

        System.out.println("user: " + user + ", quiz: " + quiz + ", quizAnswer: " + quizAnswer);

        User findUser = userRepository.findByUserUuid(user).orElseThrow();
        Quiz findQuiz = quizRepository.findByQuizUuId(quiz).orElseThrow();

        QuizRecord quizRecord = QuizRecord.builder()
                .user(findUser)
                .quiz(findQuiz)
                .isWon(quizAnswer)
                .build();

        quizRecordRepository.save(quizRecord);

        QuizDto.QuizRecordResponseDto quizRecordResponseDto = QuizDto.QuizRecordResponseDto.builder()
                .message("Quiz response recorded successfully.")
                .build();

        return quizRecordResponseDto;
    }


    public QuizDto.QuizAnswerResponseDto getQuizAnswerResponse(UUID quiz_uuid) {
        Quiz findQuiz = quizRepository.findByQuizUuId(quiz_uuid).orElseThrow();

        QuizDto.QuizAnswerResponseDto responseDto = QuizDto.QuizAnswerResponseDto.builder()
                .quizPoint(findQuiz.getQuizPoint())
                .quizQuestion(findQuiz.getQuizQuestion())
                .quizAnswer(findQuiz.isQuizAnswer())
                .quizExplanation(findQuiz.getQuizExplanation())
                .build();

        return responseDto;

    }


//    public List<QuizDto.QuizHistoryResponseDto> getQuizHistory(UUID userUuid) {
//        List<QuizDto.QuizHistoryEntity> quizHistoryEntities = quizHistoryRepository.findByUserUuid(userUuid);
//
//
//    }

    public List<QuizDto.QuizHistoryResponseDto> getQuizHistory(UUID userUuid) {
        User user = userRepository.findByUserUuid(userUuid).orElseThrow();

    }
}