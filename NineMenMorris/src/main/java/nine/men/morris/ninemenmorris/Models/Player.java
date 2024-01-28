//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private String color;
    private String style;
    private int numOfPiecesPlaced;
    private int numOfActivePieces;
    private boolean isActivePlayer;
    private boolean isAI;
    private boolean isFlying;
    private List<String> occupiedSpots;
    private List<String> combinations;

    public Player(String name, String color, String style, boolean isActivePlayer, boolean isAI) {
        this.name = name;
        this.color = color;
        this.style = style;
        this.numOfPiecesPlaced = 0;
        this.numOfActivePieces = 0;
        this.isActivePlayer = isActivePlayer;
        this.isAI = isAI;
        this.isFlying = false;
        this.occupiedSpots = new ArrayList();
        this.combinations = new ArrayList();
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public boolean isAI() {
        return this.isAI;
    }

    public List<String> getCombinations() {
        return this.combinations;
    }

    public void setNewCombination(String combination) {
        this.combinations.add(combination);
    }

    public void removeCombination(String combination) {
        this.combinations.remove(combination);
    }

    public boolean isActivePlayer() {
        return this.isActivePlayer;
    }

    public void setActivePlayer(boolean isActivePlayer) {
        this.isActivePlayer = isActivePlayer;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public String getStyle() {
        return this.style;
    }

    public List<String> getOccupiedSpots() {
        return (List)this.occupiedSpots.stream().sorted().collect(Collectors.toList());
    }

    public void setNewOccupiedSpot(String spot) {
        this.occupiedSpots.add(spot);
    }

    public void removeOccupiedSpot(String spot) {
        this.occupiedSpots.remove(spot);
    }

    public int getNumOfPiecesPlaced() {
        return this.numOfPiecesPlaced;
    }

    public void increaseNumOfPiecesPlacedBy1() {
        ++this.numOfPiecesPlaced;
    }

    public int getNumOfActivePieces() {
        return this.numOfActivePieces;
    }

    public void increaseNumOfActivePiecesBy1() {
        ++this.numOfActivePieces;
    }

    public void decreaseNumOfActivePiecesBy1() {
        --this.numOfActivePieces;
    }
}
