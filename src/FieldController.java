/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class fieldController
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * fieldController purpose: controller for the GUI
 *
 * @author schreibert
 * @version created on 10/25/2023 at 1:32 PM
 */
public class FieldController {
    public static List<Cell> cells = new ArrayList<>();

    @FXML
    public Pane field;

    @FXML
    public void initialize() {
        field.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(white, 20%), derive(black, -40%));");
        field.setFocusTraversable(true);

        createCell("new", new Image("file:cell.png"), 20, new Point2D(300, 300));
        draw();
    }

    public void createCell(String name, Image image, double size, Point2D location) {
        CellType type = CellFactory.getCellType(name, image, size);
        Cell cell = new Cell(type, location);
        cells.add(cell);
    }

    public void draw() {
        for (Cell cell: cells) {
            ImageView image = cell.getImageView();
            if (!field.getChildren().contains(image)) {
                field.getChildren().add(image);
            }
        }
    }


    @FXML
    public void onKeyReleased(KeyEvent keyEvent) {
        int size = cells.size() - 1;
        for (int i = 0; i <= size; i++) {
            cells.get(i).split();
        }
        draw();
        System.out.println(cells.size());
    }
}

