package main.java.org.zssn.escaperoom;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class SaveGameState {
    private static final String BUCKET_NAME = "zssnproject";  // nome del bucket
    private static final String KEY_NAME = "game-state.json"; // il nome del file per salvare lo stato

    public static void saveGameState(String gameState) {
        S3Client s3 = S3Utility.getS3Client();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(KEY_NAME)
                .build();

        s3.putObject(putObjectRequest, RequestBody.fromString(gameState));
        System.out.println("Game state saved to S3.");
    }

    /*Metodo di esempio per testare il salvataggio
    public static void main(String[] args) {
        String gameState = "{\"player\":\"JohnDoe\",\"score\":1000,\"level\":5}";
        saveGameState(gameState);
    }*/
}