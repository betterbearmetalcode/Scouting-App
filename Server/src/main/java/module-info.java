module com.example.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires jxl;


    opens org.tahomaroboics.scoutingapp.server to javafx.fxml;
    exports org.tahomaroboics.scoutingapp.server;
    exports org.tahomaroboics.scoutingapp.server.formeditor;
    opens org.tahomaroboics.scoutingapp.server.formeditor to javafx.fxml;
}