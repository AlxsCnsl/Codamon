package com.example.codamon.core;

import com.example.codamon.core.TurnManager.BattlePhase;
import com.example.codamon.core.TurnManager.Turn;

import java.util.ArrayList;

public class Battle {

    private ArrayList<PokemonTeam> teams = new ArrayList<>();
    private Turn turnRule;
    private ArrayList<ArrayList<Integer>> activePokemonsIndex = new ArrayList<>();
    private BattlePhase phase = BattlePhase.START_PHASE;
    private Boolean isRunning = true;
    //CONSTRUCTOR______________________________________________________________
    public Battle(PokemonTeam team1, PokemonTeam team2, Turn turnRules){
        this.teams.add(team1);
        this.teams.add(team2);
        this.activePokemonsIndex.add(new ArrayList<>());
        this.activePokemonsIndex.add(new ArrayList<>());
        this.activePokemonsIndex.get(0).add(0);
        this.activePokemonsIndex.get(1).add(0);
        this.turnRule = turnRules;
        System.out.println("#BATTLE# New battle : "+team1.getName()+
                " VS "+team2.getName());
    }

    //STRINGIFIER______________________________________________________________
    public String activesPokeomToString() {
        String string = "";
        string += "Active POKEMONS : \n";
        int teamsNumber = this.activePokemonsIndex.size();
        for (int i = 0; i < teamsNumber ; i++) {
            int activePokemonsNumber = this.activePokemonsIndex.get(i).size();
            string += "==== "+this.teams.get(i).getName()+" ====\n";
            for (int j = 0; j < activePokemonsNumber; j++) {
                if (activePokemonsNumber != 0 &&
                        teams.get(i).getTeam().get(j).getIsAlive()){
                    string += teams.get(i).getTeam().get(j).toString();
                }
            }
        }
        return string;
    }

    //GETTER___________________________________________________________________
    public ArrayList<PokemonTeam> getTeams() {
        return this.teams;
    }

    public BattlePhase getPhase() { return this.phase;}

    //SETTER___________________________________________________________________
    public void setTeams(ArrayList<PokemonTeam> teams) {
        this.teams = teams;
    }

    //TURN_MANAGER_____________________________________________________________
    private void nextPhase(){
        switch (this.phase){
            case START_PHASE -> {
                this.phase = BattlePhase.SELECT_MOVE_PHASE;
            }
            case SELECT_MOVE_PHASE -> {
                this.phase = BattlePhase.APPLY_MOVE_PHASE;
            }
            case APPLY_MOVE_PHASE -> {
                this.phase = BattlePhase.END_PHASE;
            }
            case END_PHASE -> {
                this.phase = BattlePhase.START_PHASE;
            }
        }
    }

    public void run(){
        while (isRunning){
            System.out.println(
                    "#BATTLE# Start of phase : "+this.getPhase().toString());
            this.turnRule.executePhase(this.phase, this);
            this.nextPhase();
        }
    }

}
