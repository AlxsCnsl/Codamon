package com.example.codamon.core.Effect.Status;

public class StatusTools {

    public static AbstractStatus newStatus(Status status){
        return switch (status){
            case PARALYSIS -> new Paralysis();
            default -> null;
        };
    }

    public static Status stringToStatusEnum(String status){
        return switch (status){
            case "Paralysis" -> Status.PARALYSIS;
            default -> null;
        };
    }
}
