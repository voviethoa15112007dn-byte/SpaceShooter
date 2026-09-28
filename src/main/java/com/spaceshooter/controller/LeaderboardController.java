package com.spaceshooter.controller;

import com.spaceshooter.model.Score;
import com.spaceshooter.service.ScoreService;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class LeaderboardController {

    private final VBox root;

    private final ScoreService scoreService;

    public LeaderboardController() {

        root = new VBox(15);

        root.setAlignment(
                Pos.CENTER
        );

        root.setPrefSize(
                1280,
                720
        );

        root.setStyle(
                "-fx-background-color: #050814;"
        );

        scoreService =
                new ScoreService();

        createLeaderboard();
    }

    private void createLeaderboard() {

        Label title =
                new Label(
                        "LEADERBOARD"
                );

        title.setTextFill(
                Color.LIME
        );

        title.setStyle(
                "-fx-font-size: 40px;" +
                "-fx-font-weight: bold;"
        );

        root.getChildren().add(
                title
        );

        List<Score> scores =
                scoreService
                        .getTopScores();

        if (scores.isEmpty()) {

            Label empty =
                    new Label(
                            "NO SCORES YET"
                    );

            empty.setTextFill(
                    Color.WHITE
            );

            root.getChildren().add(
                    empty
            );

            return;
        }

        int rank = 1;

        for (Score score : scores) {

            Label label =
                    new Label(
                            rank +
                            ". " +
                            score
                                    .getPlayerName() +
                            "    " +
                            score
                                    .getScore()
                    );

            label.setTextFill(
                    Color.WHITE
            );

            label.setStyle(
                    "-fx-font-size: 20px;"
            );

            root.getChildren().add(
                    label
            );

            rank++;
        }
    }

    public VBox getRoot() {

        return root;
    }
}