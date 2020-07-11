package com.gb.utils;

public class UtilFunctions {

    /**
     * Restituisce true se la stringa passata come argomento è
     * un intero non negativo, effettuandone il parsing.
     */
    public static boolean isPositiveInteger(String num) {
        try {
            int integer = Integer.parseInt(num);
            return integer > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isGeThanZero(String num) {
        try {
            int integer = Integer.parseInt(num);
            return integer >= 0;
        } catch (Exception e) {
            return false;
        }
    }

}
