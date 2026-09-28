package com.spaceshooter;

import com.spaceshooter.controller.GameController;
import com.spaceshooter.controller.MenuController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private Stage stage;

    @Override
    public void start(Stage stage) {

        this.stage = stage;

        stage.setTitle("SPACE SHOOTER");

        stage.setResizable(false);

        showMenu();

        stage.show();
    }

    private void showMenu() {

        MenuController menuController =
                new MenuController();

        menuController.setStartAction(
                this::startGame
        );

        menuController.setExitAction(
                () -> stage.close()
        );

        menuController.setLeaderboardAction(
                this::showLeaderboard
        );

        menuController.setHowToPlayAction(
                this::showHowToPlay
        );

        Scene scene =
                new Scene(
                        menuController.getRoot(),
                        WIDTH,
                        HEIGHT
                );

        stage.setScene(scene);

        scene.getRoot().requestFocus();
    }

    private void startGame() {

        GameController gameController =
                new GameController();

        gameController.setBackToMenuAction(
                this::showMenu
        );

        Scene scene =
                new Scene(
                        gameController.getRoot(),
                        WIDTH,
                        HEIGHT
                );

        gameController.setupKeyboard(scene);

        stage.setScene(scene);

        scene.getRoot().requestFocus();
    }

    private void showLeaderboard() {

        MenuController menuController =
                new MenuController();

        menuController.showLeaderboard();

        menuController.setBackAction(
                this::showMenu
        );

        Scene scene =
                new Scene(
                        menuController.getRoot(),
                        WIDTH,
                        HEIGHT
                );

        stage.setScene(scene);

        scene.getRoot().requestFocus();
    }

    private void showHowToPlay() {

        MenuController menuController =
                new MenuController();

        menuController.showHowToPlay();

        menuController.setBackAction(
                this::showMenu
        );

        Scene scene =
                new Scene(
                        menuController.getRoot(),
                        WIDTH,
                        HEIGHT
                );

        stage.setScene(scene);

        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {

        launch(args);
    }
}