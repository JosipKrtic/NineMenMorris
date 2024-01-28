//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GameOverScreenController {
    @FXML
    private Text winnerText;
    @FXML
    private Circle winnerColor;

    public GameOverScreenController() {
    }

    void setWinner(String winner, String color) {
        this.winnerText.setText(winner);
        this.winnerColor.setFill(Paint.valueOf(color));
    }
}
