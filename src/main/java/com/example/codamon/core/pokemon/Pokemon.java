package com.example.codamon.core.pokemon;
import com.example.codamon.core.type.TypeTools;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.effect.status.AbstractStatus;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.action.move.Move;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;

public class Pokemon {

    private Boolean isAlive = true;
    private final String name;
    private int lvl = 1000;
    private final HashMap<String, Integer> baseStats = new HashMap<>();
    private HashMap<String, Integer> modifierStats = new HashMap<>();
    private int currentHP;
    private ArrayList<Type> types = new ArrayList<>();
    private ArrayList<Move> moves = new ArrayList<>();
    private AbstractStatus status;
    private Terrain terrain = null;

    //CONSTRUCTOR______________________________________________________________
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
        this.baseStats.put("ACC", 1);
        this.baseStats.put("ESC", 1);
        this.resetPokemon();//for define modifierStats and other stats
        System.out.println("#POKEMON#"+name+" is Construct");
        this.resetPokemon();
        System.out.println("#POKEMON# " + name + " is built");
    }

    public Pokemon(String name){
        this.name = name;
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName =
                "/com/example/codamon/data/pokemon/"+
                        name.toLowerCase()+".json";
        InputStream inputStream =
                getClass().getResourceAsStream(pathName);
        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            initBasStatesWithJson(rootNode);
        }catch (IOException e){
            System.out.println(pathName + " could not be opened");
            e.printStackTrace();
        }
    }

    private void initBasStatesWithJson(JsonNode rootNode){
        for(int i = 0 ; i < rootNode.get("types").size() ; i++){
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
        this.baseStats.put("ACC", 1);
        this.baseStats.put("ESC", 1);
        this.resetPokemon();//for define modifierStats and other stats
        System.out.println("#POKEMON# " + name + " is built");

    }

    //STRINGIFIER______________________________________________________________
    public String toString(){
        return  "NAME: "+" "+this.name+statusToSrint()+
                "\nPV:"+this.currentHP +"/"+this.getCurrentState("HP")+
                "\nTypes:"+this.typesToString()+
                "\nMoves:"+this.movesToString();
    }

    public String statusToSrint(){
        if(this.status == null){
            return "";
        }return "[" +status.getName()+"]";
    }
    public String typesToString() {
        String types = "";
        for (Type type : this.types) {
            types += "[" + TypeTools.typeToString(type) + "]";
        }
        return types;
    }
    public String movesToString(){
        String moves = "";
        for (Move move : this.moves) {
            moves += move.toString();
        }
        return moves;
    }
    //GETTER___________________________________________________________________
    public Terrain getTerrains(){
        return this.terrain;
    }

    public int getBaseState(String key){
        //Error à faire
        return this.baseStats.get(key);
    }

    public int getModifierState(String key){
        return  this.modifierStats.get(key);
    }

    public double getFactorModifiedState(String key) {
        double factor;
        if (!key.equals("ACC") && !key.equals("ESC")) {
            switch(this.getModifierState(key)) {
                case -6 -> factor = 0.25;
                case -5 -> factor = 0.29;
                case -4 -> factor = 0.33;
                case -3 -> factor = 0.40;
                case -2 -> factor = 0.50;
                case -1 -> factor = 0.67;
                case 1 -> factor = 1.5;
                case 2 -> factor = 2;
                case 3 -> factor = 2.5;
                case 4 -> factor = 3;
                case 5 -> factor = 3.5;
                case 6 -> factor = 4;
                default -> factor = 1;
            }return factor;
        } factor = getFactorOtherModifierState(key);
        return factor;
    }

    public double getFactorOtherModifierState(String key) {
        double factor = 0;
        switch(getBaseState(key)) {
            case -6 -> factor = 0.33;
            case -5 -> factor = 0.38;
            case -4 -> factor = 0.43;
            case -3 -> factor = 0.50;
            case -2 -> factor = 0.60;
            case -1 -> factor = 0.75;
            case 1 -> factor = 1.33;
            case 2 -> factor = 1.67;
            case 3 -> factor = 2;
            case 4 -> factor = 2.33;
            case 5 -> factor = 2.67;
            case 6 -> factor = 3;
            default -> factor = 1;
        }
        return factor;
    }

    public Boolean getIsAlive(){
        return this.isAlive;
    }

    public int getCurrentState(String key){
        int iv = 61;
        if(!key.equals("HP")){
            return (int)
                    ((2*this.getBaseState("HP")*this.lvl+iv)/100)+5;

        } return (int)
                ((2*this.getBaseState("HP")*this.lvl+iv)/100)+this.lvl+10;
    }

    public ArrayList<Type> getTypes() {
        return this.types;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public String getName(){
        return this.name;
    }

    public Move getMoveByIndex(int index){
        return moves.get(index);
    }

    public Move getMoveByName(String moveSearched){
        for(Move move : this.moves){
            System.out.println("Move name : "+move.getName());
            if(move.getName().equalsIgnoreCase(moveSearched)){
                return move;
            }
        }
        System.out.println("#POKEMON#GETMOVE# "+
                        this.name+" no content move > "+
                        moveSearched);
        return null;
    }

    public int getLvl(){
        return this.lvl;
    }

    public AbstractStatus getStatus() {
        return status;
    }

    //SETTER___________________________________________________________________

    public void setTerrain(Terrain terrain){
        this.terrain = terrain;
    }

    public void setStatus(AbstractStatus status){
        this.status = status;
    }

    public void addModifierStat(String stat, int modifier){
        int pastStats = this.modifierStats.get(stat);
        this.modifierStats.put(stat, pastStats+modifier);
    }

    //REST POKEMON STATS_______________________________________________________
    private void resetModifierStats(){
        this.baseStats.forEach((stat, value)->{
            this.modifierStats.put(stat, 0);
        });
    }

    private void resetCurrentHP(){
        this.currentHP = this.getCurrentState("HP");
    }

    private void resetPokemon(){
        this.resetModifierStats();
        this.resetCurrentHP();
    }

    //MOVES TOOLS______________________________________________________________
    public void addMove(Move move){
        if(this.moves.size() < 4){
            this.moves.add(move);
        }else{
            System.out.println("#POKEMON#"+this.getName()+
                    "is trying to learn the move\""+move.getName()+
                    "but he already knows 4 moves");
        }
    }

    public void getDamage(int damage){
        this.currentHP -= damage;
        if(this.currentHP < 0){
            this.currentHP = 0;
            this.isAlive = false;
        }
    }
}
