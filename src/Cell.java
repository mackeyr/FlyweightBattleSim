/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class Soldier
 * Name: schreibert
 * Created 10/25/2023
 */


import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * Soldier purpose: Interface for the soldiers
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:35 AM
 */
public class Cell{
    private Point2D location;
    public Image image;
    private static double size;
    public CellType type;

    public Cell(CellType type){
        this.type = type;
        size = type.getSize();
        image = type.getImage();
        location = new Point2D(0,0);
    }
    public void split(){
        Cell sisterCell = new Cell(type);
        sisterCell.setLocation(location);
    }
    public void setLocation(Point2D location){
        this.location = location;
    }
}


