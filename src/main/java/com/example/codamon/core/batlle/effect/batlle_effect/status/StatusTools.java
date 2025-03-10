package com.example.codamon.core.batlle.effect.batlle_effect.status;

public class StatusTools {

    public static Status newStatus(String name){
        return switch (name){
            case "Paralysis" -> new Paralysis();
            case "Burn" -> new Burn();
            case "Poison" -> new Poison();
            default -> null;
        };
    }

    public static String statusToColor(String name) {
        return switch (name) {
            case "Paralysis" -> "#FFFF00"; // Jaune
            case "Burn" -> "#FF4500"; // Orange-rouge
            case "Poison" -> "#800080"; // Violet
            default -> "#FFFFFF";
        };
    }

}
