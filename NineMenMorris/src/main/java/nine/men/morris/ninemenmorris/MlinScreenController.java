//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris;

import nine.men.morris.ninemenmorris.Models.Board;
import nine.men.morris.ninemenmorris.Models.MinimaxLogic;
import nine.men.morris.ninemenmorris.Models.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MlinScreenController implements Initializable {
    @FXML
    private Button a1;
    @FXML
    private Button a4;
    @FXML
    private Button a7;
    @FXML
    private Button b2;
    @FXML
    private Button b4;
    @FXML
    private Button b6;
    @FXML
    private Button c3;
    @FXML
    private Button c4;
    @FXML
    private Button c5;
    @FXML
    private Button d1;
    @FXML
    private Button d2;
    @FXML
    private Button d3;
    @FXML
    private Button d5;
    @FXML
    private Button d6;
    @FXML
    private Button d7;
    @FXML
    private Button e3;
    @FXML
    private Button e4;
    @FXML
    private Button e5;
    @FXML
    private Button f2;
    @FXML
    private Button f4;
    @FXML
    private Button f6;
    @FXML
    private Button g1;
    @FXML
    private Button g4;
    @FXML
    private Button g7;
    @FXML
    private Button backButton;
    @FXML
    private Button playAIMoveButton;
    @FXML
    private Text phaseText;
    @FXML
    private Text phaseDescription;
    @FXML
    private Text player1ActiveSpots;
    @FXML
    private Text player2ActiveSpots;
    @FXML
    private Circle playerTurn;
    Player player1;
    Player player2;
    Board board;
    MinimaxLogic minimax;
    private String styleEnabled;
    private String tmpId;
    private String firstMove;
    private String player1Style;
    private String player2Style;
    private List<String> neighbourSpots = new ArrayList();
    private List<String> emptyNeighbourSpots = new ArrayList();
    private List<String> emptySpots = new ArrayList();
    private Timer timer;

    public MlinScreenController() {
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.board = new Board();
        this.minimax = new MinimaxLogic();
        this.player1ActiveSpots.setText("0");
        this.player2ActiveSpots.setText("0");
        this.setTitleAndDescription(this.board.getPlacingPhaseTitle(), this.board.getPlacingPhaseDescription());
        this.player1Style = "-fx-background-color: white;-fx-border-width: 2px;-fx-border-color: white;-fx-scale-x: 1.2;-fx-scale-y: 1.2;-fx-opacity: 1.0;";
        this.player2Style = "-fx-background-color: black;-fx-border-width: 2px;-fx-border-color: black;-fx-scale-x: 1.2;-fx-scale-y: 1.2;-fx-opacity: 1.0;";
        this.styleEnabled = "-fx-background-color: #fad47f;-fx-background-radius: 50%;-fx-border-style: solid;-fx-border-width: 5px;-fx-border-radius: 50;-fx-border-color: black;";
        this.timer = new Timer();
        this.playAIMoveButton.setDisable(true);
    }

    public void setWhoPlaysFirst(String player) {
        if (player.equals("player")) {
            this.firstMove = "player";
        } else if (player.equals("AI")) {
            this.firstMove = "AI";
        }

    }

    public void setGameMode(String gameMode) throws IOException {
        if (gameMode.equals("playerVsPlayer")) {
            this.player1 = new Player("player1", "white", this.player1Style, true, false);
            this.player2 = new Player("player2", "black", this.player2Style, false, false);
        } else if (gameMode.equals("playerVsAI")) {
            if (this.firstMove.equals("player")) {
                this.player1 = new Player("player1", "white", this.player1Style, true, false);
                this.player2 = new Player("player2", "black", this.player2Style, false, true);
            } else if (this.firstMove.equals("AI")) {
                this.player1 = new Player("player1", "white", this.player1Style, true, true);
                this.player2 = new Player("player2", "black", this.player2Style, false, false);
                this.doPlacingPhaseForAI(this.player1, this.player2);
            }
        } else if (gameMode.equals("AIvsAI")) {
            this.player1 = new Player("player1", "white", this.player1Style, true, true);
            this.player2 = new Player("player2", "black", this.player2Style, false, true);
            this.playAIMoveButton.setDisable(false);
            this.enableDisableSpots(this.board.getAllBoardSpots(), true);
        }

    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("Fxml/GameModeScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)this.backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playAIMove(ActionEvent event) throws IOException {
        if (this.player2.getNumOfPiecesPlaced() < 9) {
            if (this.player1.isActivePlayer()) {
                this.doPlacingPhaseForAI(this.player1, this.player2);
            } else {
                this.doPlacingPhaseForAI(this.player2, this.player1);
            }
        } else if (this.player2.getNumOfPiecesPlaced() == 9) {
            if (this.player1.isActivePlayer()) {
                this.doMovingPhaseForAI(this.player1, this.player2);
            } else {
                this.doMovingPhaseForAI(this.player2, this.player1);
            }
        }

    }

    @FXML
    public void placePiece(ActionEvent event) throws IOException {
        String id = ((Control)event.getSource()).getId();
        if (this.board.isPlacingPhase() && !this.board.isMovingPhase()) {
            if (this.player2.getNumOfPiecesPlaced() < 9) {
                if (this.player1.isActivePlayer()) {
                    this.player1.setNewOccupiedSpot(this.setPiece(id, this.player1.getStyle()));
                    System.out.println("Player1 just placed a piece on " + id);
                    this.player1.increaseNumOfPiecesPlacedBy1();
                    this.player1.increaseNumOfActivePiecesBy1();
                    this.player1ActiveSpots.setText(String.valueOf(this.player1.getNumOfActivePieces()));
                } else if (this.player2.isActivePlayer()) {
                    this.player2.setNewOccupiedSpot(this.setPiece(id, this.player2.getStyle()));
                    System.out.println("Player2 just placed a piece on " + id);
                    this.player2.increaseNumOfPiecesPlacedBy1();
                    this.player2.increaseNumOfActivePiecesBy1();
                    this.player2ActiveSpots.setText(String.valueOf(this.player2.getNumOfActivePieces()));
                }
            }

            if (this.player1.isActivePlayer()) {
                if (this.board.checkThreeInARow(this.player1, false) && this.validPiecesToRemove(this.player2)) {
                    this.setupRemovingPhase();
                } else {
                    this.switchActivePlayer();
                }
            } else if (this.player2.isActivePlayer()) {
                if (this.board.checkThreeInARow(this.player2, false) && this.validPiecesToRemove(this.player1)) {
                    this.setupRemovingPhase();
                } else {
                    this.switchActivePlayer();
                }
            }

            if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9 && !this.board.isRemovingPhase() && this.player1.isAI()) {
                this.switchActivePlayer();
                this.doMovingPhaseForAI(this.player1, this.player2);
            } else if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9 && !this.board.isRemovingPhase() && this.player2.isAI()) {
                this.doMovingPhaseForAI(this.player2, this.player1);
            } else if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9 && !this.board.isRemovingPhase()) {
                if (!this.checkIfGameOver()) {
                    this.setupMovingPhase();
                }
            } else if (this.player1.isActivePlayer() && this.player1.isAI()) {
                this.doPlacingPhaseForAI(this.player1, this.player2);
            } else if (this.player2.isActivePlayer() && this.player2.isAI()) {
                this.doPlacingPhaseForAI(this.player2, this.player1);
            }
        } else if (this.board.isRemovingPhase()) {
            if (this.player1.isActivePlayer()) {
                if (this.player2.getOccupiedSpots().contains(id)) {
                    if (!this.player2.isFlying()) {
                        if (this.piecesOutOfMill(this.player2).contains(id)) {
                            this.doRemovingPhase(id, this.player2);
                            this.player2ActiveSpots.setText(String.valueOf(this.player2.getNumOfActivePieces()));
                            if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9) {
                                this.setupNextPhase(this.player1, this.player2);
                            } else if (this.player2.isAI()) {
                                this.doPlacingPhaseForAI(this.player2, this.player1);
                            }
                        } else {
                            this.phaseDescription.setText(this.board.getRemovingPhaseError());
                        }
                    } else {
                        this.doRemovingPhase(id, this.player2);
                        this.player2ActiveSpots.setText(String.valueOf(this.player2.getNumOfActivePieces()));
                        if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9) {
                            this.setupNextPhase(this.player1, this.player2);
                        } else if (this.player2.isAI()) {
                            this.doPlacingPhaseForAI(this.player2, this.player1);
                        }
                    }
                }
            } else if (this.player2.isActivePlayer() && this.player1.getOccupiedSpots().contains(id)) {
                if (!this.player1.isFlying()) {
                    if (this.piecesOutOfMill(this.player1).contains(id)) {
                        this.doRemovingPhase(id, this.player1);
                        this.player1ActiveSpots.setText(String.valueOf(this.player1.getNumOfActivePieces()));
                        if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9) {
                            this.setupNextPhase(this.player2, this.player1);
                        } else if (this.player1.isAI()) {
                            this.doPlacingPhaseForAI(this.player1, this.player2);
                        }
                    } else {
                        this.phaseDescription.setText(this.board.getRemovingPhaseError());
                    }
                } else {
                    this.doRemovingPhase(id, this.player1);
                    this.player1ActiveSpots.setText(String.valueOf(this.player1.getNumOfActivePieces()));
                    if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9) {
                        this.setupNextPhase(this.player2, this.player1);
                    } else if (this.player1.isAI()) {
                        this.doPlacingPhaseForAI(this.player1, this.player2);
                    }
                }
            }
        } else if (this.board.isMovingPhase()) {
            if (this.player1.isActivePlayer()) {
                this.doMovingPhase(id, this.player1, this.player2);
            } else if (this.player2.isActivePlayer()) {
                this.doMovingPhase(id, this.player2, this.player1);
            }

            this.checkIfGameOver();
        } else if (this.board.isFlyingPhase()) {
            if (this.player1.isActivePlayer()) {
                this.doFlyingPhase(id, this.player1, this.player2);
            } else if (this.player2.isActivePlayer()) {
                this.doFlyingPhase(id, this.player2, this.player1);
            }

            this.checkIfGameOver();
        }

    }

    private void doPlacingPhaseForAI(Player player, Player opponent) throws IOException {
        String id = this.minimax.makeBestMoveForPlacingPhase(player, opponent);
        player.increaseNumOfPiecesPlacedBy1();
        player.increaseNumOfActivePiecesBy1();
        player.setNewOccupiedSpot(this.setPiece(id, player.getStyle()));
        System.out.println(player.getName() + " just placed a piece on " + id);
        this.player1ActiveSpots.setText(String.valueOf(this.player1.getNumOfActivePieces()));
        this.player2ActiveSpots.setText(String.valueOf(this.player2.getNumOfActivePieces()));
        if (this.board.checkThreeInARow(player, false) && this.validPiecesToRemove(opponent)) {
            this.doRemovingPhaseForAI(player, opponent);
            this.disableAllSpots();
            this.enableDisableSpots(this.board.getEmptySpots(player, opponent), false);
            this.switchActivePlayer();
            this.setTitleAndDescription(this.board.getPlacingPhaseTitle(), this.board.getPlacingPhaseDescription());
        } else {
            this.switchActivePlayer();
        }

        if (this.player1.getNumOfPiecesPlaced() == 9 && this.player2.getNumOfPiecesPlaced() == 9 && !this.board.isRemovingPhase() && !this.checkIfGameOver()) {
            this.setupMovingPhase();
        }

    }

    private void doRemovingPhaseForAI(Player player, Player opponent) {
        String id = this.minimax.makeBestMoveForRemoving(player, opponent);
        opponent.decreaseNumOfActivePiecesBy1();
        opponent.removeOccupiedSpot(this.removePiece(id));
        if (opponent.getNumOfPiecesPlaced() == 9) {
            List<String> singleIdList = new ArrayList();
            singleIdList.add(id);
            this.enableDisableSpots(singleIdList, true);
        }

        System.out.println(player.getName() + " just removed " + id + " from opponent");
        this.player1ActiveSpots.setText(String.valueOf(this.player1.getNumOfActivePieces()));
        this.player2ActiveSpots.setText(String.valueOf(this.player2.getNumOfActivePieces()));
        this.board.checkIfThreeInARowWasBroken(id, player);
        this.board.setRemovingPhase(false);
        this.board.setPlacingPhase(true);
    }

    private void removeAIPieceWithDelay(final Player opponent, final String id) {
        opponent.decreaseNumOfActivePiecesBy1();
        this.timer.schedule(new TimerTask() {
            public void run() {
                opponent.removeOccupiedSpot(MlinScreenController.this.removePiece(id));
                if (opponent.getNumOfPiecesPlaced() == 9) {
                    List<String> singleIdList = new ArrayList();
                    singleIdList.add(id);
                    MlinScreenController.this.enableDisableSpots(singleIdList, true);
                }

            }
        }, 500L);
    }

    private void doMovingPhaseForAI(Player player, Player opponent) throws IOException {
        List<String> spotAndMove = new ArrayList();
        String spot = "";
        String nextMove = "";
        spotAndMove.addAll(this.minimax.makeBestMoveForMovingPhase(player, opponent));
        spot = (String)spotAndMove.get(0);
        nextMove = (String)spotAndMove.get(1);
        player.removeOccupiedSpot(this.removePiece(spot));
        player.setNewOccupiedSpot(this.setPiece(nextMove, player.getStyle()));
        if (player.getNumOfPiecesPlaced() == 9) {
            List<String> singleIdList = new ArrayList();
            singleIdList.add(spot);
            this.enableDisableSpots(singleIdList, true);
        }

        System.out.println(player.getName() + " just move a piece from " + spot + " to " + nextMove);
        this.board.checkIfThreeInARowWasBroken(spot, player);
        if (this.board.checkThreeInARow(player, false)) {
            this.doRemovingPhaseForAI(player, opponent);
            if (player.isAI() && opponent.isAI()) {
                this.switchActivePlayer();
            }

            if (opponent.getNumOfActivePieces() == 3) {
                this.setupFlyingPhase(opponent);
            } else if (opponent.getNumOfActivePieces() == 2) {
                this.gameOver(player, this.board.getGameOverReason2());
                this.enableDisableSpots(this.board.getAllBoardSpots(), true);
            } else if (!this.checkIfGameOver()) {
                this.disableAllSpots();
                this.enableDisableSpots(opponent.getOccupiedSpots(), false);
                this.setTitleAndDescription(this.board.getMovingPhaseTitle(), this.board.getMovingPhaseDescription());
            }
        } else if (opponent.getNumOfActivePieces() == 3) {
            this.setupFlyingPhase(opponent);
        } else {
            if (player.isAI() && opponent.isAI()) {
                this.switchActivePlayer();
            }

            if (player.getName().equals("player1")) {
                this.setupMovingPhase();
            } else {
                this.checkIfGameOver();
                this.phaseDescription.setText(this.board.getMovingPhaseDescription());
                this.disableAllSpots();
                this.enableOnlyActivePlayerSpots();
            }
        }

    }

    private void doFlyingPhaseForAI(Player player, Player opponent) throws IOException {
        List<String> spotAndMove = new ArrayList();
        String spot = "";
        String nextMove = "";
        spotAndMove.addAll(this.minimax.makeBestMoveForFlyingPhase(player, opponent));
        spot = (String)spotAndMove.get(0);
        nextMove = (String)spotAndMove.get(1);
        player.removeOccupiedSpot(this.removePiece(spot));
        player.setNewOccupiedSpot(this.setPiece(nextMove, player.getStyle()));
        System.out.println(player.getName() + " just flew a piece from " + spot + " to " + nextMove);
        this.board.checkIfThreeInARowWasBroken(spot, player);
        if (this.board.checkThreeInARow(player, false)) {
            this.doRemovingPhaseForAI(player, opponent);
            if (opponent.getNumOfActivePieces() == 3) {
                this.setupFlyingPhase(opponent);
            } else if (opponent.getNumOfActivePieces() == 2) {
                this.gameOver(player, this.board.getGameOverReason2());
            } else if (!this.checkIfGameOver()) {
                this.disableAllSpots();
                this.setupMovingPhase();
            }
        } else if (opponent.getNumOfActivePieces() == 3) {
            this.setupFlyingPhase(opponent);
        } else {
            this.checkIfGameOver();
            this.setupMovingPhase();
        }

    }

    private boolean checkIfGameOver() throws IOException {
        if (!this.checkIfPlayerCanMove(this.player1)) {
            this.gameOver(this.player2, this.board.getGameOverReason1());
            return true;
        } else if (!this.checkIfPlayerCanMove(this.player2)) {
            this.gameOver(this.player1, this.board.getGameOverReason1());
            return true;
        } else {
            return false;
        }
    }

    private void setupRemovingPhase() {
        this.phaseDescription.setText(this.board.getRemovingPhaseDescription());
        this.board.setPlacingPhase(false);
        this.board.setRemovingPhase(true);
        this.enableOpponentsSpots();
    }

    private void setupNextPhase(Player activePlayer, Player opponent) throws IOException {
        if (!this.checkIfPlayerCanMove(opponent)) {
            this.gameOver(activePlayer, this.board.getGameOverReason1());
        } else if (opponent.getNumOfActivePieces() == 3 && opponent.isAI()) {
            this.switchActivePlayer();
            this.doFlyingPhaseForAI(opponent, activePlayer);
        } else if (opponent.getNumOfActivePieces() == 3) {
            this.setupFlyingPhase(opponent);
        } else if (opponent.getNumOfActivePieces() == 2) {
            this.gameOver(activePlayer, this.board.getGameOverReason2());
        } else if (opponent.isAI()) {
            this.setupMovingPhase();
            this.switchActivePlayer();
            this.doMovingPhaseForAI(opponent, activePlayer);
        } else {
            this.setupMovingPhase();
        }

    }

    private void setupMovingPhase() {
        this.setTitleAndDescription(this.board.getMovingPhaseTitle(), this.board.getMovingPhaseDescription());
        this.board.setPlacingPhase(false);
        this.board.setMovingPhase(true);
        this.board.setFlyingPhase(false);
        this.disableAllSpots();
        this.enableOnlyActivePlayerSpots();
    }

    private void setupFlyingPhase(Player opponent) {
        this.setTitleAndDescription(this.board.getFlyingPhaseTitle(), this.board.getFlyingPhaseDescription());
        opponent.setFlying(true);
        this.board.setPlacingPhase(false);
        this.board.setMovingPhase(false);
        this.board.setFlyingPhase(true);
        this.disableAllSpots();
        this.enableOnlyActivePlayerSpots();
    }

    private void doMovingPhase(String id, Player player, Player opponent) throws IOException {
        if (player.getOccupiedSpots().contains(id)) {
            this.disableAllSpots();
            this.enableOnlyActivePlayerSpots();
            this.neighbourSpots = this.board.getNeighbourSpots(id);
            this.emptyNeighbourSpots = this.getEmptyNeighbourSpots(this.neighbourSpots);
            if (this.emptyNeighbourSpots.isEmpty()) {
                this.phaseDescription.setText(this.board.getMovingPhaseError());
            } else {
                this.phaseDescription.setText(this.board.getMovingPhaseSuccess());
                this.enableDisableSpots(this.emptyNeighbourSpots, false);
                this.tmpId = id;
            }

            this.neighbourSpots.clear();
            this.emptyNeighbourSpots.clear();
        } else {
            player.setNewOccupiedSpot(this.setPiece(id, player.getStyle()));
            player.removeOccupiedSpot(this.removePiece(this.tmpId));
            System.out.println(player.getName() + " just moved a piece from " + this.tmpId + " to " + id);
            this.board.checkIfThreeInARowWasBroken(this.tmpId, player);
            if (this.board.checkThreeInARow(player, false)) {
                this.phaseDescription.setText(this.board.getRemovingPhaseDescription());
                this.board.setRemovingPhase(true);
                this.disableAllSpots();
                this.enableOpponentsSpots();
            } else if (opponent.getNumOfActivePieces() == 3 && opponent.isAI()) {
                this.doFlyingPhaseForAI(opponent, player);
            } else if (opponent.getNumOfActivePieces() == 3) {
                this.switchActivePlayer();
                this.setupFlyingPhase(opponent);
            } else if (opponent.isAI()) {
                this.doMovingPhaseForAI(opponent, player);
            } else {
                this.switchActivePlayer();
                this.phaseDescription.setText(this.board.getMovingPhaseDescription());
                this.disableAllSpots();
                this.enableOnlyActivePlayerSpots();
            }
        }

    }

    private void doFlyingPhase(String id, Player player, Player opponent) throws IOException {
        if (player.getOccupiedSpots().contains(id)) {
            this.disableAllSpots();
            this.enableOnlyActivePlayerSpots();
            this.emptySpots = this.board.getEmptySpots(player, opponent);
            this.phaseDescription.setText(this.board.getFlyingPhaseSuccess());
            this.enableDisableSpots(this.emptySpots, false);
            this.tmpId = id;
            this.emptySpots.clear();
        } else {
            player.setNewOccupiedSpot(this.setPiece(id, player.getStyle()));
            player.removeOccupiedSpot(this.removePiece(this.tmpId));
            System.out.println(player.getName() + " just flew a piece from " + this.tmpId + " to " + id);
            this.board.checkIfThreeInARowWasBroken(this.tmpId, player);
            if (this.board.checkThreeInARow(player, false)) {
                this.phaseDescription.setText(this.board.getRemovingPhaseDescription());
                this.board.setRemovingPhase(true);
                this.disableAllSpots();
                this.enableOpponentsSpots();
            } else if (opponent.getNumOfActivePieces() == 3 && opponent.isAI()) {
                this.doFlyingPhaseForAI(opponent, player);
            } else if (opponent.getNumOfActivePieces() == 3) {
                this.switchActivePlayer();
                this.setupFlyingPhase(opponent);
            } else if (opponent.isAI()) {
                this.doMovingPhaseForAI(opponent, player);
            } else {
                this.switchActivePlayer();
                this.setupMovingPhase();
            }
        }

    }

    private void doRemovingPhase(String id, Player player) {
        player.removeOccupiedSpot(this.removePiece(id));
        player.decreaseNumOfActivePiecesBy1();
        this.enableDisableSpots(player.getOccupiedSpots(), true);
        this.switchActivePlayer();
        this.board.checkIfThreeInARowWasBroken(id, player);
        this.board.setRemovingPhase(false);
        this.board.setPlacingPhase(true);
        this.setTitleAndDescription(this.board.getPlacingPhaseTitle(), this.board.getPlacingPhaseDescription());
    }

    private boolean checkIfPlayerCanMove(Player player) {
        List<String> avaliableSpots = new ArrayList();
        Iterator var4 = player.getOccupiedSpots().iterator();

        while(var4.hasNext()) {
            String spot = (String)var4.next();
            avaliableSpots.addAll(this.getEmptyNeighbourSpots(this.board.getNeighbourSpots(spot)));
        }

        return !avaliableSpots.isEmpty();
    }

    private void gameOver(Player winner, String description) throws IOException {
        this.disableAllSpots();
        this.setTitleAndDescription(winner.getName() + this.board.getGameOverTitle(), description);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Fxml/GameOverScreen.fxml"));
        Parent root = (Parent)loader.load();
        GameOverScreenController controller = (GameOverScreenController)loader.getController();
        controller.setWinner(winner.getName(), winner.getColor());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Mlin");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Images/play.png")));
        stage.show();
    }

    private void setTitleAndDescription(String title, String description) {
        this.phaseText.setText(title);
        this.phaseDescription.setText(description);
    }

    private void switchActivePlayer() {
        if (this.player1.isActivePlayer()) {
            this.player1.setActivePlayer(false);
            this.player2.setActivePlayer(true);
            this.playerTurn.setFill(Paint.valueOf(this.player2.getColor()));
        } else if (this.player2.isActivePlayer()) {
            this.player1.setActivePlayer(true);
            this.player2.setActivePlayer(false);
            this.playerTurn.setFill(Paint.valueOf(this.player1.getColor()));
        }

    }

    private List<String> getEmptyNeighbourSpots(List<String> spots) {
        List<String> neighbourSpots = new ArrayList();
        List<String> emptySpots = new ArrayList();
        emptySpots.addAll(this.board.getAllBoardSpots());
        emptySpots.removeAll(this.player1.getOccupiedSpots());
        emptySpots.removeAll(this.player2.getOccupiedSpots());
        Iterator var5 = spots.iterator();

        while(var5.hasNext()) {
            String spot = (String)var5.next();
            if (emptySpots.contains(spot)) {
                neighbourSpots.add(spot);
            }
        }

        return neighbourSpots;
    }

    private boolean validPiecesToRemove(Player opponent) {
        List<String> opponentsSpotsThatDontFormMill = new ArrayList();
        opponentsSpotsThatDontFormMill.addAll(opponent.getOccupiedSpots());
        Iterator var4 = opponent.getCombinations().iterator();

        while(var4.hasNext()) {
            String combination = (String)var4.next();
            String[] spots = combination.split("(?<=\\G.{2})");
            String[] var9 = spots;
            int var8 = spots.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String spot = var9[var7];
                opponentsSpotsThatDontFormMill.remove(spot);
            }
        }

        if (opponentsSpotsThatDontFormMill.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private List<String> piecesOutOfMill(Player opponent) {
        List<String> opponentsSpotsThatDontFormMill = new ArrayList();
        opponentsSpotsThatDontFormMill.addAll(opponent.getOccupiedSpots());
        Iterator var4 = opponent.getCombinations().iterator();

        while(var4.hasNext()) {
            String combination = (String)var4.next();
            String[] spots = combination.split("(?<=\\G.{2})");
            String[] var9 = spots;
            int var8 = spots.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String spot = var9[var7];
                opponentsSpotsThatDontFormMill.remove(spot);
            }
        }

        return opponentsSpotsThatDontFormMill;
    }

    private String setPiece(String id, String style) {
        return this.getCorrectPiece(id, style, true);
    }

    private String removePiece(String id) {
        return this.getCorrectPiece(id, this.styleEnabled, false);
    }

    private String getCorrectPiece(String id, String style, boolean disableState) {
        String piece = null;
        switch (id) {
            case "a1":
                this.a1.setDisable(disableState);
                this.a1.setStyle(style);
                piece = id;
                break;
            case "a4":
                this.a4.setDisable(disableState);
                this.a4.setStyle(style);
                piece = id;
                break;
            case "a7":
                this.a7.setDisable(disableState);
                this.a7.setStyle(style);
                piece = id;
                break;
            case "b2":
                this.b2.setDisable(disableState);
                this.b2.setStyle(style);
                piece = id;
                break;
            case "b4":
                this.b4.setDisable(disableState);
                this.b4.setStyle(style);
                piece = id;
                break;
            case "b6":
                this.b6.setDisable(disableState);
                this.b6.setStyle(style);
                piece = id;
                break;
            case "c3":
                this.c3.setDisable(disableState);
                this.c3.setStyle(style);
                piece = id;
                break;
            case "c4":
                this.c4.setDisable(disableState);
                this.c4.setStyle(style);
                piece = id;
                break;
            case "c5":
                this.c5.setDisable(disableState);
                this.c5.setStyle(style);
                piece = id;
                break;
            case "d1":
                this.d1.setDisable(disableState);
                this.d1.setStyle(style);
                piece = id;
                break;
            case "d2":
                this.d2.setDisable(disableState);
                this.d2.setStyle(style);
                piece = id;
                break;
            case "d3":
                this.d3.setDisable(disableState);
                this.d3.setStyle(style);
                piece = id;
                break;
            case "d5":
                this.d5.setDisable(disableState);
                this.d5.setStyle(style);
                piece = id;
                break;
            case "d6":
                this.d6.setDisable(disableState);
                this.d6.setStyle(style);
                piece = id;
                break;
            case "d7":
                this.d7.setDisable(disableState);
                this.d7.setStyle(style);
                piece = id;
                break;
            case "e3":
                this.e3.setDisable(disableState);
                this.e3.setStyle(style);
                piece = id;
                break;
            case "e4":
                this.e4.setDisable(disableState);
                this.e4.setStyle(style);
                piece = id;
                break;
            case "e5":
                this.e5.setDisable(disableState);
                this.e5.setStyle(style);
                piece = id;
                break;
            case "f2":
                this.f2.setDisable(disableState);
                this.f2.setStyle(style);
                piece = id;
                break;
            case "f4":
                this.f4.setDisable(disableState);
                this.f4.setStyle(style);
                piece = id;
                break;
            case "f6":
                this.f6.setDisable(disableState);
                this.f6.setStyle(style);
                piece = id;
                break;
            case "g1":
                this.g1.setDisable(disableState);
                this.g1.setStyle(style);
                piece = id;
                break;
            case "g4":
                this.g4.setDisable(disableState);
                this.g4.setStyle(style);
                piece = id;
                break;
            case "g7":
                this.g7.setDisable(disableState);
                this.g7.setStyle(style);
                piece = id;
        }

        return piece;
    }

    private void enableOpponentsSpots() {
        if (this.player1.isActivePlayer()) {
            this.enableDisableSpots(this.player2.getOccupiedSpots(), false);
        } else if (this.player2.isActivePlayer()) {
            this.enableDisableSpots(this.player1.getOccupiedSpots(), false);
        }

    }

    private void enableOnlyActivePlayerSpots() {
        if (this.player1.isActivePlayer()) {
            this.enableDisableSpots(this.player1.getOccupiedSpots(), false);
        } else if (this.player2.isActivePlayer()) {
            this.enableDisableSpots(this.player2.getOccupiedSpots(), false);
        }

    }

    private void disableAllSpots() {
        this.enableDisableSpots(this.board.getAllBoardSpots(), true);
    }

    private void enableDisableSpots(List<String> spots, boolean disableState) {
        for (String spot : spots) {
            switch (spot) {
                case "a1":
                    this.a1.setDisable(disableState);
                    break;
                case "a4":
                    this.a4.setDisable(disableState);
                    break;
                case "a7":
                    this.a7.setDisable(disableState);
                    break;
                case "b2":
                    this.b2.setDisable(disableState);
                    break;
                case "b4":
                    this.b4.setDisable(disableState);
                    break;
                case "b6":
                    this.b6.setDisable(disableState);
                    break;
                case "c3":
                    this.c3.setDisable(disableState);
                    break;
                case "c4":
                    this.c4.setDisable(disableState);
                    break;
                case "c5":
                    this.c5.setDisable(disableState);
                    break;
                case "d1":
                    this.d1.setDisable(disableState);
                    break;
                case "d2":
                    this.d2.setDisable(disableState);
                    break;
                case "d3":
                    this.d3.setDisable(disableState);
                    break;
                case "d5":
                    this.d5.setDisable(disableState);
                    break;
                case "d6":
                    this.d6.setDisable(disableState);
                    break;
                case "d7":
                    this.d7.setDisable(disableState);
                    break;
                case "e3":
                    this.e3.setDisable(disableState);
                    break;
                case "e4":
                    this.e4.setDisable(disableState);
                    break;
                case "e5":
                    this.e5.setDisable(disableState);
                    break;
                case "f2":
                    this.f2.setDisable(disableState);
                    break;
                case "f4":
                    this.f4.setDisable(disableState);
                    break;
                case "f6":
                    this.f6.setDisable(disableState);
                    break;
                case "g1":
                    this.g1.setDisable(disableState);
                    break;
                case "g4":
                    this.g4.setDisable(disableState);
                    break;
                case "g7":
                    this.g7.setDisable(disableState);
            }
        }
    }
}
