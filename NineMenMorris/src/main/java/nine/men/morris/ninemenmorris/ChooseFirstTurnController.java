//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseFirstTurnController {
    @FXML
    private Button player;
    @FXML
    private Button AI;
    @FXML
    private Button BackButton;

    public ChooseFirstTurnController() {
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Fxml/GameModeScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.BackButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGamePlayerVsAI(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/MlinScreen.fxml"));
        Parent root = (Parent)loader.load();
        MlinScreenController mlinScreenController = (MlinScreenController)loader.getController();
        mlinScreenController.setWhoPlaysFirst("player");
        mlinScreenController.setGameMode("playerVsAI");
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.player.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGameAIvsPlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/MlinScreen.fxml"));
        Parent root = (Parent)loader.load();
        MlinScreenController mlinScreenController = (MlinScreenController)loader.getController();
        mlinScreenController.setWhoPlaysFirst("AI");
        mlinScreenController.setGameMode("playerVsAI");
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.AI.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
