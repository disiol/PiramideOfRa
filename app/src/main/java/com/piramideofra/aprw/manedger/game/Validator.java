package com.piramideofra.aprw.manedger.game;

public class Validator {

    public static void mustBeTrue(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Condition is not true, " + msg);
        }
    }
}
