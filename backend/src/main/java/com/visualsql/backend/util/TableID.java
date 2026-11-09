package com.visualsql.backend.util;

public final class TableID {
    private TableID(){
    }

    public static String normalize(String name){
        return name.replace("\"", "").replace("`", "").toLowerCase();
    }
}