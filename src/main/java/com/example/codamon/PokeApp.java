package com.example.codamon;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.batlle.control.BotControl;
import com.example.codamon.core.batlle.control.ConsoleControl;
import com.example.codamon.core.batlle.control.GraphicControl;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;

import com.example.codamon.core.batlle.turn_manager.TurnManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokeApp extends Application {

    private static Map<SceneName, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        Trainer pokemonTrainer = new Trainer("Alexis", new GraphicControl());//Change To GraphicControl
        pokemonTrainer.getControl().setStage(stage);

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("pokemonTrainer", pokemonTrainer);
        stage.setUserData(userData);

        scenes.put(
                SceneName.MENU, new MenuView(stage).getScene());
        scenes.put(
                SceneName.BATTLE, new BattleView(stage).getScene());
        scenes.put(
                SceneName.TEAMBUILDER, new TeamBuilderView(stage).getScene());

        stage.setScene(scenes.get(SceneName.TEAMBUILDER));
        stage.setTitle("MENU");
        stage.show();


        //ALEXIS________________________________________________________________
        Scanner scanner = new Scanner(System.in); // Création du scanner


        Pokemon pikachu = new Pokemon("Pikachu");
        pikachu.addMove("Charge");
        pikachu.addMove("Eclair");
        pikachu.addMove("Fatal-Foudre");
        pikachu.addMove("Cage-Eclair");

        Pokemon psykokwak = new Pokemon("Psykokwak");
        psykokwak.addMove("Charge");
        psykokwak.addMove("Mimi-Queue");


        Pokemon togekiss = new Pokemon("Togekiss");
        togekiss.addMove("Charge");

        Pokemon lugulabre = new Pokemon("Lugulabre");
        lugulabre.addMove("Charge");
        lugulabre.addMove("Para-Spore");


        Trainer alexis = new Trainer("Alexis", new BotControl());

        alexis.addPokemon(pikachu);
        alexis.addPokemon(psykokwak);

        Trainer ethan = new Trainer("Ethan", new BotControl());
        ethan.addPokemon(togekiss);
        ethan.addPokemon(lugulabre);



        /*
        //=======Test2=====
        alexis.setTerrain(new Terrain());
        ethan.setTerrain(new Terrain());
        alexis.sendPokemon(pikachu);
        ethan.sendPokemon(togekiss);
        //=======Test2=====

        pikachu.loadMove("Switch", psykokwak);
        pikachu.getMoveByName("Switch").execute();

        psykokwak.getTerrain().getTrainersTeam();

         */



        Battle battle = new Battle(alexis, ethan, new TurnManager());
        System.out.println(alexis.getTerrain());System.out.println(ethan.getTerrain());
        battle.run();

        //______________________________________________________________________

    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}
