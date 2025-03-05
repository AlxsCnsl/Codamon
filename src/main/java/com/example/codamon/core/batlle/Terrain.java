package com.example.codamon.core.batlle;

import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.pokemon.Team;

import java.util.ArrayList;

public class Terrain {
    private ArrayList<Team> PlayersTeam; //pour le 2playerVS2player
    private ArrayList<Pokemon> activePokemons; // pour le 2pokemonVs2pokemon
    private Battle battle;

    public Terrain(){

    }
    //GETTER___________________________________________________________________
    public ArrayList<Pokemon> getActivePokemons(){
        return this.activePokemons;
    }

    public ArrayList<Team> getPlayersTeam(){
        return this.PlayersTeam;
    }
}

