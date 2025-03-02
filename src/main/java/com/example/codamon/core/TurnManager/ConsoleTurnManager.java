package com.example.codamon.core.TurnManager;

import com.example.codamon.core.Battle;

import java.util.Scanner;

import static com.example.codamon.core.GlobalTools.waitPressEnter;

public class ConsoleTurnManager implements Turn {

    public ConsoleTurnManager(){

    }

    //CONSTRUCTOR______________________________________________________________

    //STRINGIFIER______________________________________________________________

    //GETTER___________________________________________________________________

    //SETTER___________________________________________________________________

    //TURN RULE_____________________________________________________________________
    public void executePhase(BattlePhase phase, Battle battle) {
        switch (phase){
            case START_PHASE -> startPhaseRule(battle);
            case SELECT_MOVE_PHASE -> selectMovePhaseRule(battle);
            case APPLY_MOVE_PHASE -> applyMovePhaseRule(battle);
            case END_PHASE -> endPhaseRule(battle);
            default -> System.out.println("#PHASE# is not correct");
        }

    }
    public void startPhaseRule(Battle battle) {
        waitPressEnter();
    }
    public void selectMovePhaseRule(Battle battle) {
        waitPressEnter();
    }
    public void applyMovePhaseRule(Battle battle) {
        waitPressEnter();
    }
    public void endPhaseRule(Battle battle) {
        waitPressEnter();
    }

    //Tools____________________________________________________________________


}
