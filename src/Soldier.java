/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class Soldier
 * Name: schreibert
 * Created 10/25/2023
 */


import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * Soldier purpose: Interface for the soldiers
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:35 AM
 */
public class Soldier {
    private Point2D location;
    public SoldierType type;
    public Soldier(Point2D location, SoldierType type){
        this.location = location;
        this.type = type;
    }
    public void draw(Circle circle){
        type.draw(circle, location);
    }

}

