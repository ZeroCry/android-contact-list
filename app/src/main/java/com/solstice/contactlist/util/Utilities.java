package com.solstice.contactlist.util;

/**
 * Created by arielrey on 30/08/14.
 */
public class Utilities {
    /*
     * @param value receive the string to check
     * Verify If The string is empty or null
     */
    public static Boolean checkString(String value){
        if( !(value != null && !value.isEmpty()) ){
            return true;
        }else{
            return false;
        }
    }
}
