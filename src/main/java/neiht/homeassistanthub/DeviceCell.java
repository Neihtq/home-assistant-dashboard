package neiht.homeassistanthub;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;

public class DeviceCell extends HBox {
    private final String entityId;
    private final String name;
    private final String type;
    private final HelloController controller;
    private boolean isOn;
    private ToggleSwitch toggleSwitch;

    public DeviceCell(String entityId, String name, Boolean isOn, String type, HelloController controller) {
        this.entityId = entityId;
        this.name = name;
        this.isOn = isOn;
        this.controller = controller;
        this.type = type;

        initGraphics();
        registerListeners();
    }

    private void initGraphics() {
        toggleSwitch = new ToggleSwitch(name);
        toggleSwitch.setSelected(isOn);
        toggleSwitch.setFocusTraversable(false);

        setSpacing(0);
        setFillHeight(false);
        setAlignment(Pos.TOP_LEFT);

        getChildren().addAll(toggleSwitch);
    }

    private void registerListeners() {
        toggleSwitch.setOnMouseClicked(e -> changeState());
    }

    public void changeState() {
        String newState = isOn ? "off" : "on";
        String response = "";
        String body = "{\"entity_id\":\"" + entityId + "\"}";
        try {
            response = controller.httpRequest("api/services/" + type + "/turn_" + newState, body);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

        if (!response.equals("[]")) {
            System.err.println("Change state of " + entityId + " FAILED. Log: ");
            System.err.println(response);
            return;
        }

        this.isOn = !isOn;
        toggleSwitch.setSelected(isOn);
    }
}
