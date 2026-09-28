package com.spaceshooter.service;

import com.spaceshooter.model.Score;
import com.spaceshooter.repository.ScoreRepository;

import java.util.List;

public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService() {

        scoreRepository =
                new ScoreRepository();
    }

    public void saveScore(
            String playerName,
            int score,
            int level,
            int wave) {

        Score scoreData =
                new Score(
                        playerName,
                        score,
                        level,
                        wave
                );

        scoreRepository.save(
                scoreData
        );
    }

    public List<Score> getTopScores() {

        return scoreRepository
                .findTopScores();
    }
}