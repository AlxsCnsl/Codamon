package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamBuilderController {

    @FXML private VBox Pokemons;
    @FXML private Button addPokemonButton;
    @FXML private VBox PokemonsAndButton;
    @FXML private ScrollPane ScrollPane;

    public TeamBuilderController() {

    }

    private Stage stage;

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.stage = stage;
    }

    @FXML
    public void onExitButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.MENU));
    }

    @FXML
    public void onBattleButtonClick() throws IOException, InterruptedException {

        Map<SceneName, Scene> scenes = PokeApp.getScenes();
        scenes.put(
                SceneName.BATTLE, new BattleView(stage).getScene());
        stage.setScene(PokeApp.getScenes().get(SceneName.BATTLE));

    }

    public int getPokemonIndex() {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");
        int pokemonIndex = pokemonTrainer.getPokemonsTeam().getPokemons().size();
        return pokemonIndex;
    }

    private ImageView createPokemonImageView(String pokemonName) {
        URL pokemonSpriteURL = getClass().getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName + "/" + pokemonName + "_face.png");
        Image pokemonSprite = new Image(pokemonSpriteURL.toExternalForm());

        ImageView pokemonSpriteImageView = new ImageView(pokemonSprite);
        pokemonSpriteImageView.setFitWidth(100);
        pokemonSpriteImageView.setPickOnBounds(true);
        pokemonSpriteImageView.setPreserveRatio(true);

        ArrayList<ImageView> pokemonSprites = new ArrayList<>();
        pokemonSprites.add(pokemonSpriteImageView);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        userData.put("pokemonSpritesImageView" + getPokemonIndex(), pokemonSprites);
        stage.setUserData(userData);

        return pokemonSpriteImageView;
    }

    private ImageView createTypeImageView(String typeName) {
        URL typeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/" +
                        typeName + ".png");
        Image typeSprite = new Image(typeSpriteURL.toExternalForm());

        ImageView typeImageView = new ImageView(typeSprite);
        typeImageView.setFitWidth(50);
        typeImageView.setPickOnBounds(true);
        typeImageView.setPreserveRatio(true);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ImageView> pokemonSprites =
                (ArrayList<ImageView>) userData.get("pokemonSpritesImageView" + getPokemonIndex());
        pokemonSprites.add(typeImageView);
        stage.setUserData(userData);

        return typeImageView;
    }

    private HBox createPokemonSpritesHBox() {
        ImageView pokemonSpriteImageView = createPokemonImageView(
                "null");

        ImageView firstTypeImageView = createTypeImageView("null");
        ImageView secondTypeImageView = createTypeImageView("null");

        HBox pokemonSprites = new HBox(10.0);
        pokemonSprites.setAlignment(Pos.CENTER);
        pokemonSprites.getChildren().add(pokemonSpriteImageView);
        pokemonSprites.getChildren().add(firstTypeImageView);
        pokemonSprites.getChildren().add(secondTypeImageView);

        return pokemonSprites;
    }

    private void pokemonListJSONReader(ArrayList<String> pokemonList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemonList.json";
        InputStream inputStream = getClass().getResourceAsStream(pathName);
        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            for (int i = 0; i < rootNode.get("pokemonList").size() ; i++) {
                pokemonList.add(rootNode.get("pokemonList").get(i).asText());
            }
        } catch (IOException e) {
            System.out.println(pathName + " could not be opened");
        }
    }

    private void pokemonTypesJSONReader(ArrayList<String> pokemonTypes,
                                        String pokemonName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemon/" +
                        pokemonName.toLowerCase()+".json";

        InputStream inputStream = getClass().getResourceAsStream(pathName);

        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            for (int i = 0 ; i < rootNode.get("types").size() ; i++) {
                pokemonTypes.add(rootNode.get("types").get(i).asText());
            }
        } catch (IOException e){
            System.out.println(pathName + " could not be opened");
        }
    }

    private void pokemonMovesJSONReader(ArrayList<String> pokemonMoves,
                                        String pokemonName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemon/" +
                pokemonName.toLowerCase()+".json";

        InputStream inputStream = getClass().getResourceAsStream(pathName);

        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            for (int i = 0 ; i < rootNode.get("move").size() ; i++) {
                pokemonMoves.add(rootNode.get("move").get(i).asText());
            }
        } catch (IOException e){
            System.out.println(pathName + " could not be opened");
        }
    }

    private void pokemonStatsJSONReader(ArrayList<String> pokemonStats,
                                        String pokemonName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemon/" +
                pokemonName.toLowerCase()+".json";
        InputStream inputStream = getClass().getResourceAsStream(pathName);
        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            pokemonStats.add(rootNode.get("stats").get("HP").asText());
            pokemonStats.add(rootNode.get("stats").get("ATK").asText());
            pokemonStats.add(rootNode.get("stats").get("DEF").asText());
            pokemonStats.add(rootNode.get("stats").get("SPA").asText());
            pokemonStats.add(rootNode.get("stats").get("SPD").asText());
            pokemonStats.add(rootNode.get("stats").get("SPE").asText());
        } catch (IOException e){
            System.out.println(pathName + " could not be opened");
        }
    }

    private ArrayList<String> createPokemonNamesList() {
        ArrayList<String> pokemonNames = new ArrayList<>();
        pokemonListJSONReader(pokemonNames);
        return pokemonNames;
    }

    private ComboBox<String> setPokemonChoices(ArrayList<String> pokemonNames) {
        ComboBox<String> pokemonChoice = new ComboBox<>();
        for (String pokemonName : pokemonNames) {
            pokemonChoice.getItems().add(pokemonName);
        }

        return pokemonChoice;
    }

    private ArrayList<String> readPokemonTypes(String pokemonName) {
        ArrayList<String> pokemonTypes = new ArrayList<>();
        pokemonTypesJSONReader(pokemonTypes, pokemonName);
        return pokemonTypes;
    }

    private ArrayList<String> readPokemonMoves(String pokemonName) {
        ArrayList<String> pokemonMoves = new ArrayList<>();
        pokemonMovesJSONReader(pokemonMoves, pokemonName);
        return pokemonMoves;
    }

    private Image getPokemonSprite(String pokemonName) {
        URL pokemonSpriteURL = getClass().getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName.toLowerCase() + "/" +
                        pokemonName.toLowerCase() + "_face.png");

        return new Image(
                pokemonSpriteURL.toExternalForm());
    }

    private Image getTypeSprite(String pokemonFirstType) {
        URL firstTypeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/" +
                        pokemonFirstType.toLowerCase() + ".png");

        return new Image(
                firstTypeSpriteURL.toExternalForm());
    }

    private Image getNullTypeSprite() {
        URL nullTypeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/null.png");

        return new Image(
                nullTypeSpriteURL.toExternalForm());
    }

    private void setPokemonSprites(Image pokemonSprite, Image firstTypeSprite,
                                   Image secondTypeSprite, int pokemonID) {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ImageView> pokemonSpritesImageView =
                (ArrayList<ImageView>) userData.get("pokemonSpritesImageView" + pokemonID);
        pokemonSpritesImageView.get(0).setImage(pokemonSprite);
        pokemonSpritesImageView.get(1).setImage(firstTypeSprite);
        pokemonSpritesImageView.get(2).setImage(secondTypeSprite);
    }

    private VBox createPokemonVBox(Label pokemonLabel,
                                   ComboBox<String> pokemonChoice) {
        VBox PokemonVBox = new VBox(5.0);
        PokemonVBox.setAlignment(Pos.BOTTOM_CENTER);
        PokemonVBox.getChildren().add(pokemonLabel);
        PokemonVBox.getChildren().add(pokemonChoice);
        return PokemonVBox;
    }

    private void setStatsProgressBars(String pokemonName, int pokemonID) {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + pokemonID);

        ArrayList<String> pokemonStats = new ArrayList<>();
        pokemonStatsJSONReader(pokemonStats, pokemonName);

        for (int i = 0 ; i < StatBars.size() ; i++) {
            StatBars.get(i).setProgress(
                    Double.parseDouble(pokemonStats.get(i)) / 200);
        }
    }

    private void setPokemonMovesComboBoxes(String pokemonName, int pokemonID) {
        ArrayList<ComboBox<String>> pokemonMovesComboBoxes =
                (ArrayList<ComboBox<String>>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get(
                                        "pokemonMovesComboBox" + pokemonID);

        ArrayList<String> pokemonMoves = new ArrayList<>();
        pokemonMovesJSONReader(pokemonMoves, pokemonName);

        for (ComboBox<String> pokemonMovesComboBox : pokemonMovesComboBoxes) {
            pokemonMovesComboBox.getItems().removeAll(pokemonMovesComboBox.getItems());
            for (String pokemonMove : pokemonMoves) {
                pokemonMovesComboBox.getItems().add(pokemonMove);
            }
        }
    }

    private VBox createPokemonComboBox() {
        Label pokemonLabel = new Label("Pokémon");
        ArrayList<String> pokemonNames = createPokemonNamesList();
        ComboBox<String> pokemonChoice = setPokemonChoices(pokemonNames);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

        pokemonChoice.setOnAction((actionEvent -> {
            System.out.println(stage.getUserData());
            int pokemonID = Integer.parseInt(pokemonChoice.
                    getParent().getParent().getParent().getParent().getId());

            Pokemon pokemon = new Pokemon(pokemonChoice.getValue());

            int index = pokemonID + 1;

            if (pokemonTrainer.getPokemonsTeam().getPokemons().size() < index) {
                pokemonTrainer.addPokemon(pokemon);
            } else {
                pokemonTrainer.setPokemon(pokemonID, pokemon);

            }
            System.out.println(pokemonTrainer.getPokemonsTeam());

            setPokemonInfosVBoxVisible(pokemonID);

            ArrayList<String> pokemonTypes = readPokemonTypes(
                    pokemonChoice.getValue());
            Image pokemonSprite = getPokemonSprite(pokemonChoice.getValue());
            Image firstTypeSprite = getTypeSprite(pokemonTypes.get(0));
            Image secondTypeSprite = pokemonTypes.size() == 2 ?
                    getTypeSprite(pokemonTypes.get(1)) : getNullTypeSprite();

            setPokemonSprites(pokemonSprite, firstTypeSprite, secondTypeSprite,
                    pokemonID);

            setPokemonMovesComboBoxes(pokemonChoice.getValue(), pokemonID);

            setStatsProgressBars(pokemonChoice.getValue(), pokemonID);
        }));

        return createPokemonVBox(pokemonLabel, pokemonChoice);
    }

    private void setPokemonInfosVBoxInUserData(VBox pokemonInfoVBox) {
        ArrayList<VBox> pokemonInfosVBox =
                (ArrayList<VBox>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get("pokemonInfosVBox" + getPokemonIndex());
        pokemonInfosVBox.add(pokemonInfoVBox);
        stage.setUserData(stage.getUserData());
    }

    private void setPokemonInfosVBoxVisible(int pokemonID) {
        System.out.println("pokemonID : " + pokemonID);
        ArrayList<VBox> pokemonInfosVBox =
                (ArrayList<VBox>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get("pokemonInfosVBox" + pokemonID);
        for (VBox pokemonInfoVBox : pokemonInfosVBox) {
            pokemonInfoVBox.setVisible(true);
        }
        if (pokemonID < 5) {
            addPokemonButton.setVisible(true);
        }
    }

    private VBox createItemVBox() {
        Label itemLabel = new Label("Item");

        ComboBox<String> itemChoice = new ComboBox<>();
        itemChoice.getItems().add("Restes");
        itemChoice.getItems().add("Ballon");
        itemChoice.getItems().add("Orbe Vie");

        VBox ItemVBox = new VBox(5.0);
        ItemVBox.setAlignment(Pos.BOTTOM_CENTER);
        ItemVBox.getChildren().add(itemLabel);
        ItemVBox.getChildren().add(itemChoice);
        ItemVBox.setVisible(false);

        ArrayList<VBox> pokemonInfosVBox = new ArrayList<>();
        ((HashMap<String, Object>) stage.getUserData()).put("pokemonInfosVBox" + getPokemonIndex(), pokemonInfosVBox);
        stage.setUserData(stage.getUserData());
        setPokemonInfosVBoxInUserData(ItemVBox);

        return ItemVBox;
    }

    private VBox createTalentVBox() {
        Label talentLabel = new Label("Talent");

        ComboBox<String> talentChoice = new ComboBox<>();
        talentChoice.getItems().add("Agitation");
        talentChoice.getItems().add("Sérénité");
        talentChoice.getItems().add("Chanceux");

        VBox TalentVBox = new VBox(5.0);
        TalentVBox.setAlignment(Pos.BOTTOM_CENTER);
        TalentVBox.getChildren().add(talentLabel);
        TalentVBox.getChildren().add(talentChoice);
        TalentVBox.setVisible(false);

        setPokemonInfosVBoxInUserData(TalentVBox);

        return TalentVBox;
    }

    private HBox createPokemonDetailsHBox() {
        VBox PokemonChoiceBox = createPokemonComboBox();
        VBox ItemChoiceBox = createItemVBox();
        VBox TalentChoiceBox = createTalentVBox();

        HBox pokemonDetails = new HBox(5.0);
        pokemonDetails.setAlignment(Pos.CENTER);
        pokemonDetails.getChildren().add(PokemonChoiceBox);
        pokemonDetails.getChildren().add(ItemChoiceBox);
        pokemonDetails.getChildren().add(TalentChoiceBox);

        return pokemonDetails;
    }

    private VBox spritesAndDetailsVBox() {
        //===== Pokemon Sprites HBox =====
        HBox pokemonSprites = createPokemonSpritesHBox();
        //====================

        //===== Pokémon Details HBox =====;
        HBox pokemonDetails = createPokemonDetailsHBox();
        //====================

        //===== Sprites and Details VBox =====
        VBox spritesAndDetailsVBox = new VBox();
        spritesAndDetailsVBox.setAlignment(Pos.CENTER);
        spritesAndDetailsVBox.getChildren().add(pokemonSprites);
        spritesAndDetailsVBox.getChildren().add(pokemonDetails);
        //=====================================

        return spritesAndDetailsVBox;
    }

    private ComboBox<String> createMoveComboBox() {
        ComboBox<String> moveChoice = new ComboBox<>();
        moveChoice.setMinWidth(125);

        final String[] previousValue = {null};
        moveChoice.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            // 'oldValue' is the previous selection before the change
            System.out.println("Changed from: " + oldValue + " to: " + newValue);
            // Update our previous value storage if needed
            previousValue[0] = oldValue;
        });

        moveChoice.setOnAction((actionEvent -> {
            HashMap<String, Object> userData =
                    (HashMap<String, Object>) stage.getUserData();

            int pokemonID =
                    Integer.parseInt(moveChoice.getParent().getParent().getId());

            ArrayList<ComboBox<String>> pokemonMovesComboBoxes =
                    (ArrayList<ComboBox<String>>)
                            userData.get("pokemonMovesComboBox" + pokemonID);
            Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

            if (moveChoice.getValue() != null && pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID).getMoves().size() < 4) {
                pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID).switchMove(previousValue[0], moveChoice.getValue());
            } else if (pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID).getMoves().size() < 4) {
                System.out.println("test if");
                pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID).addMove(moveChoice.getValue());
                System.out.println(pokemonTrainer.getPokemonsTeam());
            } else {
                System.out.println("test else");
                System.out.println("previousValue : " + previousValue[0]);
                pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID).switchMove(previousValue[0], moveChoice.getValue());
            }

            System.out.println(pokemonTrainer.getPokemonsTeam());

            for (ComboBox<String> pokemonMoveComboBox : pokemonMovesComboBoxes) {
                if (moveChoice.getValue() == null) {
                } else if (moveChoice.getValue().equals(pokemonMoveComboBox.getValue()) && moveChoice != pokemonMoveComboBox) {


                    pokemonMoveComboBox.setValue("");
                }
            }
        }));

        return moveChoice;
    }

    private VBox createMovesVBox() {
        Label movesLabel = new Label("Attaques");

        VBox MovesVBox = new VBox();
        MovesVBox.getChildren().add(movesLabel);
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ComboBox<String>> pokemonMovesComboBoxes = new ArrayList<>();

        for (int i = 0 ; i < 4 ; i++) {
            ComboBox<String> moveChoice = createMoveComboBox();
            moveChoice.setValue("");
            pokemonMovesComboBoxes.add(moveChoice);
            MovesVBox.getChildren().add(moveChoice);
        }
        userData.put("pokemonMovesComboBox" + getPokemonIndex(), pokemonMovesComboBoxes);
        stage.setUserData(userData);

        MovesVBox.setAlignment(Pos.CENTER);
        MovesVBox.setVisible(false);

        setPokemonInfosVBoxInUserData(MovesVBox);
        return MovesVBox;
    }

    private HBox createHPDisplay() {
        Label HPLabel = new Label("HP");
        ProgressBar HPBar = new ProgressBar(0);
        HBox HPDisplay = new HBox(5.0);
        HPDisplay.setAlignment(Pos.CENTER_RIGHT);
        HPDisplay.getChildren().add(HPLabel);
        HPDisplay.getChildren().add(HPBar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars = new ArrayList<>();
        StatBars.add(HPBar);
        userData.put("pokemonStatsProgressBar" + getPokemonIndex(), StatBars);
        stage.setUserData(userData);

        return HPDisplay;
    }

    private HBox createATKDisplay() {
        Label ATKLabel = new Label("ATK");
        ProgressBar ATKBar = new ProgressBar(0);
        HBox ATKDisplay = new HBox(5.0);
        ATKDisplay.setAlignment(Pos.CENTER_RIGHT);
        ATKDisplay.getChildren().add(ATKLabel);
        ATKDisplay.getChildren().add(ATKBar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + getPokemonIndex());
        StatBars.add(ATKBar);
        stage.setUserData(userData);

        return ATKDisplay;
    }

    private HBox createDEFDisplay() {
        Label DEFLabel = new Label("DEF");
        ProgressBar DEFBar = new ProgressBar(0);
        HBox DEFDisplay = new HBox(5.0);
        DEFDisplay.setAlignment(Pos.CENTER_RIGHT);
        DEFDisplay.getChildren().add(DEFLabel);
        DEFDisplay.getChildren().add(DEFBar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + getPokemonIndex());
        StatBars.add(DEFBar);
        stage.setUserData(userData);

        return DEFDisplay;
    }

    private HBox createSPADisplay() {
        Label SPALabel = new Label("SPA");
        ProgressBar SPABar = new ProgressBar(0);
        HBox SPADisplay = new HBox(5.0);
        SPADisplay.setAlignment(Pos.CENTER_RIGHT);
        SPADisplay.getChildren().add(SPALabel);
        SPADisplay.getChildren().add(SPABar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + getPokemonIndex());
        StatBars.add(SPABar);
        stage.setUserData(userData);

        return SPADisplay;
    }

    private HBox createSPDDisplay() {
        Label SPDLabel = new Label("SPD");
        ProgressBar SPDBar = new ProgressBar(0);
        HBox SPDDisplay = new HBox(5.0);
        SPDDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPDDisplay.getChildren().add(SPDLabel);
        SPDDisplay.getChildren().add(SPDBar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + getPokemonIndex());
        StatBars.add(SPDBar);
        stage.setUserData(userData);

        return SPDDisplay;
    }

    private HBox createSPEDisplay() {
        Label SPELabel = new Label("SPE");
        ProgressBar SPEBar = new ProgressBar(0);
        HBox SPEDisplay = new HBox(5.0);
        SPEDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPEDisplay.getChildren().add(SPELabel);
        SPEDisplay.getChildren().add(SPEBar);

        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + getPokemonIndex());
        StatBars.add(SPEBar);
        stage.setUserData(userData);

        return SPEDisplay;
    }

    private VBox createStatsVBox() {
        Label statsLabel = new Label("Statistiques");

        VBox StatsVBox = new VBox();
        StatsVBox.setAlignment(Pos.CENTER);
        StatsVBox.getChildren().add(statsLabel);
        StatsVBox.getChildren().add(createHPDisplay());
        StatsVBox.getChildren().add(createATKDisplay());
        StatsVBox.getChildren().add(createDEFDisplay());
        StatsVBox.getChildren().add(createSPADisplay());
        StatsVBox.getChildren().add(createSPDDisplay());
        StatsVBox.getChildren().add(createSPEDisplay());
        StatsVBox.setVisible(false);

        setPokemonInfosVBoxInUserData(StatsVBox);

        return StatsVBox;
    }

    private HBox createPokemon() {
        VBox spritesAndDetailsVBox = spritesAndDetailsVBox();

        VBox MovesVBox = createMovesVBox();

        VBox StatsVBox = createStatsVBox();

        HBox pokemon = new HBox(10.0);
        pokemon.setId("Pokemon");
        pokemon.setAlignment(Pos.BOTTOM_CENTER);
        pokemon.setStyle("-fx-background-color:lightgray");

        Insets insets = new Insets(5.0);
        pokemon.setPadding(insets);

        pokemon.getChildren().add(spritesAndDetailsVBox);
        pokemon.getChildren().add(MovesVBox);
        pokemon.getChildren().add(StatsVBox);

        return pokemon;
    }

    @FXML
    public void onAddPokemonButtonClick() {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

        Button deletePokemonButton = new Button("Delete Pokemon");
        HBox pokemon = createPokemon();
        pokemon.getChildren().add(deletePokemonButton);
        deletePokemonButton.setOnAction((actionEvent -> {
            int pokemonID =
                    Integer.parseInt(deletePokemonButton.getParent().getId());
            System.out.println(deletePokemonButton.getParent().getId());

            System.out.println("Deleted pokemon : " + pokemonTrainer.getPokemonsTeam().getPokemons().get(pokemonID));
            Pokemons.getChildren().remove(pokemon);
            pokemonTrainer.getPokemonsTeam().getPokemons().remove(pokemonID);
            System.out.println(pokemonTrainer.getPokemonsTeam());

            if (pokemonTrainer.getPokemonsTeam().getPokemons().size() > pokemonID) {
                System.out.println("test if null");
                System.out.println("size : " + pokemonTrainer.getPokemonsTeam().getPokemons().size());
                int i = 0;

                for (i = 0 ; i < pokemonTrainer.getPokemonsTeam().getPokemons().size() - pokemonID ; i++) {
                    ArrayList<ImageView> nextPokemonSpritesImageView = (ArrayList<ImageView>) userData.get("pokemonSpritesImageView" + (pokemonID + (i + 1)));
                    ArrayList<VBox> nextPokemonInfosVBox = (ArrayList<VBox>) userData.get("pokemonInfosVBox" + (pokemonID + (i + 1)));
                    ArrayList<ProgressBar> nextPokemonStatsProgressBars = (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar" + (pokemonID + (i + 1)));
                    ArrayList<ComboBox<String>> nextPokemonMovesComboBox = (ArrayList<ComboBox<String>>) userData.get("pokemonMovesComboBox" + (pokemonID + (i + 1)));

                    userData.put("pokemonSpritesImageView" + (pokemonID + i), nextPokemonSpritesImageView);
                    userData.put("pokemonInfosVBox" + (pokemonID + i), nextPokemonInfosVBox);
                    userData.put("pokemonStatsProgressBar" + (pokemonID + i), nextPokemonStatsProgressBars);
                    userData.put("pokemonMovesComboBox" + (pokemonID + i), nextPokemonMovesComboBox);

                    System.out.println("HBox to update : " + Pokemons.getChildren().get(pokemonID + i));
                    System.out.println("HBox ID before updating : " + Pokemons.getChildren().get(pokemonID + i).getId());
                    Pokemons.getChildren().get((pokemonID + i)).setId(
                            String.valueOf((pokemonID + i)));
                    System.out.println("pokemon ID put in HBox : " + (pokemonID + i));
                }

                System.out.println("i : " + i);
                userData.remove("pokemonSpritesImageView" + (pokemonID + i));
                userData.remove("pokemonInfosVBox" + (pokemonID + i));
                userData.remove("pokemonStatsProgressBar" + (pokemonID + i));
                userData.remove("pokemonMovesComboBox" + (pokemonID + i));

                System.out.println("userdata : " + userData);
            }
        }));

        Pokemons.getChildren().add(pokemon);

        Pokemons.getChildren().get(getPokemonIndex()).setId(
                String.valueOf(getPokemonIndex()));

        System.out.println("pokemon : " + pokemon);
        System.out.println("pokemon id : " + pokemon.getId());

        addPokemonButton.setVisible(false);
    }
}
