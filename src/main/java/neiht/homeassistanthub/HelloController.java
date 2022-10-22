package neiht.homeassistanthub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import com.google.gson.Gson;

public class HelloController {

    private static final String BASE_URI = "http://192.168.178.69:8123/";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhOWE1ZDY5M2NjNTA0MjcxODM0YjcxMDQxM2E2NTc4NSIsImlhdCI6MTY2NjEyNTQxMSwiZXhwIjoxOTgxNDg1NDExfQ.xy3xy_ti4TSJgtahbFeRRkhJnsBVuVwH7pnROfPSSsE";
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private static HttpRequest getRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URI + endpoint))
                .setHeader("Authorization", "Bearer " + TOKEN)
                .setHeader("Accept", "application/json")
                .build();
    }

    private static HttpRequest postRequest(String endpoint, BodyPublisher body) {
        return HttpRequest.newBuilder()
                .POST(body)
                .uri(URI.create(BASE_URI + endpoint))
                .setHeader("Authorization", "Bearer " + TOKEN)
                .setHeader("Content-Type", "application/json")
                .build();
    }

    public String httpRequest(String endpoint, String body) throws IOException, InterruptedException {
        BodyPublisher requestBody = HttpRequest.BodyPublishers.ofString(body);
        HttpRequest request = postRequest(endpoint, requestBody);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public Map<String, Object>[] httpRequest(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = getRequest(endpoint);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parseResponse(response);
    }

    public Map<String, Object>[] parseResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        Gson gsonObject = new Gson();

        return gsonObject.fromJson(responseBody, Map[].class);
    }
}