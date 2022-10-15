package neiht.homeassistanthub;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class RoomColumn extends VBox{
    private final String name;
    private final ObservableList<DeviceCell> deviceCells;

    public RoomColumn(String name, ObservableList<DeviceCell> deviceCells) {
        this.name = name;
        this.deviceCells = deviceCells;

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
