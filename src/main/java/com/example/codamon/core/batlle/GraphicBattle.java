package com.example.codamon.core.batlle;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.turn_manager.Turn;
import javafx.stage.Stage;

public class GraphicBattle extends Battle {
    private Stage stage;

    public GraphicBattle(Trainer trainer1, Trainer trainer2, Turn turnRules) {
        super(trainer1, trainer2, turnRules);
    }

    public void setStage(Stage stage) {
        if(stage == null){
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    public void run() throws InterruptedException {
        System.out.println("#BATTLE# Start of graphic BAAATTTLLLEEEE : ");

        this.turnRule.startBattleRule(this);
        while (isRunning){
            //=======================================

            System.out.println(
                    "#BATTLE# Start of phase : "+this.getPhase().toString());
            this.turnRule.executePhase(this.phase, this);
            this.nextPhase();
            //===============================================
        }
    }

    public void executeCurrentPhase() throws InterruptedException {
        System.out.println(
                "#BATTLE# Start of phase : "+this.getPhase().toString());
        this.turnRule.executePhase(this.phase, this);
        this.nextPhase();
    }

    @Override
    public void addTurnLogs(String logs) {
        super.addTurnLogs(logs);
    }

    public void displayHistory() {

    }
}
