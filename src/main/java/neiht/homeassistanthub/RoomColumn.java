package neiht.homeassistanthub;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

public class RoomColumn extends VBox{
    private final String name;
    private final ObservableList<DeviceCell> deviceCells;

    public RoomColumn(String name, ObservableList<DeviceCell> deviceCells) {
        this.name = name;
        this.deviceCells = deviceCells;

        HelloController controller = new HelloController();
        try {
            String endpoint = "api/";
            HttpResponse<String> response = controller.httpRequest("GET", endpoint);
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ':' + v));

            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        initGraphics();
    }

    private void initGraphics() {
        Label textLabel = new Label(name);
        textLabel.setFont(new Font("Arial", 24));
        textLabel.setFocusTraversable(false);

        setSpacing(10);
        setFillWidth(true);
        setAlignment(Pos.TOP_LEFT);
        getChildren().add(textLabel);
        getChildren().addAll(deviceCells);
    }
}
