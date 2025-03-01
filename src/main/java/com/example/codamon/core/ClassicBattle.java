package com.example.codamon.core;

import java.util.ArrayList;

public class ClassicBattle {

    private ArrayList<PokemonTeam> teams = new ArrayList<>();
    private int turnNumber = 0;

    public ClassicBattle(PokemonTeam team1, PokemonTeam team2){
        this.teams.add(team1);
        this.teams.add(team2);
        System.out.println("#BATTLE# New battle : "+team1.getName()+
                " VS "+team2.getName());
    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public void incrementTurnNumber(int turnNumber) {
        this.turnNumber += turnNumber;
    }

    public ArrayList<PokemonTeam> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<PokemonTeam> teams) {
        this.teams = teams;
    }


}
