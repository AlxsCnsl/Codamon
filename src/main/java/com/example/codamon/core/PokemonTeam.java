package com.example.codamon.core;

import java.util.ArrayList;

public class PokemonTeam {

    private ArrayList<Pokemon> team = new ArrayList<>();
    private final String name;
    private int firstPokemonIndex = 0;

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

    public String toString(){
        String string = "|"+this.name+"'s team :";
        for(int i = 0; i < team.size(); i++){
            string += "\n==========\n"+
                    "N°"+(i+1)+"\n"+team.get(i).toString();
        }
        return string;
    }

    public String getName() {
        return name;
    }

    public void setFistPokemonIndex(int index){
        this.firstPokemonIndex = index;
    }

    public int getFirstPokemonIndex(){
        return this.firstPokemonIndex;
    }

    public void addPokemon(Pokemon pokemon){
        this.team.add(pokemon);
        System.out.println("#TEAM# " +this.getName()+"'s team add "+
                "Pokemon : " + pokemon.getName() +" in team" );
    }
}
