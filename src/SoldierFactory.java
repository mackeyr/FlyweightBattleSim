/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierFactory
 * Name: schreibert
 * Created 10/25/2023
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * SoldierFactory purpose: creates the soldiers for the battlefield
 *
 * @author schreibert
 * @version created on 10/25/2023 at 11:58 AM
 */
public class SoldierFactory {
    private static Map<String, SoldierType> soldierTypes = new HashMap<>();
    public static SoldierType getSoldierType(String name, int team, int damage, int range, int health) {
        SoldierType result = soldierTypes.get(name);
        if (result == null) {
            result = new SoldierType(name, team, damage, range, health);
            soldierTypes.put(name, result);
        }
        return result;
    }
}

