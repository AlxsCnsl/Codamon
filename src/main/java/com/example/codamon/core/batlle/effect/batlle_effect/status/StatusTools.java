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
}
