package com.example.mathias.virtualwaiter;

import android.nfc.FormatException;

public class MenuItemTags{
    public static int CHICKEN = 1;
    public static int BEEF = 2;
    public static int PORK = 3;
    public static int SOUP = 4;
    public static int SOFTDRINK = 5;
    public static int ALCOHOLICDRINK = 6;

    public static int valueOf(String strToConvert) throws FormatException
    {
        switch(strToConvert.toLowerCase()) {
            case "chicken":
                return CHICKEN;
            case "beef":
                return BEEF;
            case "pork":
                return PORK;
            case "soup":
                return SOUP;
            case "softdrink":
                return SOFTDRINK;
            case "alcoholicdrink":
                return ALCOHOLICDRINK;
            default:
                throw new FormatException();
        }
    }
}
