package com.example.codamon.core.effect.batlle_effect.status;

public class StatusTools {

    public static Status newStatus(String name){
        return switch (name){
            case "Paralysis" -> new Paralysis();
            default -> null;
        };
    }
}
