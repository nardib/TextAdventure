package org.zssn.escaperoom;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 * Class that manages the game and the saving and loading of the game status from the S3 bucket
 */
public class GameManager {

    /**
     * The name of the configuration file that contains the bucket name, the key name and the region
     */
    private static final String CONFIG_FILE = "/config.properties";

    /**
     * The name of the bucket where the game status is saved
     */
    private static String BUCKET_NAME;

    /**
     * The name of the file where the game status is saved
     */
    private static String KEY_NAME;

    /**
     * The region of the bucket
     */
    private Region REGION;

    /**
     * The game object that contains the status of the game
     */
    private Game g;

    /**
     * Tells if the game is being configured
     */
    private boolean configuring;

    /**
     * The turn the game is being configured
     */
    private int count;

    /**
     * Name of the player
     */
    private String playerName;

    /**
     * Gender of the player
     */
    private String playerGender;

    /**
     * Name of the enemy
     */
    private String enemyName = "Er cattivone";

    /**
     * Gender of the enemy
     */
    private String enemyGender = "neutral";

    /**
     * Counter of moves the player does bofore the enemy moves
     */
    private int movesBeforeEnemy;

    /**
     * Tells if the enemy attacks the player
     */
    private boolean enemyAttacks;

    /**
     * Boolean that tells if the game is running
     */
    private boolean gameOn;

    /**
     * Boolean that tells if the game is won
     */
    private boolean gameWon;

    /**
     * Boolean that tells if the game is lost
     */
    private boolean gameLost;

    /**
     * Boolean to say if the last progress was saved
     */
    private boolean saved;

    /**
     * Boolean to say if we are asking to save the progress before quitting
     */
    private boolean askingToSave;

    /**
     * Defualt constructor for the GameManager class
     */
    public GameManager() {
        g = null;
        configuring = false;
        count = 0;
        gameOn = false;
        gameWon = false;
        gameLost = false;
        saved = false;
        askingToSave = false;
        loadConfig();
    }

    /**
     * Method that loads the configuration file that contains the bucket name, the key name and the region
     */
    private void loadConfig() { //loads the configuration file
        try {
            InputStream input = GameManager.class.getResourceAsStream(CONFIG_FILE);
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Warning: unable to find 'config.properties' file in the classpath. Consult the README.md file for instructions.");
            }
            prop.load(input);
            BUCKET_NAME = prop.getProperty("bucketName");
            KEY_NAME = prop.getProperty("keyName");
            REGION = Region.of(prop.getProperty("region"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Warning: unable to load properly 'config.properties' file in the classpath. Consult the README.md file for instructions.");
        }
    }

    /**
     * Method that saves the status of the game in the S3 bucket
     * 
     * @param game the game object that contains the status of the game
     */
    public void saveProgress(Game game) { //fuction to be used for saving status
        String message = serialize(game);
        upLoad(message);
    }

    /**
     * Method that resumes the status of the game from the S3 bucket
     * 
     * @return the game object that contains the status of the game
     */
    public Game resumeProgress() { //function to be used to resume status
        String message = downLoad();
        Game game = deserialize(message);
        return game;
    }

    /**
     * Method that serializes the game object into a string
     * 
     * @param game the game object that contains the status of the game
     * @return the string that contains the serialized game object
     */
    private String serialize(Game game) { //converts object in string format, eg: "{\"name\":\"John",\"age\":\"30"}"
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Converte l'oggetto Java in una stringa JSON
            String jsonString = objectMapper.writeValueAsString(game); //usa il metodo writeValueAsSrting dell'oggetto ObjectMapper per convertire l'oggetto in una string come nell'esempio sopra
            return jsonString; //ritorna l'oggetto string contente il JSON dell'oggetto Game
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during serialization");
        }
    }

    /**
     * Method that deserializes the string into a game object
     * 
     * @param gameString the string that contains the serialized game object
     * @return the game object that contains the status of the game
     */
    private Game deserialize(String gameString) { //converts string format into Game object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Converte la stringa JSON in un oggetto Java
            Game obj = objectMapper.readValue(gameString, Game.class); //usa la stringa jsonString per costruire l'oggetto di tipo Game
            return obj; //ritorna l'oggetto inizializzato
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deserialization");
        }
    }

    /**
     * Method that uploads the string into the S3 bucket
     * 
     * @param message the string that contains the serialized game object
     */
    private void upLoad(String message) {
        try {
            S3Client s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

            // Crea un file temporaneo e scrivi il contenuto
            Path tempFile = Files.createTempFile("temp", ".json");

            Files.write(tempFile, message.getBytes(), StandardOpenOption.WRITE);

            // Crea una richiesta PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            // Carica il file nel bucket S3
            s3.putObject(putObjectRequest, tempFile);

            // Elimina il file temporaneo
            Files.delete(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during upload! Check the README.md file for instructions or check the error message.");
        }
    }

    /**
     * Method that downloads the string from the S3 bucket
     * 
     * @return the string that contains the serialized game object
     */
    private String downLoad() { //downloads the string from the bucket
        try {
            S3Client s3 = S3Client.builder()
                    .region(REGION)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            // Scarica il file dal bucket S3
            Path tempFile = Files.createTempFile("temp", ".json");

            if (Files.exists(tempFile)) {
                Files.delete(tempFile);
            }

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            s3.getObject(getObjectRequest, tempFile);

            // Leggi il contenuto del file
            String content = new String(Files.readAllBytes(tempFile));

            // Elimina il file temporaneo
            Files.delete(tempFile);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during download! Check the README.md file for instructions or check the error message.");
        }
    }

    /**
     * Method that returns the game object
     * 
     * @return the game object
     */
    public Game getGame() {
        return g;
    }

    /**
     * Method that performs the next move in the game
     * 
     * @param move the move to be performed
     * @return the string that contains the result of the move
     */
    public String nextMove(String move) {
        move = move.trim();

        if (askingToSave) {
            if (move.equalsIgnoreCase("yes") || move.equalsIgnoreCase("y")) {
                saveProgress(g);
                askingToSave = false;
                gameOn = false;
                g = null;
                gameLost = false;
                gameWon = false;
                return "Game saved!\nWe are sorry to see you go! Hope to see you soon!\nIf you want to resume the game, enter 'resume' or to start a new game enter 'new game'.";
            }
            else if (move.equalsIgnoreCase("no") || move.equalsIgnoreCase("n")) {
                askingToSave = false;
                gameOn = false;
                g = null;
                gameLost = false;
                gameWon = false;
                return "\nWe are sorry to see you go! Hope to see you soon!\nIf you want to resume the game, enter 'resume' or to start a new game enter 'new game'.";
            }
            return "Input not valid. Valid inputs are 'yes' or 'no'.";
        }

        if (move.equals("quit")) {
            System.exit(0);
        }
        
        if (configuring) {
            if (count == 0) {
                playerName = move;
                count++;
                return "Name setted to " + playerName +"!\n\nEnter the gender for the player:\n1.Male\n2.Female\n3.Neutral";
            }
            else if (count == 1) {
                if (move.equals("1"))
                    playerGender = "male";
                else if (move.equals("2"))
                    playerGender = "female";
                else if (move.equals("3"))
                    playerGender = "neutral";
                else 
                    return "Input not valid. Valid inputs are 1, 2 or 3.";
                count++;
                return "Gender setted to " + playerGender + "\n\nEnter 'true' (1) if the enemy attacks the player, 'false' (0) otherwise.";
            }
            else if (count == 2) {
                if (move.equalsIgnoreCase("true") || move.equalsIgnoreCase("1") || move.equalsIgnoreCase("t"))
                    enemyAttacks = true;
                else if (move.equalsIgnoreCase("false") || move.equalsIgnoreCase("0") || move.equalsIgnoreCase("f")){
                    enemyAttacks = false;
                    movesBeforeEnemy = 1;
                    g = new Game(playerName, playerGender, enemyName, enemyGender, movesBeforeEnemy, enemyAttacks);
                    configuring = false;
                    count = 0;
                    gameOn = true;
                    return "\nGame configured! Enter 'help' to see the list of commands.\nTo win the game you have to find all the stars in the map and fill the holes in the central room with them.\nGood luck!";
                }
                else
                    return "Input not valid. Valid inputs are 'true' or 'false'.";
                count++;
                return "\nEnter the name for the enemy (type 0 to use the defualt configuraton): ";
            }
            else if (count == 3) {
                if (move.equals("0")) {
                    count += 2;
                    return "Ok, your enemy is " + enemyName + "\n\nChoose the difficulty level:\n1.Easy\n2.Medium\n3.Hard";
                }
                enemyName = move;
                count++;
                return "Name setted to " + playerName +"!\n\nEnter the gender for the player:\n1.Male\n2.Female\n3.Neutral";
            }
            else if (count == 4) {
                if (move.equals("1"))
                    enemyGender = "m";
                else if (move.equals("2"))
                    enemyGender = "f";
                else if (move.equals("3"))
                    enemyGender = "n";
                else
                    return "Input not valid. Valid inputs are 1, 2 or 3.";
                count++;
                return "\nChoose the difficulty level:\n1.Easy\n2.Medium\n3.Hard";
            }
            
            else if (count == 5) {
                if (move.equals("1"))
                    movesBeforeEnemy = 5;
                else if (move.equals("2"))
                    movesBeforeEnemy = 3;
                else if (move.equals("3"))
                    movesBeforeEnemy = 1;
                else
                    return "Input not valid. Valid inputs are 1, 2 or 3.";
                g = new Game(playerName, playerGender, enemyName, enemyGender, movesBeforeEnemy, enemyAttacks);
                configuring = false;
                count = 0;
                gameOn = true;
                return "\nGame configured! Enter 'help' to see the list of commands.\nTo win the game you have to find all the stars in the map and fill the holes in the central room with them.\nGood luck!";
            }
            return "Error during configuration!";
        }
        else if (!gameOn) {
            if (move.equalsIgnoreCase("resume")) {
                try {
                    g = resumeProgress();
                    gameOn = true;
                    saved = true;
                    g.saveCurrentState();
                    return "\nGame resumed!\n" + g.nextMove("status").substring(("\n-------------------------- Input : status --------------------------\n").length());
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
            if (move.equalsIgnoreCase("new game"))
            {
                configuring = true;
                saved = false;
                return "\nEnter the name for the player:";
            }
            return "Game not started. Enter 'new game' to start a new game or 'resume' to resume a previous game.";
        }
        else {
            if (move.equalsIgnoreCase("save")) {
                try {
                    saveProgress(g);
                    saved = true;
                    return "Game saved!";
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
            if (move.equals("exit")) {
                if (saved) {
                    gameOn = false;
                    g = null;
                    gameLost = false;
                    gameWon = false;
                    return "We are sorry to see you go! Hope to see you soon!\nIf you want to resume the game, enter 'resume' or to start a new game enter 'new game'.";
                }
                askingToSave = true;
                return "\nYou have not saved the progress. Do you want to save it before quitting? (yes/no)";
            }

            String result = g.nextMove(move);
            saved = false;
            if (g.checkWin()) {
                gameOn = false;
                g = null;
                gameWon = true;
                return "\n-------------------------- Input : " + move + " --------------------------\n\nYou win! " + playerName + " filled all the star holes!\nIf you want to start a new game enter 'new game'.";
            }
            else if (g.checkLost()) {
                gameOn = false;
                g = null;
                gameLost = true;
                return "\n-------------------------- Input : " + move + " --------------------------\n\nGame Over! " + enemyName + " killed " + playerName + "!\nIf you want to start a new game enter 'new game'.";
            }
            return result;
        }
    }

    /**
     * Method that returns if the game was won
     * 
     * @return true if the game was won, false otherwise
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Method that returns if the game was lost
     * 
     * @return true if the game was lost, false otherwise
     */
    public boolean isGameLost() {
        return gameLost;
    }
}