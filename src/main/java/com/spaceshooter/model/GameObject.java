package com.spaceshooter.model;

import javafx.scene.Node;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected double width;
    protected double height;

    protected Node view;

    public GameObject(
            double x,
            double y,
            double width,
            double height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Node getView() {
        return view;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract void update();

    public boolean intersects(GameObject other) {

        return this.x < other.x + other.width
                && this.x + this.width > other.x
                && this.y < other.y + other.height
                && this.y + this.height > other.y;
    }
}