package com.spaceshooter.controller;

import com.spaceshooter.model.Score;
import com.spaceshooter.service.ScoreService;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class MenuController {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private final Pane root;

    private Runnable startAction;
    private Runnable exitAction;
    private Runnable leaderboardAction;
    private Runnable howToPlayAction;
    private Runnable backAction;

    public MenuController() {

        root = new Pane();

        createBackground();

        createMainMenu();
    }

    public Pane getRoot() {

        return root;
    }

    private void createBackground() {

        Rectangle background =
                new Rectangle(
                        WIDTH,
                        HEIGHT
                );

        background.setFill(
                Color.rgb(
                        3,
                        6,
                        18
                )
        );

        root.getChildren().add(
                background
        );

        createStars();
    }

    private void createStars() {

        for (int i = 0; i < 100; i++) {

            double x =
                    Math.random() * WIDTH;

            double y =
                    Math.random() * HEIGHT;

            double size =
                    1 + Math.random() * 2;

            Circle star =
                    new Circle(
                            x,
                            y,
                            size,
                            Color.WHITE
                    );

            star.setOpacity(
                    0.3 +
                    Math.random() * 0.7
            );

            root.getChildren().add(star);
        }
    }

    private void createMainMenu() {

        Label title =
                createTitle(
                        "SPACE SHOOTER"
                );

        title.setLayoutX(390);

        title.setLayoutY(100);

        Label subtitle =
                new Label(
                        "GALACTIC DEFENSE FORCE"
                );

        subtitle.setTextFill(
                Color.CYAN
        );

        subtitle.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );

        subtitle.setLayoutX(500);

        subtitle.setLayoutY(175);

        Button startButton =
                createButton(
                        "START GAME"
                );

        Button leaderboardButton =
                createButton(
                        "LEADERBOARD"
                );

        Button howToPlayButton =
                createButton(
                        "HOW TO PLAY"
                );

        Button exitButton =
                createButton(
                        "EXIT"
                );

        double buttonX = 515;

        startButton.setLayoutX(buttonX);
        startButton.setLayoutY(250);

        leaderboardButton.setLayoutX(buttonX);
        leaderboardButton.setLayoutY(320);

        howToPlayButton.setLayoutX(buttonX);
        howToPlayButton.setLayoutY(390);

        exitButton.setLayoutX(buttonX);
        exitButton.setLayoutY(460);

        startButton.setOnAction(
                event -> {

                    if (startAction != null) {
                        startAction.run();
                    }
                }
        );

        leaderboardButton.setOnAction(
                event -> {

                    if (leaderboardAction != null) {
                        leaderboardAction.run();
                    }
                }
        );

        howToPlayButton.setOnAction(
                event -> {

                    if (howToPlayAction != null) {
                        howToPlayAction.run();
                    }
                }
        );

        exitButton.setOnAction(
                event -> {

                    if (exitAction != null) {
                        exitAction.run();
                    }
                }
        );

        Label version =
                new Label(
                        "VERSION 1.0"
                );

        version.setTextFill(
                Color.GRAY
        );

        version.setStyle(
                "-fx-font-size: 12px;"
        );

        version.setLayoutX(590);

        version.setLayoutY(650);

        root.getChildren().addAll(
                title,
                subtitle,
                startButton,
                leaderboardButton,
                howToPlayButton,
                exitButton,
                version
        );
    }

    private Label createTitle(
            String text) {

        Label title =
                new Label(text);

        title.setTextFill(
                Color.LIME
        );

        title.setStyle(
                "-fx-font-size: 52px;" +
                "-fx-font-weight: bold;"
        );

        return title;
    }

    private Button createButton(
            String text) {

        Button button =
                new Button(text);

        button.setPrefWidth(250);

        button.setPrefHeight(50);

        button.setTextFill(
                Color.WHITE
        );

        button.setStyle(
                "-fx-background-color: #101b35;" +
                "-fx-border-color: #00ff66;" +
                "-fx-border-width: 2px;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        return button;
    }

    public void showLeaderboard() {

        root.getChildren().clear();

        createBackground();

        Label title =
                createTitle(
                        "LEADERBOARD"
                );

        title.setLayoutX(470);

        title.setLayoutY(70);

        root.getChildren().add(title);

        ScoreService scoreService =
                new ScoreService();

        List<Score> scores =
                scoreService.getTopScores();

        VBox scoreBox =
                new VBox(12);

        scoreBox.setAlignment(
                Pos.CENTER_LEFT
        );

        scoreBox.setLayoutX(380);

        scoreBox.setLayoutY(170);

        if (scores.isEmpty()) {

            Label empty =
                    new Label(
                            "NO SCORES YET"
                    );

            empty.setTextFill(
                    Color.WHITE
            );

            empty.setStyle(
                    "-fx-font-size: 24px;"
            );

            scoreBox.getChildren().add(
                    empty
            );

        } else {

            int rank = 1;

            for (Score score : scores) {

                Label label =
                        new Label(
                                String.format(
                                        "%02d     %-15s     %06d",
                                        rank,
                                        score.getPlayerName(),
                                        score.getScore()
                                )
                        );

                label.setTextFill(
                        Color.WHITE
                );

                label.setStyle(
                        "-fx-font-size: 20px;" +
                        "-fx-font-family: monospace;"
                );

                scoreBox.getChildren().add(
                        label
                );

                rank++;
            }
        }

        Button backButton =
                createButton(
                        "BACK"
                );

        backButton.setLayoutX(515);

        backButton.setLayoutY(580);

        backButton.setOnAction(
                event -> {

                    if (backAction != null) {
                        backAction.run();
                    }
                }
        );

        root.getChildren().addAll(
                scoreBox,
                backButton
        );
    }

    public void showHowToPlay() {

        root.getChildren().clear();

        createBackground();

        Label title =
                createTitle(
                        "HOW TO PLAY"
                );

        title.setLayoutX(455);

        title.setLayoutY(70);

        Label controls =
                new Label(
                        """
                        CONTROLS

                        ← / A       MOVE LEFT
                        → / D       MOVE RIGHT
                        SPACE       SHOOT

                        ─────────────────────────────

                        MISSION

                        Destroy all enemy ships.
                        Survive enemy attacks.
                        Complete all 6 waves.
                        Defeat the final BOSS.

                        ─────────────────────────────

                        SCORING

                        NORMAL ENEMY       +100
                        BOSS              +1000
                        """
                );

        controls.setTextFill(
                Color.WHITE
        );

        controls.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-family: monospace;"
        );

        controls.setLayoutX(410);

        controls.setLayoutY(175);

        Button backButton =
                createButton(
                        "BACK"
                );

        backButton.setLayoutX(515);

        backButton.setLayoutY(590);

        backButton.setOnAction(
                event -> {

                    if (backAction != null) {
                        backAction.run();
                    }
                }
        );

        root.getChildren().addAll(
                title,
                controls,
                backButton
        );
    }

    public void setStartAction(
            Runnable action) {

        this.startAction = action;
    }

    public void setExitAction(
            Runnable action) {

        this.exitAction = action;
    }

    public void setLeaderboardAction(
            Runnable action) {

        this.leaderboardAction = action;
    }

    public void setHowToPlayAction(
            Runnable action) {

        this.howToPlayAction = action;
    }

    public void setBackAction(
            Runnable action) {

        this.backAction = action;
    }
}