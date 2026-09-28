package com.spaceshooter.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyBullet extends GameObject {

    private static final double SPEED = 6;

    public EnemyBullet(double x, double y) {

        super(x, y, 6, 18);

        Rectangle bullet =
                new Rectangle(
                        width,
                        height
                );

        bullet.setFill(Color.RED);

        view = bullet;

        updatePosition();
    }

    @Override
    public void update() {

        y += SPEED;

        updatePosition();
    }

    private void updatePosition() {

        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public boolean isOffScreen(
            double screenHeight) {

        return y > screenHeight;
    }
}