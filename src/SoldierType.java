/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierType
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * SoldierType purpose: specifies the attributes of the soldier
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:44 AM
 */
public class SoldierType {
    private String name;
    private int team;
    private int damage;
    private int range;
    private int health;
    private Paint color;
    private static Point2D[] teamLocations = {new Point2D(FieldController.CANVAS_SIZE / 4, FieldController.CANVAS_SIZE / 12),
            new Point2D(FieldController.CANVAS_SIZE / 8, FieldController.CANVAS_SIZE / 2),
            new Point2D(FieldController.CANVAS_SIZE / 4, 11 * FieldController.CANVAS_SIZE / 12),
            new Point2D(3 * FieldController.CANVAS_SIZE / 4, FieldController.CANVAS_SIZE / 12),
            new Point2D(7 * FieldController.CANVAS_SIZE / 8, FieldController.CANVAS_SIZE / 2),
            new Point2D(3 * FieldController.CANVAS_SIZE / 4, 11 * FieldController.CANVAS_SIZE / 12)};

    public SoldierType(String name, int team, int damage, int range, int health) {
        this.name = name;
        this.team = team;
        this.damage = damage;
        this.range = range;
        this.health = health;
        switch (team) {
            case 0 -> color = Color.RED;
            case 1 -> color = Color.BLUE;
            case 2 -> color = Color.YELLOW;
            case 3 -> color = Color.CYAN;
            case 4 -> color = Color.MAGENTA;
            case 5 -> color = Color.BLACK;
            default -> color = null;
        }
    }

    public Circle draw(Circle circle, Point2D location) {
        circle.setCenterX(location.getX());
        circle.setCenterY(location.getY());
        circle.setFill(color);
        return circle;
    }

    public int getTeam() {
        return team;
    }

    public int getRange() {
        return range;
    }

    public int getHealth() {
        return health;
    }

    public void attack(Soldier target) {
        target.takeDamage(damage);
    }

    public Point2D[] getTeamLocations() {
        return teamLocations;
    }
}

