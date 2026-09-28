package com.spaceshooter.controller;

import com.spaceshooter.model.Bullet;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.EnemyBullet;
import com.spaceshooter.model.Player;
import com.spaceshooter.service.GameService;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Set;

public class GameController {

    private static final double WIDTH = 1280;

    private static final double HEIGHT = 720;

    private final Pane root;

    private final GameService gameService;

    private final Label scoreLabel;

    private final Label livesLabel;

    private final Label waveLabel;

    private final Label levelLabel;

    private final Label missionLabel;

    private AnimationTimer gameLoop;

    private boolean paused;

    private boolean gameEnded;

    private Runnable backToMenuAction;

    public GameController() {

        root = new Pane();

        gameService =
                new GameService();

        scoreLabel =
                new Label();

        livesLabel =
                new Label();

        waveLabel =
                new Label();

        levelLabel =
                new Label();

        missionLabel =
                new Label();

        paused = false;

        gameEnded = false;

        createBackground();

        createHUD();

        gameService.createWave();

        addPlayer();

        addEnemies();

        startGameLoop();
    }

    public Pane getRoot() {

        return root;
    }

    public void setupKeyboard(
            Scene scene) {

        scene.setOnKeyPressed(
                event -> {

                    KeyCode key =
                            event.getCode();

                    if (key ==
                            KeyCode.ESCAPE) {

                        togglePause();

                        return;
                    }

                    if (paused ||
                            gameEnded) {

                        return;
                    }

                    Player player =
                            gameService
                                    .getPlayer();

                    if (key ==
                            KeyCode.LEFT ||
                            key ==
                            KeyCode.A) {

                        player.moveLeft();
                    }

                    if (key ==
                            KeyCode.RIGHT ||
                            key ==
                            KeyCode.D) {

                        player.moveRight(
                                WIDTH
                        );
                    }

                    if (key ==
                            KeyCode.SPACE) {

                        shoot();
                    }
                }
        );
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

        for (int i = 0;
             i < 130;
             i++) {

            double x =
                    Math.random()
                            * WIDTH;

            double y =
                    Math.random()
                            * HEIGHT;

            double size =
                    0.5 +
                    Math.random() * 2;

            Circle star =
                    new Circle(
                            x,
                            y,
                            size,
                            Color.WHITE
                    );

            star.setOpacity(
                    0.2 +
                    Math.random() * 0.8
            );

            root.getChildren().add(
                    star
            );
        }
    }

    private void createHUD() {

        scoreLabel.setText(
                "SCORE: 0"
        );

        styleHUD(
                scoreLabel,
                Color.LIME
        );

        scoreLabel.setLayoutX(25);

        scoreLabel.setLayoutY(18);

        livesLabel.setText(
                "LIVES: 3"
        );

        styleHUD(
                livesLabel,
                Color.WHITE
        );

        livesLabel.setLayoutX(25);

        livesLabel.setLayoutY(48);

        waveLabel.setText(
                "WAVE: 1 / 6"
        );

        styleHUD(
                waveLabel,
                Color.CYAN
        );

        waveLabel.setLayoutX(
                WIDTH / 2 - 70
        );

        waveLabel.setLayoutY(18);

        levelLabel.setText(
                "LEVEL: 1"
        );

        styleHUD(
                levelLabel,
                Color.YELLOW
        );

        levelLabel.setLayoutX(
                WIDTH - 150
        );

        levelLabel.setLayoutY(18);

        missionLabel.setText(
                "MISSION: DESTROY ENEMIES"
        );

        styleHUD(
                missionLabel,
                Color.ORANGE
        );

        missionLabel.setLayoutX(
                WIDTH / 2 - 150
        );

        missionLabel.setLayoutY(55);

        root.getChildren().addAll(
                scoreLabel,
                livesLabel,
                waveLabel,
                levelLabel,
                missionLabel
        );
    }

    private void styleHUD(
            Label label,
            Color color) {

        label.setTextFill(color);

        label.setStyle(
                "-fx-font-size: 19px;" +
                "-fx-font-weight: bold;"
        );
    }

    private void addPlayer() {

        Player player =
                gameService.getPlayer();

        root.getChildren().add(
                player.getView()
        );
    }

    private void addEnemies() {

        for (Enemy enemy :
                gameService
                        .getEnemies()) {

            if (!root.getChildren()
                    .contains(
                            enemy.getView()
                    )) {

                root.getChildren().add(
                        enemy.getView()
                );
            }
        }
    }

    private void shoot() {

        Bullet bullet =
                gameService
                        .playerShoot();

        root.getChildren().add(
                bullet.getView()
        );
    }

    private void startGameLoop() {

        gameLoop =
                new AnimationTimer() {

                    @Override
                    public void handle(
                            long now) {

                        if (paused ||
                                gameEnded) {

                            return;
                        }

                        gameService.update();

                        updateObjects();

                        updateHUD();

                        checkGameState();
                    }
                };

        gameLoop.start();
    }

    private void updateObjects() {

        Set<Node> activeObjects =
                new HashSet<>();

        activeObjects.add(
                gameService
                        .getPlayer()
                        .getView()
        );

        for (Bullet bullet :
                gameService
                        .getBullets()) {

            Node view =
                    bullet.getView();

            activeObjects.add(view);

            if (!root.getChildren()
                    .contains(view)) {

                root.getChildren().add(
                        view
                );
            }
        }

        for (Enemy enemy :
                gameService
                        .getEnemies()) {

            Node view =
                    enemy.getView();

            activeObjects.add(view);

            if (!root.getChildren()
                    .contains(view)) {

                root.getChildren().add(
                        view
                );
            }
        }

        for (EnemyBullet bullet :
                gameService
                        .getEnemyBullets()) {

            Node view =
                    bullet.getView();

            activeObjects.add(view);

            if (!root.getChildren()
                    .contains(view)) {

                root.getChildren().add(
                        view
                );
            }
        }

        root.getChildren().removeIf(
                node -> {

                    if (node ==
                            scoreLabel ||
                        node ==
                            livesLabel ||
                        node ==
                            waveLabel ||
                        node ==
                            levelLabel ||
                        node ==
                            missionLabel) {

                        return false;
                    }

                    if (node ==
                            gameService
                                .getPlayer()
                                .getView()) {

                        return false;
                    }

                    if (activeObjects
                            .contains(node)) {

                        return false;
                    }

                    return node instanceof
                            javafx.scene.shape.Polygon
                            ||
                            node instanceof
                            javafx.scene.shape.Rectangle;
                }
        );
    }

    private void updateHUD() {

        scoreLabel.setText(
                "SCORE: " +
                gameService.getScore()
        );

        livesLabel.setText(
                "LIVES: " +
                gameService.getLives()
        );

        waveLabel.setText(
                "WAVE: " +
                gameService
                        .getWave()
                        .getCurrentWave()
                +
                " / " +
                gameService
                        .getWave()
                        .getTotalWaves()
        );

        levelLabel.setText(
                "LEVEL: " +
                gameService.getLevel()
        );

        if (gameService
                .getWave()
                .isFinalWave()) {

            missionLabel.setText(
                    "MISSION: DEFEAT THE BOSS"
            );

        } else {

            missionLabel.setText(
                    "MISSION: DESTROY ENEMIES"
            );
        }
    }

    private void checkGameState() {

        if (gameService.isGameOver()) {

            gameEnded = true;

            gameLoop.stop();

            showGameOver();

            return;
        }

        if (gameService.isVictory()) {

            gameEnded = true;

            gameLoop.stop();

            showVictory();

            return;
        }

        int oldWave =
                gameService
                        .getWave()
                        .getCurrentWave();

        boolean changed =
                gameService
                        .checkWaveCompleted();

        if (changed) {

            int newWave =
                    gameService
                            .getWave()
                            .getCurrentWave();

            if (newWave != oldWave) {

                addEnemies();
            }
        }
    }

    private void togglePause() {

        if (gameEnded) {

            return;
        }

        paused = !paused;

        if (paused) {

            showPauseOverlay();

        } else {

            removePauseOverlay();
        }
    }

    private void showPauseOverlay() {

        if (root.lookup("#pauseOverlay")
                != null) {

            return;
        }

        Rectangle overlay =
                new Rectangle(
                        WIDTH,
                        HEIGHT
                );

        overlay.setId(
                "pauseOverlay"
        );

        overlay.setFill(
                Color.rgb(
                        0,
                        0,
                        0,
                        0.75
                )
        );

        Label pauseLabel =
                new Label(
                        "GAME PAUSED"
                );

        pauseLabel.setTextFill(
                Color.CYAN
        );

        pauseLabel.setStyle(
                "-fx-font-size: 55px;" +
                "-fx-font-weight: bold;"
        );

        pauseLabel.setLayoutX(480);

        pauseLabel.setLayoutY(270);

        Label info =
                new Label(
                        "PRESS ESC TO CONTINUE"
                );

        info.setTextFill(
                Color.WHITE
        );

        info.setStyle(
                "-fx-font-size: 18px;"
        );

        info.setLayoutX(500);

        info.setLayoutY(350);

        root.getChildren().addAll(
                overlay,
                pauseLabel,
                info
        );
    }

    private void removePauseOverlay() {

        root.getChildren()
                .removeIf(
                        node ->
                                node.getId() != null
                                &&
                                node.getId()
                                        .equals(
                                                "pauseOverlay"
                                        )
                );

        root.getChildren()
                .removeIf(
                        node ->
                                node instanceof Label
                                &&
                                (
                                    ((Label) node)
                                    .getText()
                                    .equals(
                                        "GAME PAUSED"
                                    )
                                    ||
                                    ((Label) node)
                                    .getText()
                                    .equals(
                                        "PRESS ESC TO CONTINUE"
                                    )
                                )
                );
    }

    private void showGameOver() {

        Rectangle overlay =
                new Rectangle(
                        WIDTH,
                        HEIGHT
                );

        overlay.setFill(
                Color.rgb(
                        0,
                        0,
                        0,
                        0.75
                )
        );

        Label title =
                new Label(
                        "GAME OVER"
                );

        title.setTextFill(
                Color.RED
        );

        title.setStyle(
                "-fx-font-size: 65px;" +
                "-fx-font-weight: bold;"
        );

        title.setLayoutX(465);

        title.setLayoutY(230);

        Label score =
                new Label(
                        "FINAL SCORE: " +
                        gameService.getScore()
                );

        score.setTextFill(
                Color.WHITE
        );

        score.setStyle(
                "-fx-font-size: 25px;"
        );

        score.setLayoutX(520);

        score.setLayoutY(320);

        Button restart =
                createOverlayButton(
                        "RESTART"
                );

        restart.setLayoutX(470);

        restart.setLayoutY(390);

        restart.setOnAction(
                event -> restartGame()
        );

        Button menu =
                createOverlayButton(
                        "MAIN MENU"
                );

        menu.setLayoutX(760);

        menu.setLayoutY(390);

        menu.setOnAction(
                event -> backToMenu()
        );

        root.getChildren().addAll(
                overlay,
                title,
                score,
                restart,
                menu
        );
    }

    private void showVictory() {

        Rectangle overlay =
                new Rectangle(
                        WIDTH,
                        HEIGHT
                );

        overlay.setFill(
                Color.rgb(
                        0,
                        0,
                        0,
                        0.70
                )
        );

        Label title =
                new Label(
                        "MISSION COMPLETE"
                );

        title.setTextFill(
                Color.LIME
        );

        title.setStyle(
                "-fx-font-size: 55px;" +
                "-fx-font-weight: bold;"
        );

        title.setLayoutX(390);

        title.setLayoutY(230);

        Label score =
                new Label(
                        "FINAL SCORE: " +
                        gameService.getScore()
                );

        score.setTextFill(
                Color.WHITE
        );

        score.setStyle(
                "-fx-font-size: 25px;"
        );

        score.setLayoutX(520);

        score.setLayoutY(320);

        Button restart =
                createOverlayButton(
                        "PLAY AGAIN"
                );

        restart.setLayoutX(470);

        restart.setLayoutY(390);

        restart.setOnAction(
                event -> restartGame()
        );

        Button menu =
                createOverlayButton(
                        "MAIN MENU"
                );

        menu.setLayoutX(760);

        menu.setLayoutY(390);

        menu.setOnAction(
                event -> backToMenu()
        );

        root.getChildren().addAll(
                overlay,
                title,
                score,
                restart,
                menu
        );
    }

    private Button createOverlayButton(
            String text) {

        Button button =
                new Button(text);

        button.setPrefWidth(200);

        button.setPrefHeight(50);

        button.setTextFill(
                Color.WHITE
        );

        button.setStyle(
                "-fx-background-color: #101b35;" +
                "-fx-border-color: #00ff66;" +
                "-fx-border-width: 2px;" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;"
        );

        return button;
    }

    private void restartGame() {

        root.getChildren().removeIf(
                node ->
                        node != scoreLabel
                        &&
                        node != livesLabel
                        &&
                        node != waveLabel
                        &&
                        node != levelLabel
                        &&
                        node != missionLabel
        );

        createBackground();

        addPlayer();

        gameService.reset();

        addEnemies();

        paused = false;

        gameEnded = false;

        startGameLoop();
    }

    private void backToMenu() {

        if (gameLoop != null) {

            gameLoop.stop();
        }

        if (backToMenuAction != null) {

            backToMenuAction.run();
        }
    }

    public void setBackToMenuAction(
            Runnable action) {

        this.backToMenuAction = action;
    }
}