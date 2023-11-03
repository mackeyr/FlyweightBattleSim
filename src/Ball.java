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
    private double radius;
    private ImageView ballImage;

    public Ball(double x, double y, double velocityX, double velocityY, double radius) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        Image image = new Image("Table_tennis_ball.png");
        ballImage = new ImageView(image);
        ballImage.setFitWidth(2 * radius);
        ballImage.setFitHeight(2 * radius);

        // Set the initial position of the ImageView
        ballImage.setLayoutX(x - radius);
        ballImage.setLayoutY(y - radius);
    }
    public List<Ball> split() {
        List<Ball> balls = new ArrayList<>();
        double radius = getRadius() * 0.7;
        Ball newBall1 = new Ball(getX() - radius, getY(), -getVelocityX(), getVelocityY(), radius);
        Ball newBall2 = new Ball(getX() + radius, getY(), getVelocityX(), getVelocityY(), radius);
        balls.add(newBall1);
        balls.add(newBall2);
        return balls;
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

    public double getRadius() {
        return radius;
    }

    public ImageView getBallImage() {
        return ballImage;
    }
}

