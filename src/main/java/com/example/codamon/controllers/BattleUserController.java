package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.controllers.GraphiqueElements.UiFactory;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.GraphicBattle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.batlle.control.BotController;
import com.example.codamon.core.batlle.control.TrainerController;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.turn_manager.GraphicTurnManager;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class BattleUserController implements TrainerController {

    public BattleUserController() {}

    private Stage stage;
    private GraphicBattle battle;
    @FXML VBox history;
    @FXML HBox movesButtons;
    @FXML HBox switchButtons;
    @FXML VBox mainPokemonBackSprite;
    @FXML ImageView mainPokemonSpriteImageView;
    @FXML Image mainPokemonSpriteImage;
    @FXML VBox botPokemonFrontSprite;
    @FXML ImageView botPokemonSpriteImageView;
    @FXML Image botPokemonSpriteImage;
    @FXML Text pokemonName1;
    @FXML Text pokemonName2;
    @FXML Text pokemonCurrentHP1;
    @FXML Text pokemonMaxHP1;
    @FXML Text pokemonCurrentHP2;
    @FXML Text pokemonMaxHP2;

    public void setStage(Stage stage) throws InterruptedException {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
        setBattle();
        this.battle.getTurnRule().startBattleRule(this.battle);
        setScreen();
        this.battle.executeCurrentPhase();
        this.battle.executeCurrentPhase();
    }

    private void setScreen(){
        setSwitchButtons();
        setMainPokemonSprite();
        setBotPokemonSprite();
        setPokemonNames();
        setPokemonHPs();
    }

    private void setBattle() throws InterruptedException {
        Trainer user = getMainTrainer();
        user.setController(this);
        Trainer botTrainer = setBotTrainer();
        battle =  new GraphicBattle(
                user,
                botTrainer,
                new GraphicTurnManager()
        );
    }

    //à changer plus tard
    private Trainer setBotTrainer(){
        Trainer bot = new Trainer("Ethan", new BotController());
        Pokemon lugulabre = new Pokemon("Lugulabre");
        lugulabre.addMove("feu-follet");
        lugulabre.addMove("Charge");
        Pokemon togekiss = new Pokemon("Togekiss");
        togekiss.addMove("Charge");
        togekiss.addMove("Mimi-queue");
        togekiss.addMove("Atterrissage");
        bot.addPokemon(togekiss);
        bot.addPokemon(lugulabre);
        return bot;
    }

    public void setBattle(GraphicBattle battle){
        this.battle = battle;
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

    @Override public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon) {
        System.out.println("getMoveChoice is called");
        CompletableFuture<Move> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            System.out.println("Platform runlater");
            movesButtons.getChildren().clear();
            for (Move move : pokemon.getMoves()) {
                HBox attackButton = UiFactory.newAttackButton(move);
                attackButton.setOnMouseClicked(e -> {
                    move.getOwner().loadMove(move.getName(), getTarget(pokemon));
                    future.complete(move);
                    switchButtons.getChildren().clear();
                    movesButtons.getChildren().clear();
                });
                movesButtons.getChildren().add(attackButton);
            } switchButtons.getChildren().clear();

            for (Pokemon pokemonSwitch : getMainTrainer().getPokemonsTeam().getPokemons()) {
                System.out.println(pokemonSwitch.getName());
                VBox pokemonCard =  UiFactory.newPokemonBattleCard(pokemonSwitch);
                pokemonCard.setOnMouseClicked(e -> {
                    if (!pokemonSwitch.getIsAlive() ||
                            pokemonSwitch.equals(getMainTrainerPokemon())) {
                        return;
                    }
                    getMainTrainerPokemon().loadMove("Switch", pokemonSwitch);
                    future.complete(getMainTrainerPokemon().getSwitchMove());
                    switchButtons.getChildren().clear();
                    movesButtons.getChildren().clear();
                });

                switchButtons.getChildren().add(pokemonCard);
            }
        });
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

    public CompletableFuture<Void> switchBeforeKo(Trainer trainer) {
        CompletableFuture<Void> switchFuture = new CompletableFuture<>();

        // Run UI updates on the FX thread
        Platform.runLater(() -> {
            System.out.println("switchBeforeKo: Displaying switch buttons...");

            // Clear any previous buttons from the container (make sure switchButtons is visible)
            switchButtons.getChildren().clear();

            // Get the team of Pokémon for the main trainer (could be trainer.getPokemonsTeam().getPokemons())
            ArrayList<Pokemon> team = getMainTrainer().getPokemonsTeam().getPokemons();
            boolean anyAvailable = false;

            // For each living Pokémon in the team, create a button for switching
            for (Pokemon pokemonSwitch : team) {
                if (!pokemonSwitch.getIsAlive()) {
                    // Skip Pokémon that are KO
                    continue;
                }
                anyAvailable = true;
                Button switchButton = new Button(pokemonSwitch.getName());
                switchButton.setPrefSize(100, 30);
                // When the user clicks a button, send the chosen Pokémon,
                // clear the buttons, update the UI, and then complete the future.
                switchButton.setOnAction(e -> {
                    System.out.println("switchBeforeKo: " + pokemonSwitch.getName() + " selected.");
                    getMainTrainer().sendPokemon(pokemonSwitch);
                    switchButtons.getChildren().clear();
                    updatePokemons();
                    if (!switchFuture.isDone()) {
                        switchFuture.complete(null);
                    }
                });

                // Add the button to the container (make sure this container is visible)
                switchButtons.getChildren().add(switchButton);
            }

            // If no available Pokémon are found (should not happen if end-phase was reached properly),
            // complete the future to avoid hanging.
            if (!anyAvailable) {
                System.out.println("switchBeforeKo: No available Pokémon to switch!");
                switchFuture.complete(null);
            }
        });

        return switchFuture;
    }



    private VBox getTypeBox(String type){
        VBox typeBox = new VBox();
        return null;
    }

    private VBox getStatusBox(String type){
        return null;
    }

    private ImageView createPokemonImageView(String pokemonName, String direction) {
        Image pokemonSprite = UiFactory.getPokemonSprite(pokemonName, direction);

        ImageView pokemonSpriteImageView = new ImageView(pokemonSprite);
        pokemonSpriteImageView.setFitWidth(350);
        pokemonSpriteImageView.setPickOnBounds(true);
        pokemonSpriteImageView.setPreserveRatio(true);

        return pokemonSpriteImageView;
    }

    //SETTER____________________________________________________________________
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

    private void setMainPokemonSprite() {
        mainPokemonSpriteImageView.setImage(UiFactory.getPokemonSprite(getMainTrainer().getActivePokemons().getFirst().getName(), "back"));
    }

    private void setBotPokemonSprite() {
        botPokemonSpriteImageView.setImage(UiFactory.getPokemonSprite(getBotTrainer().getActivePokemons().getFirst().getName(), "face"));
    }


    //UPDATE___________________________________________________________________

    public void updatePokemons(){
        updatePokemonSprites();
        updatePokemonNames();
        updatePokemonHPs();
    }

    private void updateMainPokemonSprite() {
        mainPokemonBackSprite.getChildren().clear();
        if (!getMainTrainer().getActivePokemons().isEmpty()) {
            ImageView pokemonSpriteImageView = createPokemonImageView(
                    getMainTrainer().getActivePokemons().getFirst().getName(),
                    "back");
            mainPokemonBackSprite.getChildren().add(pokemonSpriteImageView);
        }
    }

    private void updateBotPokemonSprite() {
        botPokemonFrontSprite.getChildren().clear();
        if (!getBotTrainer().getActivePokemons().isEmpty()) {
            ImageView pokemonSpriteImageView = createPokemonImageView(
                    getBotTrainer().getActivePokemons().getFirst().getName(),
                    "face"
            );
            botPokemonFrontSprite.getChildren().add(pokemonSpriteImageView);
        }
    }

    private void updatePokemonSprites() {
        updateMainPokemonSprite();
        updateBotPokemonSprite();
    }

    private void updatePokemonNames() {
        pokemonName1.setText(getMainTrainer().getActivePokemons().getFirst().getName());
        pokemonName2.setText(getBotTrainer().getActivePokemons().getFirst().getName());
    }

    private void updatePokemonHPs() {
        pokemonCurrentHP1.setText(String.valueOf(getMainTrainerPokemon().getCurrentHP()));
        pokemonCurrentHP2.setText(String.valueOf(getBotTrainerPokemon().getCurrentHP()));
        pokemonMaxHP1.setText("/" + getMainTrainerPokemon().getCurrentState("HP"));
        pokemonMaxHP2.setText("/" + getBotTrainerPokemon().getCurrentState("HP"));
    }

    public void updateHistory(String text, int size){
        Text newText = new Text(text);
        newText.setFont(new Font(20));
        history.getChildren().add(newText);
    }

    private void setSwitchButtons() {
        ArrayList<Pokemon> pokemonsTeam = getMainTrainer().getPokemonsTeam().getPokemons();
        for (Pokemon pokemon : pokemonsTeam) {
            Button switchButton = new Button(pokemon.getName());
            switchButton.setPrefSize(100, 30);
            switchButtons.getChildren().add(switchButton);
        }
    }


}