module neiht.homeassistanthub {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;

    opens neiht.homeassistanthub to javafx.fxml;
    exports neiht.homeassistanthub;
}