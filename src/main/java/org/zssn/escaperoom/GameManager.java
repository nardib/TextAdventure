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
     * Defualt constructor for the GameManager class
     */
    public GameManager() {
        g = new Game("Filippo", "M", "Er cattivone", "N", 3, true);
        loadConfig();
    }

    /**
     * Consructor for the GameManager class
     * 
     * @param name the name of the player
     * @param gender the gender of the player
     * @param count the number of moves made by the player before the enemy performs an action
     * @param isEnemyAlive a boolean that indicates if the enemy is alive
     */
    public GameManager(String name, String gender, int count, boolean isEnemyAlive) {
        g = new Game(name, gender, "Er cattivone", "N", count, isEnemyAlive);
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
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
                return;
            }
            prop.load(input);
            BUCKET_NAME = prop.getProperty("bucketName");
            KEY_NAME = prop.getProperty("keyName");
            REGION = Region.of(prop.getProperty("region"));
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        return null;
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
        }
        return null;
    }

    /**
     * Method that uploads the string into the S3 bucket
     * 
     * @param message the string that contains the serialized game object
     */
    private void upLoad(String message) { //loads the string into the bucket
        S3Client s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
            // Crea un file temporaneo e scrivi il contenuto
            Path tempFile = Files.createTempFile("temp", ".json");

            Files.write(tempFile, message.getBytes(), StandardOpenOption.WRITE);    //Nardi: i changed contet with message (now compile, but not sure if it's correct)

            // Crea una richiesta PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            // Carica il file nel bucket S3
            s3.putObject(putObjectRequest, tempFile);
            System.out.println("Oggetto caricato in S3 con successo!");

            // Elimina il file temporaneo
            Files.delete(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that downloads the string from the S3 bucket
     * 
     * @return the string that contains the serialized game object
     */
    private String downLoad() { //downloads the string from the bucket
        S3Client s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
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
            System.out.println("Oggetto scaricato da S3 con successo!");

            // Leggi il contenuto del file
            String content = new String(Files.readAllBytes(tempFile));
            System.out.println("Contenuto del file: " + content);

            // Elimina il file temporaneo
            Files.delete(tempFile);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        if (move.equals("save"))
            saveProgress(g);
        if (move.equals("load"))
            g = resumeProgress();
        return g.nextMove(move);
    }
}