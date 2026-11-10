package com.visualsql.backend.util;

/**
 * General class to help normalize all table, column names to avoid any case mismatches while representing the tables. Also polishes '"', '`'
 */
public final class TableID {
    private TableID(){
    }

    public static String normalize(String name){
        return name.replace("\"", "").replace("`", "").toLowerCase();
    }
}