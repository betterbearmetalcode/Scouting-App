package org.tahomaroboics.scoutingapp.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController extends VBox {


    public MenuItem enterFormEditButton;

    public Label testLabel;


    @FXML
    Text text = new Text("testing 123");


   @FXML
    protected void enterFormEditMode(ActionEvent event) {
       Stage mainStage = (Stage) testLabel.getScene().getWindow();

        mainStage.setScene(ScoutingServer.formEditScene);
   }


}