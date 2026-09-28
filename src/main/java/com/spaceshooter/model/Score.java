package com.spaceshooter.model;

import java.time.LocalDateTime;

public class Score {

    private int id;

    private String playerName;

    private int score;

    private int level;

    private int wave;

    private LocalDateTime date;

    public Score() {
    }

    public Score(
            String playerName,
            int score,
            int level,
            int wave) {

        this.playerName = playerName;

        this.score = score;

        this.level = level;

        this.wave = wave;

        this.date = LocalDateTime.now();
    }

    public Score(
            int id,
            String playerName,
            int score,
            int level,
            int wave,
            LocalDateTime date) {

        this.id = id;

        this.playerName = playerName;

        this.score = score;

        this.level = level;

        this.wave = wave;

        this.date = date;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getPlayerName() {

        return playerName;
    }

    public void setPlayerName(
            String playerName) {

        this.playerName = playerName;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public int getLevel() {

        return level;
    }

    public void setLevel(int level) {

        this.level = level;
    }

    public int getWave() {

        return wave;
    }

    public void setWave(int wave) {

        this.wave = wave;
    }

    public LocalDateTime getDate() {

        return date;
    }

    public void setDate(
            LocalDateTime date) {

        this.date = date;
    }
}