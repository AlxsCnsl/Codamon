package com.example.codamon.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Pokemon {

    private final String name;
    private final HashMap<String, Integer> baseStats = new HashMap<>();
    private HashMap<String, Integer> modifierStats = new HashMap<>();
    private int accurateHP;
    private ArrayList<Type> types = new ArrayList<>();

    public Pokemon(String name, ArrayList<Type> types,
                   int HP, int ATK, int DEF, int SPA, int SPD, int SPE){
        this.name = name;
        this.types = types;
        this.baseStats.put("HP", HP);
        this.baseStats.put("ATK", ATK);
        this.baseStats.put("DEF", DEF);
        this.baseStats.put("SPA", SPA);
        this.baseStats.put("SPD", SPD);
        this.baseStats.put("SPE", SPE);
        this.resetPokemon();
        System.out.println("#POKEMON#"+name+" is Construct");
    }

    public Pokemon(String name){
        this.name = name;
        ObjectMapper objectMapper = new ObjectMapper();
        String pathFile =
                "resources/com/example/codamon/data/pokemon/"+name.toLowerCase()+".json";
        try {
            JsonNode rootNode = objectMapper.readTree(new File(pathFile));
            setBasStatesWithJson(rootNode);
        }catch (IOException e){
            System.out.println(pathFile + " could not be opened");
            e.printStackTrace();
        }
    }

    private void setBasStatesWithJson(JsonNode rootNode){
        for(int i = 0; i <rootNode.get("types").size()  ; i++ ){
            this.types.add(TypeTools.stringToType(
                    rootNode.get("types").get(i).asText()));
        }
        JsonNode stats = rootNode.get("stats");
        this.baseStats.put("HP", stats.get("HP").asInt());
        this.baseStats.put("ATK", stats.get("ATK").asInt());
        this.baseStats.put("DEF", stats.get("DEF").asInt());
        this.baseStats.put("SPA", stats.get("SPA").asInt());
        this.baseStats.put("SPD", stats.get("SPD").asInt());
        this.baseStats.put("SPE", stats.get("SPE").asInt());
        this.resetPokemon();
        System.out.println("#POKEMON#"+name+" is Construct");
    }




    //STRINGIFIER______________________________________________________________
    public String toString(){
        return "_____\n"+ "NAME: "+ this.name+
                " PV:"+this.accurateHP+"/"+this.getCurrentState("HP")+"\n"+
                "Types:"+this.typesToString();
    }

    public String typesToString() {
        String type = "";
        for (Type value : this.types) {
            type += "[" + TypeTools.typeToString(value) + "]";
        }
        return type;
    }

    //GETTER___________________________________________________________________
    private int getBaseState(String key){
        //Error à faire
        return  this.baseStats.get(key);
    }

    private int getCurrentState(String key){
        if(!key.equals("HP")){
            return 2*this.getBaseState(key)+5;
        } return 2*this.getBaseState("HP")+110;
    }

    public int getAccurateHP() {
        return this.accurateHP;
    }

    public String getName(){
        return this.name;
    }

    //REST POKEMON STATS_______________________________________________________
    private void resetModifierStats(){
        this.baseStats.forEach((stat, value)->{
            this.modifierStats.put(stat, 0);
        });
    }

    private void resetAccurateHP(){
        this.accurateHP = this.getCurrentState("HP");
    }

    private void resetPokemon(){
        this.resetModifierStats();
        this.resetAccurateHP();
    }
}
