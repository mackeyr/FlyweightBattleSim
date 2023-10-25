/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class fieldController
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
public class fieldController {
    private final static double NUM_SOLDIERS = 60;
    private final static double NUM_TEAMS = 6;
    private final static int CANVAS_SIZE = 800;
    public static List<Soldier> soldiers = new ArrayList<>();

    @FXML
    public Pane field;

    @FXML
    public void initialize() {
        field.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(white, 20%), derive(black, -40%));");
        field.setFocusTraversable(true);
        for (int i = 0; i < Math.floor(NUM_SOLDIERS/NUM_TEAMS); i++){
            Point2D location = new Point2D(random(0, CANVAS_SIZE/2), random(0, CANVAS_SIZE/3));
            createSoldier(location, "A", 0, 10, 35, 100);
            location = new Point2D(random(0, CANVAS_SIZE/2), random(CANVAS_SIZE/3, (2*CANVAS_SIZE)/3));
            createSoldier(location, "B", 1, 10, 10, 100);
            location = new Point2D(random(0, CANVAS_SIZE/2), random((2*CANVAS_SIZE)/3, CANVAS_SIZE));
            createSoldier(location, "C", 2, 10, 10, 100);
            location = new Point2D(random(CANVAS_SIZE/2, CANVAS_SIZE), random(0, CANVAS_SIZE/3));
            createSoldier(location, "D", 3, 10, 20, 100);
            location = new Point2D(random(CANVAS_SIZE/2, CANVAS_SIZE), random(CANVAS_SIZE/3, (2*CANVAS_SIZE)/3));
            createSoldier(location, "E", 4, 10, 10, 100);
            location = new Point2D(random(CANVAS_SIZE/2, CANVAS_SIZE), random((2*CANVAS_SIZE)/3, CANVAS_SIZE));
            createSoldier(location, "F", 5, 10, 10, 100);
        }
        paint();
    }

    public void createSoldier(Point2D location, String name, int team, int damage, int range, int health) {
        SoldierType type = SoldierFactory.getSoldierType(name, team, damage, range, health);
        Soldier soldier = new Soldier(location, type);
        soldiers.add(soldier);
    }

    private static double random(int min, int max) {
        return min + (Math.random() * ((max - min) + 1));
    }

    public void paint(){
        field.getChildren().clear();
        for (Soldier solder : soldiers) {
            Circle circle = new Circle();
            circle.setRadius(1);
            solder.draw(circle);
            field.getChildren().add(circle);
        }
    }
    public void onMouseClicked(MouseEvent mouseEvent) {
        for (Soldier soldier: soldiers) {
            soldier.move();
            paint();
        }
    }
}

