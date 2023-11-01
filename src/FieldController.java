/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class fieldController
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.fxml.FXML;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
public class FieldController {;
    public static List<Soldier> cells = new ArrayList<>();

    @FXML
    public Pane field;

    @FXML
    public void initialize() {
        field.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(white, 20%), derive(black, -40%));");
        field.setFocusTraversable(true);
    }

    public void createCell(String name, Image image, double size) {
        CellType type = CellFactory.getCellType(name, image, size);
        Cell cell = new Cell(type);
        cells.add(cell);
    }
    

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        for (Cell cell: cells) {
            cell.split();
        }
    }

}

