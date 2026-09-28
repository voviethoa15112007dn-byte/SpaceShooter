package com.spaceshooter.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Player extends GameObject {

    private static final double SPEED = 8;

    public Player(double x, double y) {

        super(x, y, 60, 60);

        Polygon ship = new Polygon();

        ship.getPoints().addAll(
                30.0, 0.0,
                0.0, 50.0,
                20.0, 40.0,
                30.0, 60.0,
                40.0, 40.0,
                60.0, 50.0
        );

        ship.setFill(Color.LIME);

        view = ship;

        update();
    }

    public void moveLeft() {

        x -= SPEED;

        if (x < 0) {
            x = 0;
        }

        update();
    }

    public void moveRight(double screenWidth) {

        x += SPEED;

        if (x + width > screenWidth) {
            x = screenWidth - width;
        }

        update();
    }

    @Override
    public void update() {

        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public Bullet shoot() {

        return new Bullet(
                x + width / 2 - 3,
                y - 20
        );
    }
}