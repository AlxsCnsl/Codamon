package com.example.codamon.core.batlle.turn_manager;

import com.example.codamon.core.batlle.Battle;

public interface Turn {
    public void executePhase(BattlePhase phase, Battle battle) throws InterruptedException;
    public void startPhaseRule(Battle battle) throws InterruptedException;
    public void selectMovePhaseRule(Battle battle) throws InterruptedException;
    public void applyMovePhaseRule(Battle battle) throws InterruptedException;
    public void endPhaseRule(Battle battle) throws InterruptedException;
    public void startBattleRule(Battle battle);
}
