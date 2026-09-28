package com.spaceshooter.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Enemy extends GameObject {

    private double speed;
    private int health;
    private double direction;

    public Enemy(double x, double y) {

        super(x, y, 60, 50);

        speed = 1.5;
        health = 1;
        direction = 1;

        Polygon ship = new Polygon();

        ship.getPoints().addAll(
                0.0, 15.0,
                15.0, 0.0,
                30.0, 15.0,
                45.0, 0.0,
                60.0, 15.0,
                45.0, 35.0,
                30.0, 25.0,
                15.0, 35.0
        );

        ship.setFill(Color.RED);

        view = ship;

        updateView();
    }

    @Override
    public void update() {

        x += speed * direction;

        if (x <= 30) {

            x = 30;

            direction = 1;

            y += 10;
        }

        if (x + width >= 1250) {

            x = 1250 - width;

            direction = -1;

            y += 10;
        }

        updateView();
    }

    private void updateView() {

        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public void takeDamage() {

        health--;
    }

    public boolean isDestroyed() {

        return health <= 0;
    }

    public EnemyBullet shoot() {

        return new EnemyBullet(
                x + width / 2 - 3,
                y + height
        );
    }
}