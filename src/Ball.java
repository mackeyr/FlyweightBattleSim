/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class Ball
 * Name: schreibert
 * Created 11/1/2023
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
/**
 * Course SWE2410-121
 * Fall 2023-2024
 * Ball purpose: balling
 *
 * @author schreibert
 * @version created on 11/1/2023 at 3:21 PM
 */
class Ball {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private BallType type;

    public Ball(double x, double y, double velocityX, double velocityY, BallType type) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.type = type;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    public BallType getType() {
        return type;
    }
}

