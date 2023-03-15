package main.system.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenAIHelper {
    public static final String API_KEY = "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";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";


    public OpenAIHelper() {

    }

    public static CompletableFuture<String> getAIResponseAsync(String inputstr, String API_KEY) {
        return CompletableFuture.supplyAsync(() -> getAIResponse(inputstr, API_KEY), executorService);
    }

    public static String getAIResponse(String inputstr, String API_KEY) {
        String prompt = inputstr.trim() + "Respond like Nietzsche";
        HttpURLConnection connection;
        StringBuilder response;
        try {
            URL url = new URL(OPENAI_API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);
            String requestBody = String.format("{\"model\": \"text-davinci-003\", \"prompt\": \"%s\", \"max_tokens\": %d}", prompt, 70);
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

    public static String extractGeneratedText(String jsonResponse) {
        Pattern pattern = Pattern.compile("\"text\":\\s*\"(.*?)\",");
        Matcher matcher = pattern.matcher(jsonResponse);
        if (matcher.find()) {
            String text = matcher.group(1);
            text = text.replace("\\n", ""); // Remove newline escape sequences
            text = text.replace("\\r", ""); // Remove carriage return escape sequences
            text = text.replace("\\t", ""); // Remove tab escape sequences
            return text;
        } else {
            throw new IllegalArgumentException("Invalid JSON response or unable to find the generated text.");
        }
    }


    public static void main(String[] args) {
        System.out.println(getAIResponse("hello", API_KEY));
    }
}
