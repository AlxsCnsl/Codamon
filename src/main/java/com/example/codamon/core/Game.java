package com.example.codamon.core;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.turn_manager.ConsoleTurnManager;

public class Game {
    private Trainer user;
    public Game(Trainer user){
        this.user = user;
    }

    public void startBotBattle(Trainer bot){
        Battle battle;
        battle = new Battle(user, bot, new ConsoleTurnManager());
    }
}
