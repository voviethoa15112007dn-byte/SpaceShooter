package com.spaceshooter.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Boss extends Enemy {

    private int bossHealth;

    private double bossDirection = 1;

    private double bossSpeed = 2;

    public Boss(double x, double y) {

        super(x, y);

        bossHealth = 20;

        createBossView();
    }

    private void createBossView() {

        Polygon boss = new Polygon();

        boss.getPoints().addAll(
                40.0, 0.0,
                80.0, 20.0,
                70.0, 45.0,
                55.0, 55.0,
                40.0, 45.0,
                25.0, 55.0,
                10.0, 45.0,
                0.0, 20.0
        );

        boss.setFill(Color.DARKRED);

        view = boss;

        width = 80;
        height = 55;

        update();
    }

    @Override
    public void update() {

        x += bossSpeed * bossDirection;

        if (x <= 50) {

            x = 50;

            bossDirection = 1;
        }

        if (x + width >= 1230) {

            x = 1230 - width;

            bossDirection = -1;
        }

        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    @Override
    public void takeDamage() {

        bossHealth--;
    }

    @Override
    public boolean isDestroyed() {

        return bossHealth <= 0;
    }

    public int getBossHealth() {

        return bossHealth;
    }
}