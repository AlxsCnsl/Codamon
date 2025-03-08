package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.GraphicBattle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.batlle.control.TrainerControl;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class BattleController implements TrainerControl {

    public BattleController() {}

    private Stage stage;
    private GraphicBattle graphicBattle;
    @FXML VBox history;
    @FXML HBox movesButtons;
    @FXML HBox switchButtons;
    @FXML VBox mainPokemonBackSprite;
    @FXML VBox botPokemonFrontSprite;
    @FXML Text pokemonName1;
    @FXML Text pokemonName2;
    @FXML Text pokemonCurrentHP1;
    @FXML Text pokemonMaxHP1;
    @FXML Text pokemonCurrentHP2;
    @FXML Text pokemonMaxHP2;

    @Override
    public void switchBeforeKo(Trainer trainer) {
        return;
    }

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;

//        setAttackButtons();
        setSwitchButtons();
        setMainPokemonSprite();
        setBotPokemonSprite();
        setPokemonNames();
        setPokemonHPs();
    }

    public void setGraphicBattle(GraphicBattle graphicBattle) throws InterruptedException {
        this.graphicBattle = graphicBattle;
        graphicBattle.executeCurrentPhase();
        graphicBattle.executeCurrentPhase();
    }

    @FXML
    private void onExitButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.MENU));
    }

    private Trainer getMainTrainer() {
        HashMap<String, Object> userData = (HashMap<String, Object>) stage.getUserData();
        Trainer mainTrainer = (Trainer) userData.get("pokemonTrainer");

        return mainTrainer;
    }

    private Pokemon getMainTrainerPokemon() {
        return getMainTrainer().getActivePokemons().getFirst();
    }

    private Trainer getBotTrainer() {
        Battle battle = getMainTrainer().getTerrain().getBattle();
        for(Terrain terrain : battle.getTerrains()){
            if(!terrain.equals(getMainTrainer().getTerrain())){
                return terrain.getTrainersTeam().getFirst();
            }
        }
        System.out.println("BotTrainer not found");
        return null;
    }

    private Pokemon getBotTrainerPokemon() {
        return getBotTrainer().getActivePokemons().getFirst();
    }

//    private void setAttackButtons() {
//        ArrayList<Move> moves = getMainTrainer().getActivePokemons().getFirst().getMoves();
//
//        for (Move move : moves) {
//            Button moveButton = new Button(move.getName());
//            moveButton.setPrefSize(200, 50);
//            movesButtons.getChildren().add(moveButton);
//
//            moveButton.setOnAction((actionEvent -> {
//                System.out.println("moveButton.setOnAction : " + getMainTrainerPokemon().getMoveByName(moveButton.getText()));
//                try {
//                    graphicBattle.executeCurrentPhase();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }));
//        }
//    }

    @Override
    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon) {
        System.out.println("getMoveChoice is called");
        CompletableFuture<Move> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            System.out.println("Platform runlater");
            movesButtons.getChildren().clear();

            for (Move move : pokemon.getMoves()) {
                System.out.println("boucle for");
                Button moveButton = new Button(move.getName());
                moveButton.setPrefSize(200, 50);
                moveButton.setOnAction(e -> {
                    move.getOwner().loadMove(move.getName(), getTarget(pokemon));
                    future.complete(move);
                    movesButtons.getChildren().clear();
                    try {
                        graphicBattle.executeCurrentPhase(); // APPLY MOVE PHASE
                        setPokemonsCurrentHPs();
                        graphicBattle.executeCurrentPhase(); // END PHASE
                        graphicBattle.executeCurrentPhase(); // START PHASE
                        graphicBattle.executeCurrentPhase(); // SELECT MOVE PHASE
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                movesButtons.getChildren().add(moveButton);
            }
        });
        System.out.println("end of getMoveChoice");
        return future;
    }

    private Pokemon getTarget(Pokemon pokemon) {
        Pokemon target = null;
        Battle battle = pokemon.getTerrain().getBattle();
        for(Terrain terrain : battle.getTerrains()){//Si : 2 terrain ↓↓↓↓↓↓
            if(!terrain.equals(pokemon.getTerrain())){
                target = terrain.getActivePokemons().getFirst();
            }
        }
        return target;
    }

    private Image getPokemonSprite(String pokemonName, String direction) {
        URL pokemonSpriteURL = getClass().getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName.toLowerCase() + "/" +
                        pokemonName.toLowerCase() + "_" + direction + ".png");

        return new Image(
                pokemonSpriteURL.toExternalForm());
    }

    private ImageView createPokemonImageView(String pokemonName, String direction) {
        Image pokemonSprite = getPokemonSprite(pokemonName, direction);

        ImageView pokemonSpriteImageView = new ImageView(pokemonSprite);
        pokemonSpriteImageView.setFitWidth(350);
        pokemonSpriteImageView.setPickOnBounds(true);
        pokemonSpriteImageView.setPreserveRatio(true);

        return pokemonSpriteImageView;
    }

    private void setMainPokemonSprite() {
        ImageView pokemonSpriteImageView = createPokemonImageView(getMainTrainer().getActivePokemons().getFirst().getName(), "back");
        mainPokemonBackSprite.getChildren().add(pokemonSpriteImageView);
    }

    private void setBotPokemonSprite() {
        ImageView pokemonSpriteImageView = createPokemonImageView(getBotTrainer().getActivePokemons().getFirst().getName(), "face");
        botPokemonFrontSprite.getChildren().add(pokemonSpriteImageView);
    }

    private void setSwitchButtons() {
        ArrayList<Pokemon> pokemonsTeam = getMainTrainer().getPokemonsTeam().getPokemons();
        for (Pokemon pokemon : pokemonsTeam) {
            Button switchButton = new Button(pokemon.getName());
            switchButton.setPrefSize(100, 30);
            switchButtons.getChildren().add(switchButton);
        }
    }

    private void setPokemonNames() {
        pokemonName1.setText(getMainTrainer().getActivePokemons().getFirst().getName());
        pokemonName2.setText(getBotTrainer().getActivePokemons().getFirst().getName());
    }

    private void setPokemonHPs() {
        pokemonCurrentHP1.setText(String.valueOf(getMainTrainerPokemon().getCurrentHP()));
        pokemonCurrentHP2.setText(String.valueOf(getBotTrainerPokemon().getCurrentHP()));
        pokemonMaxHP1.setText("/" + getMainTrainerPokemon().getCurrentState("HP"));
        pokemonMaxHP2.setText("/" + getBotTrainerPokemon().getCurrentState("HP"));
    }

    private void setPokemonsCurrentHPs() {
        if (getBotTrainer().getActivePokemons().isEmpty()) {
            pokemonCurrentHP2.setText("0");
        } else if (getMainTrainer().getActivePokemons().isEmpty()) {
            pokemonCurrentHP1.setText("0");
        } else {
            pokemonCurrentHP1.setText(String.valueOf(getMainTrainerPokemon().getCurrentHP()));
            pokemonCurrentHP2.setText(String.valueOf(getBotTrainerPokemon().getCurrentHP()));
        }
    }
}