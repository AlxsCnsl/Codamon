package com.example.codamon.core;

import java.util.ArrayList;

public class PokemonTeam {

    private ArrayList<Pokemon> team = new ArrayList<>();
    private final String name;
    private int firstPokemonIndex = 0;

    //CONSTRUCTOR_______________________________________________________________
    public PokemonTeam(String name, ArrayList<Pokemon> team){
        this.team = team;
        this.name = name;
        this.constructorAlert();
    }
    public PokemonTeam(String name){
        this.name = name;
        this.constructorAlert();
    }

    private void constructorAlert(){
        System.out.println("#TEAM# Team : "+this.getName()+
                " is is instantiated");
    }

    //STRINGIFIER______________________________________________________________
    public String toString(){
        String string = "|"+this.name+"'s team :============";
        for(int i = 0; i < team.size(); i++){
            string += "\n==========\n"+
                    "[N°"+(i+1)+"] "+team.get(i).toString();
        }
        string += "\n======================";
        return string;
    }

    //GETTER___________________________________________________________________
    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        return team;
    }

    public int getFirstPokemonIndex(){
        return this.firstPokemonIndex;
    }

    //SETTER___________________________________________________________________
    public void setFistPokemonIndex(int index){
        this.firstPokemonIndex = index;
    }

    //MODIFIER_________________________________________________________________
    public void addPokemon(Pokemon pokemon){
        this.team.add(pokemon);
        System.out.println("#TEAM# " +this.getName()+"'s team add "+
                "Pokemon : " + pokemon.getName() +" in team" );
    }

}
