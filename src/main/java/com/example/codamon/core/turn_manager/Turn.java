package com.example.codamon.core.turn_manager;

import com.example.codamon.core.Battle;

public interface Turn {
    public void executePhase(BattlePhase phase, Battle battle);
    public void startPhaseRule(Battle battle) ;
    public void selectMovePhaseRule(Battle battle);
    public void applyMovePhaseRule(Battle battle);
    public void endPhaseRule(Battle battle);

}
