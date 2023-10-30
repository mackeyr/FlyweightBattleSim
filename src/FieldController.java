/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class fieldController
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
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
public class FieldController {
    private final static double NUM_SOLDIERS = 10000;
    private final static double NUM_TEAMS = 6;
    public final static double CANVAS_SIZE = 600;
    public static List<Soldier> soldiers = new ArrayList<>();

    @FXML
    public Pane field;

    @FXML
    public void initialize() {
        field.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(white, 20%), derive(black, -40%));");
        field.setFocusTraversable(true);
        for (int i = 0; i < Math.floor(NUM_SOLDIERS/NUM_TEAMS); i++){
            createSoldier("A", 0, 10, 500, 10);
            createSoldier("B", 1, 10, 10, 10);
            createSoldier("C", 2, 10, 10, 10);
            createSoldier("D", 3, 10, 20, 10);
            createSoldier("E", 4, 10, 10, 10);
            createSoldier("F", 5, 10, 10, 10);
        }
        paint();
    }

    public void createSoldier(String name, int team, int damage, int range, int health) {
        SoldierType type = SoldierFactory.getSoldierType(name, team, damage, range, health);
        Soldier soldier = new Soldier(type);
        soldiers.add(soldier);
    }



    public void paint(){
        field.getChildren().clear();
        for (Soldier soldier: soldiers) {
            Circle circle = new Circle();
            circle.setRadius(5);
            soldier.draw(circle);
            field.getChildren().add(circle);
        }
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        List<Soldier> toRemove = new ArrayList<>();
        for (Soldier soldier: soldiers) {
            soldier.move();
            if (soldier.isDead) {
                toRemove.add(soldier);
                field.getChildren().remove(soldier.circle);
            }
        }
        soldiers.removeAll(toRemove);
    }

}

