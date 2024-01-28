//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    private boolean placingPhase = true;
    private boolean movingPhase = false;
    private boolean flyingPhase = false;
    private boolean removingPhase = false;
    private String placingPhaseTitle = "Placing Phase";
    private String placingPhaseDescription = "Click on any vacant point to place piece.";
    private String movingPhaseTitle = "Moving Phase";
    private String movingPhaseDescription = "Select a piece to move.";
    private String movingPhaseError = "No space to move this piece.";
    private String movingPhaseSuccess = "Select a spot where to move selected piece.";
    private String flyingPhaseTitle = "Flying Phase";
    private String flyingPhaseDescription = "Select a piece to move to any available spot.";
    private String flyingPhaseSuccess = "Select a spot where to move selected piece.";
    private String removingPhaseDescription = "Click on any opponents piece to remove it.";
    private String removingPhaseError = "Selected piece forms a mill.";
    private String gameOverTitle = " won the game!";
    private String gameOverReason1 = "Opponent cannot move any piece.";
    private String gameOverReason2 = "Opponent has only 2 pieces left.";
    private List<String> allBoardSpots = new ArrayList(List.of("a1", "a4", "a7", "b2", "b4", "b6", "c3", "c4", "c5", "d1", "d2", "d3", "d5", "d6", "d7", "e3", "e4", "e5", "f2", "f4", "f6", "g1", "g4", "g7"));
    private List<String> neighbourSpots = new ArrayList();

    public Board() {
    }

    public String getRemovingPhaseError() {
        return this.removingPhaseError;
    }

    public boolean isPlacingPhase() {
        return this.placingPhase;
    }

    public void setPlacingPhase(boolean placingPhase) {
        this.placingPhase = placingPhase;
    }

    public boolean isMovingPhase() {
        return this.movingPhase;
    }

    public void setMovingPhase(boolean movingPhase) {
        this.movingPhase = movingPhase;
    }

    public boolean isFlyingPhase() {
        return this.flyingPhase;
    }

    public void setFlyingPhase(boolean flyingPhase) {
        this.flyingPhase = flyingPhase;
    }

    public boolean isRemovingPhase() {
        return this.removingPhase;
    }

    public void setRemovingPhase(boolean removingPhase) {
        this.removingPhase = removingPhase;
    }

    public String getPlacingPhaseTitle() {
        return this.placingPhaseTitle;
    }

    public String getPlacingPhaseDescription() {
        return this.placingPhaseDescription;
    }

    public String getMovingPhaseTitle() {
        return this.movingPhaseTitle;
    }

    public String getMovingPhaseDescription() {
        return this.movingPhaseDescription;
    }

    public String getMovingPhaseSuccess() {
        return this.movingPhaseSuccess;
    }

    public String getMovingPhaseError() {
        return this.movingPhaseError;
    }

    public String getFlyingPhaseTitle() {
        return this.flyingPhaseTitle;
    }

    public String getFlyingPhaseDescription() {
        return this.flyingPhaseDescription;
    }

    public String getFlyingPhaseSuccess() {
        return this.flyingPhaseSuccess;
    }

    public String getRemovingPhaseDescription() {
        return this.removingPhaseDescription;
    }

    public String getGameOverTitle() {
        return this.gameOverTitle;
    }

    public String getGameOverReason1() {
        return this.gameOverReason1;
    }

    public String getGameOverReason2() {
        return this.gameOverReason2;
    }

    public List<String> getAllBoardSpots() {
        return this.allBoardSpots;
    }

    public void checkIfThreeInARowWasBroken(String id, Player player) {
        String tmpCombination = "";
        Iterator var5 = player.getCombinations().iterator();

        while(var5.hasNext()) {
            String combination = (String)var5.next();
            if (combination.contains(id)) {
                tmpCombination = combination;
                break;
            }
        }

        player.removeCombination(tmpCombination);
    }

    public boolean checkThreeInARow(Player player, boolean minimaxMove) {
        String newCombination = this.checkAllPossibleCombinations(player.getOccupiedSpots(), player.getCombinations());
        if (newCombination != null) {
            if (!minimaxMove) {
                player.setNewCombination(newCombination);
            }

            return true;
        } else {
            return false;
        }
    }

    private String checkAllPossibleCombinations(List<String> spots, List<String> playerCombinations) {
        if (spots.contains("a7") && spots.contains("d7") && spots.contains("g7") && this.checkIfNewCombination("a7d7g7", playerCombinations)) {
            return "a7d7g7";
        } else if (spots.contains("b6") && spots.contains("d6") && spots.contains("f6") && this.checkIfNewCombination("b6d6f6", playerCombinations)) {
            return "b6d6f6";
        } else if (spots.contains("c5") && spots.contains("d5") && spots.contains("e5") && this.checkIfNewCombination("c5d5e5", playerCombinations)) {
            return "c5d5e5";
        } else if (spots.contains("a4") && spots.contains("b4") && spots.contains("c4") && this.checkIfNewCombination("a4b4c4", playerCombinations)) {
            return "a4b4c4";
        } else if (spots.contains("e4") && spots.contains("f4") && spots.contains("g4") && this.checkIfNewCombination("e4f4g4", playerCombinations)) {
            return "e4f4g4";
        } else if (spots.contains("c3") && spots.contains("d3") && spots.contains("e3") && this.checkIfNewCombination("c3d3e3", playerCombinations)) {
            return "c3d3e3";
        } else if (spots.contains("b2") && spots.contains("d2") && spots.contains("f2") && this.checkIfNewCombination("b2d2f2", playerCombinations)) {
            return "b2d2f2";
        } else if (spots.contains("a1") && spots.contains("d1") && spots.contains("g1") && this.checkIfNewCombination("a1d1g1", playerCombinations)) {
            return "a1d1g1";
        } else if (spots.contains("a7") && spots.contains("a4") && spots.contains("a1") && this.checkIfNewCombination("a7a4a1", playerCombinations)) {
            return "a7a4a1";
        } else if (spots.contains("b6") && spots.contains("b4") && spots.contains("b2") && this.checkIfNewCombination("b6b4b2", playerCombinations)) {
            return "b6b4b2";
        } else if (spots.contains("c5") && spots.contains("c4") && spots.contains("c3") && this.checkIfNewCombination("c5c4c3", playerCombinations)) {
            return "c5c4c3";
        } else if (spots.contains("d7") && spots.contains("d6") && spots.contains("d5") && this.checkIfNewCombination("d7d6d5", playerCombinations)) {
            return "d7d6d5";
        } else if (spots.contains("d3") && spots.contains("d2") && spots.contains("d1") && this.checkIfNewCombination("d3d2d1", playerCombinations)) {
            return "d3d2d1";
        } else if (spots.contains("e5") && spots.contains("e4") && spots.contains("e3") && this.checkIfNewCombination("e5e4e3", playerCombinations)) {
            return "e5e4e3";
        } else if (spots.contains("f6") && spots.contains("f4") && spots.contains("f2") && this.checkIfNewCombination("f6f4f2", playerCombinations)) {
            return "f6f4f2";
        } else {
            return spots.contains("g7") && spots.contains("g4") && spots.contains("g1") && this.checkIfNewCombination("g7g4g1", playerCombinations) ? "g7g4g1" : null;
        }
    }

    private boolean checkIfNewCombination(String tmpCombination, List<String> playerCombinations) {
        return playerCombinations.isEmpty() || !playerCombinations.contains(tmpCombination);
    }

    public List<String> getNeighbourSpots(String id) {
        this.neighbourSpots.clear();
        switch (id) {
            case "a1":
                this.neighbourSpots.add("d1");
                this.neighbourSpots.add("a4");
                break;
            case "a4":
                this.neighbourSpots.add("a1");
                this.neighbourSpots.add("a7");
                this.neighbourSpots.add("b4");
                break;
            case "a7":
                this.neighbourSpots.add("a4");
                this.neighbourSpots.add("d7");
                break;
            case "b2":
                this.neighbourSpots.add("b4");
                this.neighbourSpots.add("d2");
                break;
            case "b4":
                this.neighbourSpots.add("a4");
                this.neighbourSpots.add("b2");
                this.neighbourSpots.add("b6");
                this.neighbourSpots.add("c4");
                break;
            case "b6":
                this.neighbourSpots.add("b4");
                this.neighbourSpots.add("d6");
                break;
            case "c3":
                this.neighbourSpots.add("d3");
                this.neighbourSpots.add("c4");
                break;
            case "c4":
                this.neighbourSpots.add("b4");
                this.neighbourSpots.add("c3");
                this.neighbourSpots.add("c5");
                break;
            case "c5":
                this.neighbourSpots.add("c4");
                this.neighbourSpots.add("d5");
                break;
            case "d1":
                this.neighbourSpots.add("a1");
                this.neighbourSpots.add("d2");
                this.neighbourSpots.add("g1");
                break;
            case "d2":
                this.neighbourSpots.add("b2");
                this.neighbourSpots.add("d1");
                this.neighbourSpots.add("d3");
                this.neighbourSpots.add("f2");
                break;
            case "d3":
                this.neighbourSpots.add("c3");
                this.neighbourSpots.add("d2");
                this.neighbourSpots.add("e3");
                break;
            case "d5":
                this.neighbourSpots.add("c5");
                this.neighbourSpots.add("d6");
                this.neighbourSpots.add("e5");
                break;
            case "d6":
                this.neighbourSpots.add("b6");
                this.neighbourSpots.add("d5");
                this.neighbourSpots.add("d7");
                this.neighbourSpots.add("f6");
                break;
            case "d7":
                this.neighbourSpots.add("a7");
                this.neighbourSpots.add("d6");
                this.neighbourSpots.add("g7");
                break;
            case "e3":
                this.neighbourSpots.add("d3");
                this.neighbourSpots.add("e4");
                break;
            case "e4":
                this.neighbourSpots.add("e3");
                this.neighbourSpots.add("e5");
                this.neighbourSpots.add("f4");
                break;
            case "e5":
                this.neighbourSpots.add("d5");
                this.neighbourSpots.add("e4");
                break;
            case "f2":
                this.neighbourSpots.add("d2");
                this.neighbourSpots.add("f4");
                break;
            case "f4":
                this.neighbourSpots.add("e4");
                this.neighbourSpots.add("f2");
                this.neighbourSpots.add("f6");
                this.neighbourSpots.add("g4");
                break;
            case "f6":
                this.neighbourSpots.add("d6");
                this.neighbourSpots.add("f4");
                break;
            case "g1":
                this.neighbourSpots.add("d1");
                this.neighbourSpots.add("g4");
                break;
            case "g4":
                this.neighbourSpots.add("f4");
                this.neighbourSpots.add("g1");
                this.neighbourSpots.add("g7");
                break;
            case "g7":
                this.neighbourSpots.add("d7");
                this.neighbourSpots.add("g4");
        }

        return this.neighbourSpots;
    }

    public List<String> getEmptySpots(Player player, Player opponent) {
        List<String> emptySpots = new ArrayList();
        emptySpots.addAll(this.getAllBoardSpots());
        emptySpots.removeAll(player.getOccupiedSpots());
        emptySpots.removeAll(opponent.getOccupiedSpots());
        return emptySpots;
    }
}
