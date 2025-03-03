package com.example.codamon.core;

import java.util.ArrayList;

public class TypeTools {
    private static final double[][] typeTable = {
            //        NOR  FIR  WAT  GRA  ELE  ICE  FIG  POI  GRO  FLY  PSY  BUG  ROC  GHO  DRA  DAR  STE  FAI
            /*NOR*/ { 1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,  0.5,   0,   1,   1,  0.5,   1 },
            /*FIR*/ { 1,  0.5, 0.5,  2,   1,   2,   1,   1,   1,   1,   1,   2,   0.5,  1,   0.5,  1,   2,   1 },
            /*WAT*/ { 1,   2,  0.5,  0.5,  1,   1,   1,   1,   2,   1,   1,   1,   2,   1,   0.5,  1,   1,   1 },
            /*GRA*/ { 1,  0.5,  2,  0.5,  1,   1,   1,  0.5,  2,  0.5,  1,  0.5,   2,   1,  0.5,  1,  0.5,   1 },
            /*ELE*/ { 1,   1,   2,  0.5,  0.5,  1,   1,   1,   0,   2,   1,   1,   1,   1,  0.5,  1,   1,   1 },
            /*ICE*/ { 1,  0.5,  0.5,  2,   1,  0.5,   1,   1,   2,   2,   1,   1,   1,   1,   2,   1,  0.5,   1 },
            /*FIG*/ { 2,   1,   1,   1,   1,   2,   1,  0.5,   1,   0.5,  0.5,  0.5,   2,   0,   1,   2,   2,  0.5 },
            /*POI*/ { 1,   1,   1,   2,   1,   1,   1,  0.5,  0.5,   1,   1,   1,   0.5,  0.5,  1,   1,   0,   2 },
            /*GRO*/ { 1,   2,   1,  0.5,   2,   1,   1,   2,   1,   0,   1,   0.5,   2,   1,   1,   1,   2,   1 },
            /*FLY*/ { 1,   1,   1,   2,  0.5,   1,   2,   1,   1,   1,   1,   2,   0.5,   1,   1,   1,   0.5,   1 },
            /*PSY*/ { 1,   1,   1,   1,   1,   1,   2,   2,   1,   1,  0.5,   1,   1,   1,   1,   0,   0.5,   1 },
            /*BUG*/ { 1,  0.5,  1,   2,   1,   1,  0.5,  0.5,   1,   0.5,   2,   1,   1,   0.5,   1,   2,   0.5,  0.5 },
            /*ROC*/ { 1,   2,   1,   1,   1,   2,  0.5,   1,   0.5,   2,   1,   2,   1,   1,   1,   1,   0.5,   1 },
            /*GHO*/ { 0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   2,   1,   1,   2,   1,   0.5,   1,   1 },
            /*DRA*/ { 1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   2,   1,   0.5,   0 },
            /*DAR*/ { 1,   1,   1,   1,   1,   1,  0.5,   1,   1,   1,   2,   1,   1,   2,   1,  0.5,   1,  0.5 },
            /*STE*/ { 1,  0.5,  0.5,   1,   1,   2,   1,   1,   1,   1,   1,   1,   2,   1,   1,   1,  0.5,   2 },
            /*FAI*/ { 1,  0.5,  1,   1,   1,   1,   2,  0.5,   1,   1,   1,   1,   1,   1,   2,   2,  0.5,   1 }
        };

    public static double getTypeEfficiency(Type attacker, Type defender){
        return typeTable[attacker.ordinal()][defender.ordinal()];
    }

    public static double getTypesEfficiency(Type attackerType, ArrayList<Type> defenderTypes){
        double coef = 0;
        for (Type defenderType : defenderTypes) {
            coef += getTypeEfficiency(attackerType, defenderType);
        }
        return coef;
    }

    public static ArrayList<Type> typeList(Type type){
        ArrayList<Type> list =  new ArrayList<>();
        list.add(type);
        return list;
    }

    public static String getEfficiencyAttackLog(
            Type attackerType, ArrayList<Type> defenderTypes){
        String log ;
        switch ((int)(getTypesEfficiency(attackerType, defenderTypes) * 100 )){
            case 400 -> log = "VERY SUPPER EFIC";
            case 200 -> log = "SUPPER EFIC";
            case 50 -> log = "NOT VERY EFIC";
            case 25 -> log = "VERY NOT EFIC";
            default -> log = "";
        }
        return log;
    }

    public static ArrayList<Type> typeList(Type type1, Type type2){
        ArrayList<Type> list =  new ArrayList<>();
        list.add(type1);list.add(type2);
        return list;
    }

    public static String typeToString(Type type) {
        return switch (type) {
            case NORMAL -> "Normal";
            case FIRE -> "Fire";
            case WATER -> "Water";
            case GRASS -> "Grass";
            case ELECTRIC -> "Electric";
            case ICE -> "Ice";
            case FIGHTING -> "Fighting";
            case POISON -> "Poison";
            case GROUND -> "Ground";
            case FLYING -> "Flying";
            case PSYCHIC -> "Psychic";
            case BUG -> "Bug";
            case ROCK -> "Rock";
            case GHOST -> "Ghost";
            case DRAGON -> "Dragon";
            case DARK -> "Dark";
            case STEEL -> "Steel";
            case FAIRY -> "Fairy";
            default -> "Unknown";
        };
    }

    public static Type stringToType(String type) {
        return switch (type) {
            case "Normal" -> Type.NORMAL;
            case "Fire" -> Type.FIRE;
            case "Water" -> Type.WATER;
            case "Grass" -> Type.GRASS;
            case "Electric" -> Type.ELECTRIC;
            case "Ice" -> Type.ICE;
            case "Fighting" -> Type.FIGHTING;
            case "Poison" -> Type.POISON;
            case "Ground" -> Type.GROUND;
            case "Flying" -> Type.FLYING;
            case "Psychic" -> Type.PSYCHIC;
            case "Bug" -> Type.BUG;
            case "Rock" -> Type.ROCK;
            case "Ghost" -> Type.GHOST;
            case "Dragon" -> Type.DRAGON;
            case "Dark" -> Type.DARK;
            case "Steel" -> Type.STEEL;
            case "Fairy" -> Type.FAIRY;
            default -> null; // Ou tu peux lever une exception si la valeur est inconnue
        };
    }
}

