/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierType
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

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

    public double getSize() {
        return size;
    }

    public Image getImage() {
        return image;
    }

    public void split(Cell cell) {
        int splits = cell.incrementSplits();
        Point2D change = new Point2D(splits % 2 == 0 ? size : 0, splits % 2 == 0 ? 0 : size).multiply(Math.sqrt(1 + splits));
        Cell newCell = new Cell(this, cell.getLocation().subtract(change), splits);
        cell.setLocation(cell.getLocation().add(change));
        FieldController.cells.add(newCell);
    }
}

