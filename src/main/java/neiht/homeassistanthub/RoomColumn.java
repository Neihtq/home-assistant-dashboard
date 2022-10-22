package neiht.homeassistanthub;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class RoomColumn extends HBox{
    public RoomColumn(ObservableList<DeviceCell> deviceCells) {
        initGraphics(deviceCells);
    }

    private void initGraphics(ObservableList<DeviceCell> deviceCells) {
        setSpacing(10);
        setFillHeight(true);
        setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(deviceCells);
    }
}
