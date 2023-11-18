package org.tahomaroboics.scoutingapp.server;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class ScoutingServer extends Application {


    public static Scene formEditScene;
    public static  Scene mainScene;

    //private Parent root = InjectionManager.load(ScoutingServer.class.getResource("main-scene.fxml"));



    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Scouting Server");
        stage.setScene(mainScene);
        stage.show();


    }


    @Override
    public void init() throws Exception {
        FXMLLoader formLoader = new FXMLLoader(ScoutingServer.class.getResource("form-editor-scene.fxml"));

        formEditScene = new Scene(formLoader.load());

        FXMLLoader mainLoader = new FXMLLoader(ScoutingServer.class.getResource("main-scene.fxml"));
        mainScene = new Scene(mainLoader.load());




    }


    public static void main(String[] args) {
        launch();
    }
}