package com.example.codamon;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.action.category.Category;
import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.action.move.MoveTools;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.pokemon.Team;
import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;
import com.example.codamon.core.*;

import com.example.codamon.core.batlle.turn_manager.ConsoleTurnManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokeApp extends Application {

    private static Map<SceneName, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        Trainer pokemonTrainer = new Trainer("Alexis");
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

        /*
        //ALEXIS________________________________________________________________
        Scanner scanner = new Scanner(System.in); // Création du scanner


        Pokemon pikachu = new Pokemon("Pikachu");
        pikachu.addMove("Charge");
        pikachu.addMove("Eclair");
        pikachu.addMove("Fatal-Foudre");
        pikachu.addMove("Cage-Eclair");

        Pokemon raikou = new Pokemon("Raikou");
        raikou.addMove("Fatal-Foudre");

        Pokemon psykokwak = new Pokemon("Psykokwak");
        psykokwak.addMove("Charge");
        psykokwak.addMove("Mimi-Queue");

        Pokemon togekiss = new Pokemon("Togekiss");
        togekiss.addMove("Charge");

        Pokemon lugulabre = new Pokemon("Lugulabre");

        Trainer alexis = new Trainer("Alexis");
        Team team1 = new Team(alexis);
        team1.addPokemon(pikachu);
        //team1.addPokemon(psykokwak);

        Trainer ethane = new Trainer("Ethan");
        Team team2 = new Team(ethane);
        team2.addPokemon(togekiss);
        //team2.addPokemon(lugulabre);

        System.out.println(togekiss.getTypes());
        System.out.println(togekiss.getName());

        Battle battle = new Battle(alexis, ethane, new ConsoleTurnManager());


        pikachu.switchMove("Charge", "Para-Spore");
        pikachu.getMoveByName("Cage-Eclair").execute(pikachu, togekiss, battle);
        //psykokwak.getMoveByName("Charge").execute(psykokwak, togekiss, battle);


        togekiss.getMoveByName("Charge").execute(togekiss, pikachu, battle);

        togekiss.unsetMajorStatus();

        System.out.println(team1);System.out.println(team2);
        //System.out.println(battle.activePokemonsToString());
        battle.run();

        */

        //______________________________________________________________________



    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}
