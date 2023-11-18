package org.tahomaroboics.scoutingapp.server.formeditor;

import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.tahomaroboics.scoutingapp.server.ScoutingServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FormEditorController extends VBox {

    public FormEditorController() throws FileNotFoundException {
    }

    private enum SelectedWidgetType {
        NONE,
        LABEL,
        BUTTON,
        CHECKBOX,
        TEXT_FIELD,
        RADIO_BUTTON,
        DROP_DOWN
    }
    public final int IMAGE_SIZE = 32;
    public SelectedWidgetType selectedWidget = SelectedWidgetType.NONE;
    public Button returnButton;

    public ImageView cursorImageView;

    public Button text;

    public Button button;

    public Button checkbox;

    public Button textfield;

    public Button radiobutton;

    public Button dropdown;
    Image image = new Image(new FileInputStream("C:\\Users\\Caleb\\IdeaProjects\\Scouting-App\\Server\\src\\main\\resources\\org\\tahomaroboics\\scoutingapp\\server\\formeditor\\widgets\\textFieldCursor.png"));
    Image blankImage = new Image(new FileInputStream("C:\\Users\\Caleb\\IdeaProjects\\Scouting-App\\Server\\src\\main\\resources\\org\\tahomaroboics\\scoutingapp\\server\\formeditor\\widgets\\none.png"));

    public void returnToMainMenu(ActionEvent event) {
        Stage mainStage = (Stage) returnButton.getScene().getWindow();

        mainStage.setScene(ScoutingServer.mainScene);
    }

    public void  addLabelWidget(ActionEvent event) {
        selectedWidget = SelectedWidgetType.LABEL;

    }

    public void mouseDragEntered(MouseEvent event) {
        System.out.println("drag detected");
        cursorImageView.setImage(image);
        cursorImageView.setFitHeight(IMAGE_SIZE);
        cursorImageView.setFitWidth(IMAGE_SIZE);
        cursorImageView.setX(event.getSceneX() - 200);
        cursorImageView.setY(event.getY());
    }

    public void mouseReleased(MouseEvent event) {
        cursorImageView.setImage(blankImage);
        System.out.println("Mouse Released");
    }

}
