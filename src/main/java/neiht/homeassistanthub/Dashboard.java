package neiht.homeassistanthub;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Dashboard extends Application {
    private static final int COLSPAN = 1;
    private static final int ROWSPAN = 1;
    private static final int NUMCOLS = 3;
    private static final int PADDING = 5;
    private static final DashboardController controller = new DashboardController();
    private static final List<String> STATES = Arrays.asList("on", "off");
    private static final List<String> VALID = Arrays.asList("switch", "light");

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));

        ArrayList<Map<String, Object>> devices = scanDevices();
        ObservableList<DeviceCell> deviceCells = buildDeviceCell(devices);

        initDeviceList(deviceCells, root);
        initScanBtn(root);

        Scene scene = new Scene(root, 800, 480);
        stage.setScene(scene);
        stage.setTitle("Home Assistant Hub");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void initScanBtn(VBox root) {
        Button scanBtn = new Button("Scan for new devices");
        scanBtn.setOnAction(e -> System.out.println("Clicked Scan Button!"));
        scanBtn.setFocusTraversable(false);
        VBox.setVgrow(scanBtn, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(scanBtn);
    }

    public void getDevices(Map<String, Object>[] response, ArrayList<Map<String, Object>> devices) {
        for (Map<String, Object> obj : response) {
            String state = (String) obj.get("state");
            if (STATES.contains(state)) {
                devices.add(obj);
            }
        }
    }

    public ObservableList<DeviceCell> buildDeviceCell(List<Map<String, Object>> devices) {
        ObservableList<DeviceCell> deviceCells = FXCollections.observableArrayList();
        for (Map<String, Object> device : devices) {
            boolean isOn = device.get("state").equals("on");
            String entityId = (String) device.get("entity_id");

            String[] splitString = entityId.split("\\.");
            String type = splitString[0];
            if (!VALID.contains(type)) {
                continue;
            }

            Map<String, Object> attributes = (Map<String, Object>) device.get("attributes");
            String name = (String) attributes.get("friendly_name");
            DeviceCell deviceCell = new DeviceCell(entityId, name, isOn, type, controller);
            HBox.setHgrow(deviceCell, Priority.ALWAYS);

            GridPane.setFillWidth(deviceCell, true);
            GridPane.setFillHeight(deviceCell, true);
            deviceCells.add(deviceCell);
        }

        return deviceCells;
    }

    public ArrayList<Map<String, Object>> scanDevices() {
        ArrayList<Map<String, Object>> devices = new ArrayList<>();
        try {
            Map<String, Object>[] response = controller.httpRequest("api/states");
            getDevices(response, devices);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

        return devices;
    }

    public void initDeviceList(ObservableList<DeviceCell> deviceCells, VBox root) {
        GridPane deviceGrid = new GridPane();
        deviceGrid.setHgap(PADDING);
        deviceGrid.setVgap(PADDING);
        deviceGrid.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        int row = 0;
        for (int i = 0; i < deviceCells.size(); i++) {
            DeviceCell deviceCell = deviceCells.get(i);
            int col = i % NUMCOLS;
            if (col == 0) {
                row++;
            }

            deviceGrid.add(deviceCell, col, row, COLSPAN, ROWSPAN);
        }

        VBox.setVgrow(deviceGrid, Priority.ALWAYS);
        root.getChildren().add(deviceGrid);
    }

    public static void main(String[] args) {
        launch();
    }
}