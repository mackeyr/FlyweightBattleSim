/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class BallFactory
 * Name: schreibert
 * Created 11/3/2023
 */

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * BallFactory purpose: factory
 *
 * @author schreibert
 * @version created on 11/3/2023 at 5:07 PM
 */
public class BallFactory {
    static Map<String, BallType> ballTypes = new HashMap<>();

    public static BallType getBallType(String name, Image image, double radius){
        BallType result = ballTypes.get(name);
        if (result == null){
            result = new BallType(image, radius);
            ballTypes.put(name, result);
        }
        return result;
    }
}

