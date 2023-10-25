/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierType
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.scene.paint.Color;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * SoldierType purpose: specifies the attributes of the soldier
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:44 AM
 */
public class SoldierType {
    private int team;
    private String name;
    private int damage;
    private int range;
    public SoldierType(String name, int team, int damage, int range){
        this.name = name;
        this.team = team;
        this.damage = damage;
        this.range = range;
    }

}

