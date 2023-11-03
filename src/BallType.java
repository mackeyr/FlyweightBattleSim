/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class BallType
 * Name: schreibert
 * Created 11/3/2023
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * BallType purpose: type for ball
 *
 * @author schreibert
 * @version created on 11/3/2023 at 5:07 PM
 */
public class BallType {
    private double radius;
    private Image image;
    public BallType(Image image, double radius){
        this.radius = radius;
        this.image = image;
    }

    public double getRadius() {
        return radius;
    }

    public Image getBallImage() {
        return image;
    }
}

