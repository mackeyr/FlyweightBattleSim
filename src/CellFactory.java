/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class SoldierFactory
 * Name: schreibert
 * Created 10/25/2023
 */

import javafx.scene.image.Image;

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
public class CellFactory {
    private static Map<String, CellType> cellTypes = new HashMap<>();
    public static CellType getCellType(String name, Image image, double size) {
        CellType result = cellTypes.get(name);
        if (result == null) {
            result = new CellType(name, image, size);
            cellTypes.put(name, result);
        }
        return result;
    }
}

