package neiht.homeassistanthub; import javafx.application.Application; import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));

        HBox devicesList = initGraphics();
        VBox.setVgrow(devicesList, Priority.ALWAYS);
        root.getChildren().add(devicesList);

        Button scanBtn = new Button("Scan for new devices");
        scanBtn.setOnAction(e -> System.out.println("Clicked Scan Button!"));
        scanBtn.setFocusTraversable(false);
        VBox.setVgrow(scanBtn, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(scanBtn);

        Scene scene = new Scene(root, 800, 480);
        stage.setScene(scene);
        stage.setTitle("Home Assistant Hub");
        stage.setScene(scene);
        stage.show();
    }

    public HBox initGraphics() {
        HBox root = new HBox();
        root.setFillHeight(true);
        ArrayList<RoomColumn> roomColumns = createColumns();

        for (RoomColumn roomColumn : roomColumns) {
            HBox.setHgrow(roomColumn, Priority.ALWAYS);
            root.getChildren().add(roomColumn);
        }

        return root;
    }


    public ArrayList<RoomColumn> createColumns() {
        ArrayList<RoomColumn> roomColumns = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String idx = Integer.toString(i);
            ObservableList<DeviceCell> deviceCells = scanDevices(idx);
            RoomColumn room = new RoomColumn("room" + idx, deviceCells);
            roomColumns.add(room);
        }

        return roomColumns;
    }

    public ObservableList<DeviceCell> scanDevices(String prefix){
        ObservableList<DeviceCell> deviceCells = FXCollections.observableArrayList();

        boolean isOn = false;
        for (int i = 0; i < 5; i++) {
            DeviceCell device = new DeviceCell(prefix + i, "device" + i, isOn);
            deviceCells.add(device);
            isOn = !isOn;
        }

        return deviceCells;
    }

    public static void main(String[] args) {
        launch();
    }
}