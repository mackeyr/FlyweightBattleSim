/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierType
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * SoldierType purpose: specifies the attributes of the soldier
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:44 AM
 */
public class CellType {
    private String name;
    private Image image;
    private double size;
    public CellType(String name, Image image, double size) {
        this.name = name;
        this.image = image;
        this.size = size;
    }


}

