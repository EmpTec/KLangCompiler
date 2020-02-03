package de.hsrm.compiler.Klang.helper;

public class Helper {
    public static String getErrorPrefix(int line, int col) {
        return "Error in line " + line + ":" + col + " ";
    }
}