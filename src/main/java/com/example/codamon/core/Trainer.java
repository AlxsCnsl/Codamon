package com.example.codamon.core;

import com.example.codamon.core.pokemon.Team;

public class Trainer {
    private Team pokemonTeam;
    private String name;
    private Trainer(String name){
        this.name = name;
    }

    //GETTER___________________________________________________________________
    public String getName() {
        return name;
    }
}
