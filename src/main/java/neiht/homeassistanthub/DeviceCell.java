package neiht.homeassistanthub;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;

public class DeviceCell extends HBox {
    private final String entityId;
    private final String name;
    private boolean isOn;
    private ToggleSwitch toggleSwitch;

    public DeviceCell(String entityId, String name, Boolean isOn) {
        this.entityId = entityId;
        this.name = name;
        this.isOn = isOn;

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
        toggleSwitch.setOnMouseClicked(e -> handleDevicePropertyChanged());
    }

    private void handleDevicePropertyChanged() {
        this.isOn = !isOn;
        toggleSwitch.setSelected(isOn);

        String log = String.format("%s .%s is on: %b", name, entityId, isOn);
        System.out.println(log);
    }
}
