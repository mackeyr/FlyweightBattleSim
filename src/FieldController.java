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
        field.setFocusTraversable(true);

        createCell("cell1", new Image("file:cell1.png"), 10, new Point2D(300, 300));
        createCell("cell2", new Image("file:cell2.png"), 8, new Point2D(200, 200));
        createCell("cell3", new Image("file:cell3.png"), 12, new Point2D(400, 400));
        createCell("cell4", new Image("file:cell4.png"), 14, new Point2D(200, 400));
        createCell("cell5", new Image("file:cell5.png"), 6, new Point2D(400, 200));

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
    }
}

