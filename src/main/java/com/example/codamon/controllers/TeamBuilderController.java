package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class TeamBuilderController {

    @FXML private VBox Pokemons;

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
    public void onBattleButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.BATTLE));
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
        userData.put("pokemonSpritesImageView" , pokemonSprites);
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
                (ArrayList<ImageView>) userData.get("pokemonSpritesImageView");
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
                                   Image secondTypeSprite) {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ImageView> pokemonSpritesImageView =
                (ArrayList<ImageView>) userData.get("pokemonSpritesImageView");
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

    private void setStatsProgressBars(String pokemonName) {
        HashMap<String, Object> userData =
                (HashMap<String, Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");

        ArrayList<String> pokemonStats = new ArrayList<>();
        pokemonStatsJSONReader(pokemonStats, pokemonName);

        for (int i = 0 ; i < StatBars.size() ; i++) {
            StatBars.get(i).setProgress(
                    Double.parseDouble(pokemonStats.get(i)) / 200);
        }
    }

    private void setPokemonMovesComboBoxes(String pokemonName) {
        ArrayList<ComboBox<String>> pokemonMovesComboBoxes =
                (ArrayList<ComboBox<String>>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get(
                                        "pokemonMovesComboBox");

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

        pokemonChoice.setOnAction((actionEvent -> {
            System.out.println(stage.getUserData());
            HashMap<String, Object> userData =
                    (HashMap<String, Object>) stage.getUserData();
            Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");
            Pokemon pokemon = new Pokemon(pokemonChoice.getValue());

            if (pokemonTrainer.getPokemonsTeam().getPokemons().size() == 0) {
                pokemonTrainer.getPokemonsTeam().getPokemons().add(pokemon);
            } else {
                pokemonTrainer.getPokemonsTeam().getPokemons().set(0, pokemon);
            }
            System.out.println(pokemonTrainer.getPokemonsTeam());

            setPokemonInfosVBoxVisible();

            ArrayList<String> pokemonTypes = readPokemonTypes(
                    pokemonChoice.getValue());
            Image pokemonSprite = getPokemonSprite(pokemonChoice.getValue());
            Image firstTypeSprite = getTypeSprite(pokemonTypes.get(0));
            Image secondTypeSprite = pokemonTypes.size() == 2 ?
                    getTypeSprite(pokemonTypes.get(1)) : getNullTypeSprite();
            setPokemonSprites(pokemonSprite, firstTypeSprite, secondTypeSprite);

            setPokemonMovesComboBoxes(pokemonChoice.getValue());

            setStatsProgressBars(pokemonChoice.getValue());
        }));

        return createPokemonVBox(pokemonLabel, pokemonChoice);
    }

    private void setPokemonInfosVBoxInUserData(VBox pokemonInfoVBox) {
        ArrayList<VBox> pokemonInfosVBox =
                (ArrayList<VBox>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get("pokemonInfosVBox");
        pokemonInfosVBox.add(pokemonInfoVBox);
        stage.setUserData(stage.getUserData());
    }

    private void setPokemonInfosVBoxVisible() {
        ArrayList<VBox> pokemonInfosVBox =
                (ArrayList<VBox>)
                        ((HashMap<String, Object>)
                                stage.getUserData()).get("pokemonInfosVBox");
        for (VBox pokemonInfoVBox : pokemonInfosVBox) {
            pokemonInfoVBox.setVisible(true);
        }
    }

    private VBox createItemChoiceBox() {
        Label itemLabel = new Label("Item");

        ChoiceBox<String> itemChoice = new ChoiceBox<>();
        itemChoice.getItems().add("Restes");
        itemChoice.getItems().add("Ballon");
        itemChoice.getItems().add("Orbe Vie");

        VBox ItemVBox = new VBox(5.0);
        ItemVBox.setAlignment(Pos.BOTTOM_CENTER);
        ItemVBox.getChildren().add(itemLabel);
        ItemVBox.getChildren().add(itemChoice);
        ItemVBox.setVisible(false);

        ArrayList<VBox> pokemonInfosVBox = new ArrayList<>();
        ((HashMap<String, Object>) stage.getUserData()).put("pokemonInfosVBox", pokemonInfosVBox);
        stage.setUserData(stage.getUserData());
        setPokemonInfosVBoxInUserData(ItemVBox);

        return ItemVBox;
    }

    private VBox createTalentChoiceBox() {
        Label talentLabel = new Label("Talent");

        ChoiceBox<String> talentChoice = new ChoiceBox<>();
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
        VBox ItemChoiceBox = createItemChoiceBox();
        VBox TalentChoiceBox = createTalentChoiceBox();

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

        moveChoice.setOnAction((actionEvent -> {
            HashMap<String, Object> userData =
                    (HashMap<String, Object>) stage.getUserData();

            ArrayList<ComboBox<String>> pokemonMovesComboBoxes =
                    (ArrayList<ComboBox<String>>)
                            userData.get("pokemonMovesComboBox");
            Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

            for (ComboBox<String> pokemonMoveComboBox : pokemonMovesComboBoxes) {
                if (moveChoice.getValue() == null) {
                } else if (moveChoice.getValue().equals(pokemonMoveComboBox.getValue()) && moveChoice != pokemonMoveComboBox) {
//                    pokemonTrainer.getPokemonsTeam().getPokemons().get(0).addMove(moveChoice.getValue());
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
        userData.put("pokemonMovesComboBox", pokemonMovesComboBoxes);
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
        userData.put("pokemonStatsProgressBar", StatBars);
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
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");
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
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");
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
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");
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
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");
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
                (ArrayList<ProgressBar>) userData.get("pokemonStatsProgressBar");
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
        HBox pokemon = createPokemon();
        Pokemons.getChildren().add(pokemon);
    }
}
