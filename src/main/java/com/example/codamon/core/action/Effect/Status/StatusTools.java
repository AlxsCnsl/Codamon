package com.example.codamon.core.action.Effect.Status;

public class StatusTools {

    public static Status newStatus(StatusEnum status){
        switch (status){
            case PARALYSIS -> {
                return new Paralysis();
            }
            default -> {
                return null;
            }
        }
    }
}
