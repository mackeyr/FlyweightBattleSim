/*
 * Course: SWE2410-121
 * Fall 2023-2024
 * File header contains class ObjectSizeFetcher
 * Name: schreibert
 * Created 11/3/2023
 */

import java.lang.instrument.Instrumentation;

/**
 * Course SWE2410-121
 * Fall 2023-2024
 * ObjectSizeFetcher purpose: mem
 *
 * @author schreibert
 * @version created on 11/3/2023 at 11:17 AM
 */
public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}

