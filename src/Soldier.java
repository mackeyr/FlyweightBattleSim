/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class Soldier
 * Name: schreibert
 * Created 10/25/2023
 */


import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * Soldier purpose: Interface for the soldiers
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:35 AM
 */
public class Soldier {
    private Point2D location;
    public Circle circle;
    private static int speed = 10;
    public SoldierType type;
    private int health;
    public boolean isDead = false;
    private Soldier target;

    public Soldier(SoldierType type){
        this.type = type;
        health = type.getHealth();

        double theta = random(0, 360);
        double r = random(0, 50);
        location = type.getTeamLocations()[type.getTeam()].add(r * Math.cos(theta), r * Math.sin(theta));
    }
    public void draw(Circle circle){
        this.circle = type.draw(circle, location);
    }

    public void move() {
        if (target == null || target.isDead) {
            target = chooseTarget();
        }

        if (target != null) {
            if (target.getLocation().distance(location) < type.getRange()) {
                type.attack(target);
            } else {
                double distance = getLocation().distance(target.getLocation());
                if (distance > type.getRange()) {
                    double xChange = speed * (getLocation().getX() - target.getLocation().getX()) / distance;
                    double yChange = speed * (getLocation().getY() - target.getLocation().getY()) / distance;
                    location = getLocation().subtract(xChange, yChange);
                }
            }
        }
        circle.setCenterX(location.getX());
        circle.setCenterY(location.getY());
    }

    public int getTeam() {
        return type.getTeam();
    }

    public Point2D getLocation() {
        return location;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }

    private static double random(int min, int max) {
        return min + (Math.random() * ((max - min) + 1));
    }

    public Soldier chooseTarget() {
        Soldier target = null;
        for (Soldier soldier : FieldController.soldiers) {
            if (soldier.getTeam() != getTeam()) {
                if (target == null || target.getLocation().distance(location) >
                        soldier.getLocation().distance(location)) {
                    target = soldier;
                }
            }
        }
        return target;
    }
}


