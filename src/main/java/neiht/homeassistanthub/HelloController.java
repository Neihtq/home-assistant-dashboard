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

    private static final String BASE_URI = "192.168.178.69:8123";
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

    private static HttpRequest getRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URI))
                .setHeader("User-Agent", "Java 11 HttplClient Bot")
                .build();
    }

    private static void httpRequest(String type) throws IOException, InterruptedException {
        HttpRequest request;
        switch (type) {
            case "GET":
                request = getRequest();
                break;
            default:
                request = getRequest();
                break;
        }

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ':' + v));

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

}