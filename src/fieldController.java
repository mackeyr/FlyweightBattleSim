/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class fieldController
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
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
public class fieldController {
    private final static double NUM_SOLDIERS = 1000000;
    private final static double NUM_TEAMS = 2;
    private final static int CANVAS_SIZE = 500;
    public List<Soldier> soldiers = new ArrayList<>();
    public Pane field;

    @FXML
    public void initialize() {
        field.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(forestgreen, 20%), derive(forestgreen, -40%));");
        for (int i = 0; i < Math.floor(NUM_SOLDIERS/NUM_TEAMS); i++){
            Point2D location = new Point2D(random(0, CANVAS_SIZE), random(0, CANVAS_SIZE));
            createSoldier(location, "RED", 0, 10, 10, 100);
            location = new Point2D(random(0, CANVAS_SIZE), random(0, CANVAS_SIZE));
            createSoldier(location, "BLUE", 1, 10, 10, 100);
        }
        Math.random();
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
        for (Soldier solder : soldiers){
            Circle circle = new Circle();
            circle.setRadius(1);
            solder.draw(circle);
            field.getChildren().add(circle);
        }
    }
}

