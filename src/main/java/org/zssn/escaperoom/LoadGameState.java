package main.java.org.zssn.escaperoom;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class LoadGameState {
    private static final String BUCKET_NAME = "znnsproject";
    private static final String KEY_NAME = "game-state.json";

    public static String loadGameState() {
        S3Client s3 = S3Utility.getS3Client();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(KEY_NAME)
                .build();

        String gameState = s3.getObjectAsBytes(getObjectRequest).asUtf8String();
        System.out.println("Game state loaded from S3.");
        return gameState;
    }

    /* Metodo di esempio per testare il caricamento
    public static void main(String[] args) {
        String gameState = loadGameState();
        System.out.println("Loaded game state: " + gameState);
    }
    */
}