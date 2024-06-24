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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GameManager {
    private static final String BUCKET_NAME = "znnsproject"; //name of the bucket
    private static final String KEY_NAME = "game-state.json"; //name of the file to save the data in
    private final Region REGION = Region.US_EAST_1; // regione del Bucket

    public void saveProgress(Game game) { //fuction to be used for saving status
        String message = serialize(game);
        upLoad(message);
    }

    public Game resumeProgress() { //function to be used to resume status
        String message = downLoad();
        Game game = deserialize(message);
        return game;
    }

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

    private String downLoad() { //downloads the string from the bucket
        S3Client s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
            // Scarica il file dal bucket S3
            Path tempFile = Files.createTempFile("temp", ".json");
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
}