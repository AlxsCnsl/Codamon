package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.GraphicBattle;
import com.example.codamon.core.batlle.control.BotControl;
import com.example.codamon.core.batlle.turn_manager.GraphicTurnManager;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class MenuController {

    public MenuController() {

    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    @FXML private Text creditText;
//    @FXML private Button battleButton;
//    @FXML private Button teamBuilderButton;

    @FXML
    public void onBattleButtonClick() {
        Pokemon pikachu = new Pokemon("Pikachu");
        pikachu.addMove("Charge");
        pikachu.addMove("Eclair");
        pikachu.addMove("Fatal-Foudre");
        pikachu.addMove("Cage-Eclair");

        HashMap<String, Object> userData = (HashMap<String, Object>) stage.getUserData();
        Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

        Trainer bot = new Trainer("bot", new BotControl());
        bot.addPokemon(pikachu);

        stage.setScene(PokeApp.getScenes().get(SceneName.BATTLE));
        GraphicBattle graphicBattle = new GraphicBattle(pokemonTrainer, bot, new GraphicTurnManager());
        graphicBattle.turnRule.startBattleRule(graphicBattle);
    }

    @FXML
    public void onTeamBuilderButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.TEAMBUILDER));
    }
}