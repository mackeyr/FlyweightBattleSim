/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class Soldier
 * Name: schreibert
 * Created 10/25/2023
 */


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * Soldier purpose: Interface for the soldiers
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:35 AM
 */
public class Cell {
    private Point2D location;
    private Image image;
    private ImageView imageView;
    private CellType type;
    private int splits;

    public Cell(CellType type, Point2D location){
        this.type = type;
        image = type.getImage();
        imageView = new ImageView(image);
        this.location = location;
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(getSize());
        setLocation(location);
    }

    public Cell(CellType type, Point2D location, int splits) {
        this(type, location);
        this.splits = ++splits;
    }
    public void split() {
        type.split(this);
    }
    public void setLocation(Point2D location){
        this.location = location;
        imageView.setLayoutX(location.getX());
        imageView.setLayoutY(location.getY());
    }

    public Point2D getLocation() {
        return location;
    }

    public double getSize() {
        return type.getSize();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int incrementSplits() {
        return splits++;
    }
}


