package com.example.codamon.core.battle.move.category;

public class CathegoryTools {
    public CathegoryTools(){}

    public static Category stringToCategory(String string){
        return switch (string){
            case "Status" -> Category.STATUS;
            case "Special" -> Category.SPECIAL;
            case "Physical" -> Category.PHYSICAL;
            default ->
                    throw new IllegalStateException("Unexpected value: "
                            + string);
        };
    }
}
