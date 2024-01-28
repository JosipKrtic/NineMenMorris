//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("Fxml/StartScreen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Nine Men Morris");
            primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("Images/play.png")));
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
