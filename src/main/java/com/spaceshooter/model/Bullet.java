package com.spaceshooter.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends GameObject {

    private static final double SPEED = 10;

    public Bullet(double x, double y) {

        super(x, y, 6, 20);

        Rectangle bullet =
                new Rectangle(
                        width,
                        height
                );

        bullet.setFill(Color.LIME);

        view = bullet;

        updatePosition();
    }

    @Override
    public void update() {

        y -= SPEED;

        updatePosition();
    }

    private void updatePosition() {

        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public boolean isOffScreen() {

        return y + height < 0;
    }
}