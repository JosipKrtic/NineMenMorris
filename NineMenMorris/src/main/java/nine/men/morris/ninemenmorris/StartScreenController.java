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

public class StartScreenController {
    @FXML
    private Button PlayButton;
    @FXML
    private Button AboutButton;
    @FXML
    private Button ExitButton;
    @FXML
    private Button BackButton;
    @FXML
    private Button playerVsPlayer;
    @FXML
    private Button playerVsAI;
    @FXML
    private Button AIvsAI;

    public StartScreenController() {
    }

    @FXML
    public void startGame(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Fxml/GameModeScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.PlayButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void aboutGame(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Fxml/AboutScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.AboutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exitGame(ActionEvent event) {
        Stage stage = (Stage)this.ExitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Fxml/StartScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.BackButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGamePlayerVsPlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/MlinScreen.fxml"));
        Parent root = (Parent)loader.load();
        MlinScreenController mlinScreenController = (MlinScreenController)loader.getController();
        mlinScreenController.setGameMode("playerVsPlayer");
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.playerVsPlayer.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGamePlayerVsAI(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/ChooseFirstTurnScreen.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.playerVsAI.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGameAIvsAI(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/MlinScreen.fxml"));
        Parent root = (Parent)loader.load();
        MlinScreenController mlinScreenController = (MlinScreenController)loader.getController();
        mlinScreenController.setGameMode("AIvsAI");
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.playerVsPlayer.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
