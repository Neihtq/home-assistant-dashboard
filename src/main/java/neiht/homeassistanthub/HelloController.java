package neiht.homeassistanthub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HelloController {

    private static final String BASE_URI = "http://192.168.178.69:8123/";
    private static final String TOKEN = "";
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
                .uri(URI.create(BASE_URI))
                .setHeader("Authorization", "Bearer " + TOKEN)
                .build();
    }

    public HttpResponse<String> httpRequest(String type, String endpoint) throws IOException, InterruptedException {
        HttpRequest request;
        switch (type) {
            case "GET":
                request = getRequest(endpoint);
                break;
            default:
                request = getRequest(endpoint);
                break;
        }

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}