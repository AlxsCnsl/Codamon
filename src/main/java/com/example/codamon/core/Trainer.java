package com.example.codamon.core;

import com.example.codamon.core.pokemon.Team;

public class Trainer {
    private Team pokemonTeam;
    private String name;
    public Trainer(String name){
        this.name = name;
        this.pokemonTeam = new Team(this);
    }

    //GETTER___________________________________________________________________
    public String getName() {
        return name;
    }

    public Team getTeam() {
        return pokemonTeam;
    }

    //SETTER___________________________________________________________________
    public void setTeam(Team pokemonTeam) {
        this.pokemonTeam = pokemonTeam;
    }
}
