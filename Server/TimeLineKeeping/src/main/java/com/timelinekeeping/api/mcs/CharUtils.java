package com.timelinekeeping.api.mcs;

/**
 * Created by lethanhtan on 11/18/16.
 */
public class CharUtils {
    /**
     * Convert the characters to ASCII value
     * @param character character
     * @return ASCII value
     */
    public static int CharToASCII(final char character){
        return (int)character;
    }

    /**
     * Convert the ASCII value to character
     * @param ascii ascii value
     * @return character value
     */
    public static char ASCIIToChar(final int ascii){
        return (char)ascii;
    }
}
