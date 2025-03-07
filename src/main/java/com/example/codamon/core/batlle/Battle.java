package com.example.codamon.core.batlle;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.turn_manager.BattlePhase;
import com.example.codamon.core.batlle.turn_manager.Turn;

import java.util.ArrayList;

public class Battle {

    private ArrayList<ArrayList<Trainer>> playersTeams =
            new ArrayList<>();//if 2p/2p
    private ArrayList<Terrain> terrains = new ArrayList<>();
    private Turn turnRule;
    private BattlePhase phase = BattlePhase.START_PHASE;
    private Boolean isRunning = true;

    //CONSTRUCTOR______________________________________________________________
    public Battle(Trainer trainer1, Trainer trainer2, Turn turnRules){//==============================================à refaire
        ArrayList<Trainer> playersTeam1 = new ArrayList<>();
        this.playersTeams.add(playersTeam1);
        this.playersTeams.get(0).add(trainer1);
        this.terrains.add(new Terrain());
        this.terrains.get(0).addTrainer(trainer1);
        this.terrains.get(0).setBattle(this);
        System.out.println(this.terrains.get(0).getTrainersTeam().size());//====================================

        ArrayList<Trainer> playersTeam2 = new ArrayList<>();
        this.playersTeams.add(playersTeam2);
        this.playersTeams.get(1).add(trainer2);
        this.terrains.add(new Terrain());
        this.terrains.get(1).addTrainer(trainer2);
        this.terrains.get(1).setBattle(this);
        System.out.println(this.terrains.get(1).getTrainersTeam().size());//====================================

        this.turnRule = turnRules;
    }


    //STRINGIFIER______________________________________________________________
    public String activePokemonsToString() {//==============================================à refaire
        return null;
    }

    //GETTER___________________________________________________________________
    public BattlePhase getPhase() { return this.phase;}
    public ArrayList<Terrain> getTerrains() {return this.terrains;}
    public ArrayList<ArrayList<Trainer>> getPlayersTeams() {
        return this.playersTeams;
    }

    //SETTER___________________________________________________________________
    public void stop(){
        this.isRunning = false;
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
        System.out.println("#BATTLE# Start of BAAATTTLLLEEEE : ");

        this.turnRule.startBattleRule(this);
        while (isRunning){
            System.out.println(
                    "#BATTLE# Start of phase : "+this.getPhase().toString());
            this.turnRule.executePhase(this.phase, this);
            this.nextPhase();
        }
    }

}
