//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nine.men.morris.ninemenmorris.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinimaxLogic {
    String[] boardState = new String[24];
    String playerColor;
    int numberOfActivePlayerPieces;
    int numberOfActiveOpponentPieces;
    int currentGamePhase;

    public MinimaxLogic() {
    }

    public String makeBestMoveForPlacingPhase(Player player, Player opponent) {
        this.createCurrentGameState(player, opponent);
        if (player.getColor().equals("white")) {
            this.playerColor = "white";
        } else {
            this.playerColor = "black";
        }

        this.currentGamePhase = 1;
        this.numberOfActivePlayerPieces = player.getNumOfPiecesPlaced();
        this.numberOfActiveOpponentPieces = opponent.getNumOfPiecesPlaced();
        double bestScore = Double.NEGATIVE_INFINITY;
        int moveIndex = 0;
        int tmpRemoveSpotIndex = 0;
        boolean removingPhase = false;

        for(int i = 0; i < this.boardState.length; ++i) {
            if (this.boardState[i] == null) {
                if (this.playerColor.equals("white")) {
                    this.boardState[i] = "W";
                    ++this.numberOfActivePlayerPieces;
                } else {
                    this.boardState[i] = "B";
                    ++this.numberOfActivePlayerPieces;
                }

                if (this.isItPartOfMill(i)) {
                    tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                    this.boardState[tmpRemoveSpotIndex] = null;
                    removingPhase = true;
                }

                double utilityScore = this.minimaxWithAlphaBetaPruningForPlacingPhase(i, false, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                this.boardState[i] = null;
                --this.numberOfActivePlayerPieces;
                if (removingPhase) {
                    if (this.playerColor.equals("white")) {
                        this.boardState[tmpRemoveSpotIndex] = "B";
                    } else {
                        this.boardState[tmpRemoveSpotIndex] = "W";
                    }

                    removingPhase = false;
                }

                if (utilityScore >= bestScore) {
                    bestScore = utilityScore;
                    moveIndex = i;
                }
            }
        }

        return this.getMoveFromIndex(moveIndex);
    }

    public List<String> makeBestMoveForMovingPhase(Player player, Player opponent) {
        this.createCurrentGameState(player, opponent);
        if (player.getColor().equals("white")) {
            this.playerColor = "white";
        } else {
            this.playerColor = "black";
        }

        this.currentGamePhase = 2;
        this.numberOfActivePlayerPieces = player.getNumOfActivePieces();
        this.numberOfActiveOpponentPieces = opponent.getNumOfActivePieces();
        double bestScore = Double.NEGATIVE_INFINITY;
        int currentMoveSpotIndex = 0;
        int nextMoveIndex = 0;
        int tmpRemoveSpotIndex = 0;
        boolean removingPhase = false;
        List<Integer> emptyNeighbors = new ArrayList();
        int i;
        Integer emptyNeighbor;
        Iterator var12;
        double utilityScore;
        if (this.playerColor.equals("white")) {
            for(i = 0; i < this.boardState.length; ++i) {
                if (this.boardState[i] == "W") {
                    emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                    var12 = emptyNeighbors.iterator();

                    while(var12.hasNext()) {
                        emptyNeighbor = (Integer)var12.next();
                        this.boardState[i] = null;
                        this.boardState[emptyNeighbor] = "W";
                        if (this.isItPartOfMill(emptyNeighbor)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            --this.numberOfActiveOpponentPieces;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, false, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                        this.boardState[i] = "W";
                        this.boardState[emptyNeighbor] = null;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                                ++this.numberOfActiveOpponentPieces;
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                                ++this.numberOfActiveOpponentPieces;
                            }

                            removingPhase = false;
                        }

                        if (utilityScore >= bestScore) {
                            bestScore = utilityScore;
                            currentMoveSpotIndex = i;
                            nextMoveIndex = emptyNeighbor;
                        }
                    }

                    emptyNeighbors.clear();
                }
            }
        } else {
            for(i = 0; i < this.boardState.length; ++i) {
                if (this.boardState[i] == "B") {
                    emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                    var12 = emptyNeighbors.iterator();

                    while(var12.hasNext()) {
                        emptyNeighbor = (Integer)var12.next();
                        this.boardState[i] = null;
                        this.boardState[emptyNeighbor] = "B";
                        if (this.isItPartOfMill(emptyNeighbor)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            --this.numberOfActiveOpponentPieces;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, false, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                        this.boardState[i] = "B";
                        this.boardState[emptyNeighbor] = null;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                                ++this.numberOfActiveOpponentPieces;
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                                ++this.numberOfActiveOpponentPieces;
                            }

                            removingPhase = false;
                        }

                        if (utilityScore >= bestScore) {
                            bestScore = utilityScore;
                            currentMoveSpotIndex = i;
                            nextMoveIndex = emptyNeighbor;
                        }
                    }

                    emptyNeighbors.clear();
                }
            }
        }

        List<String> spotAndMove = new ArrayList();
        String currentMoveSpot = this.getMoveFromIndex(currentMoveSpotIndex);
        String nextMove = this.getMoveFromIndex(nextMoveIndex);
        spotAndMove.add(currentMoveSpot);
        spotAndMove.add(nextMove);
        return spotAndMove;
    }

    public List<String> makeBestMoveForFlyingPhase(Player player, Player opponent) {
        this.createCurrentGameState(player, opponent);
        if (player.getColor().equals("white")) {
            this.playerColor = "white";
        } else {
            this.playerColor = "black";
        }

        this.currentGamePhase = 3;
        this.numberOfActivePlayerPieces = player.getNumOfActivePieces();
        this.numberOfActiveOpponentPieces = opponent.getNumOfActivePieces();
        double bestScore = Double.NEGATIVE_INFINITY;
        int currentMoveSpotIndex = 0;
        int nextMoveIndex = 0;
        int tmpRemoveSpotIndex = 0;
        boolean removingPhase = false;
        List<Integer> emptySpotsOnBoard = new ArrayList();
        int i;
        Integer emptySpot;
        Iterator var12;
        double utilityScore;
        if (this.playerColor.equals("white")) {
            for(i = 0; i < this.boardState.length; ++i) {
                if (this.boardState[i] == "W") {
                    emptySpotsOnBoard.addAll(this.getEmptySpots());
                    var12 = emptySpotsOnBoard.iterator();

                    while(var12.hasNext()) {
                        emptySpot = (Integer)var12.next();
                        this.boardState[i] = null;
                        this.boardState[emptySpot] = "W";
                        if (this.isItPartOfMill(emptySpot)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            --this.numberOfActiveOpponentPieces;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForFlyingPhase(emptySpot, false, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                        this.boardState[i] = "W";
                        this.boardState[emptySpot] = null;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                                ++this.numberOfActiveOpponentPieces;
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                                ++this.numberOfActiveOpponentPieces;
                            }

                            removingPhase = false;
                        }

                        if (utilityScore >= bestScore) {
                            bestScore = utilityScore;
                            currentMoveSpotIndex = i;
                            nextMoveIndex = emptySpot;
                        }
                    }

                    emptySpotsOnBoard.clear();
                }
            }
        } else {
            for(i = 0; i < this.boardState.length; ++i) {
                if (this.boardState[i] == "B") {
                    emptySpotsOnBoard.addAll(this.getEmptyNeighbors(i));
                    var12 = emptySpotsOnBoard.iterator();

                    while(var12.hasNext()) {
                        emptySpot = (Integer)var12.next();
                        this.boardState[i] = null;
                        this.boardState[emptySpot] = "B";
                        if (this.isItPartOfMill(emptySpot)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            --this.numberOfActiveOpponentPieces;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForFlyingPhase(emptySpot, false, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                        this.boardState[i] = "B";
                        this.boardState[emptySpot] = null;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                                ++this.numberOfActiveOpponentPieces;
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                                ++this.numberOfActiveOpponentPieces;
                            }

                            removingPhase = false;
                        }

                        if (utilityScore >= bestScore) {
                            bestScore = utilityScore;
                            currentMoveSpotIndex = i;
                            nextMoveIndex = emptySpot;
                        }
                    }

                    emptySpotsOnBoard.clear();
                }
            }
        }

        List<String> spotAndMove = new ArrayList();
        String currentMoveSpot = this.getMoveFromIndex(currentMoveSpotIndex);
        String nextMove = this.getMoveFromIndex(nextMoveIndex);
        spotAndMove.add(currentMoveSpot);
        spotAndMove.add(nextMove);
        return spotAndMove;
    }

    public String makeBestMoveForRemoving(Player player, Player opponent) {
        this.createCurrentGameState(player, opponent);
        if (player.getColor().equals("white")) {
            this.playerColor = "white";
        } else {
            this.playerColor = "black";
        }

        double bestScore = Double.NEGATIVE_INFINITY;
        int moveIndex = 0;

        for(int i = 0; i < this.boardState.length; ++i) {
            if (!this.isItPartOfMill(i)) {
                double utilityScore;
                if (this.playerColor.equals("white")) {
                    if (this.boardState[i] == "B") {
                        this.boardState[i] = null;
                        utilityScore = this.evaluateRemovingPhase();
                        this.boardState[i] = "B";
                        if (utilityScore >= bestScore) {
                            bestScore = utilityScore;
                            moveIndex = i;
                        }
                    }
                } else if (this.boardState[i] == "W") {
                    this.boardState[i] = null;
                    utilityScore = this.evaluateRemovingPhase();
                    this.boardState[i] = "W";
                    if (utilityScore >= bestScore) {
                        bestScore = utilityScore;
                        moveIndex = i;
                    }
                }
            }
        }

        return this.getMoveFromIndex(moveIndex);
    }

    private double minimaxWithAlphaBetaPruningForPlacingPhase(int positionOfNewSpot, boolean isMaximizing, int depth, double alpha, double beta) {
        if (depth != 0 && !this.checkIfEndOfPhase()) {
            double bestScore;
            int tmpRemoveSpotIndex;
            boolean removingPhase;
            int i;
            double utilityScore;
            if (isMaximizing) {
                bestScore = Double.NEGATIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;

                for(i = 0; i < this.boardState.length; ++i) {
                    if (this.boardState[i] == null) {
                        if (this.playerColor.equals("white")) {
                            this.boardState[i] = "W";
                            ++this.numberOfActivePlayerPieces;
                        } else {
                            this.boardState[i] = "B";
                            ++this.numberOfActivePlayerPieces;
                        }

                        if (this.isItPartOfMill(i)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForPlacingPhase(i, false, depth - 1, alpha, beta);
                        this.boardState[i] = null;
                        --this.numberOfActivePlayerPieces;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                            }

                            removingPhase = false;
                        }

                        bestScore = Math.max(utilityScore, bestScore);
                        alpha = Math.max(alpha, utilityScore);
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }

                return bestScore;
            } else {
                bestScore = Double.POSITIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;

                for(i = 0; i < this.boardState.length; ++i) {
                    if (this.boardState[i] == null) {
                        if (this.playerColor.equals("white")) {
                            this.boardState[i] = "B";
                            ++this.numberOfActiveOpponentPieces;
                        } else {
                            this.boardState[i] = "W";
                            ++this.numberOfActiveOpponentPieces;
                        }

                        if (this.isItPartOfMill(i)) {
                            tmpRemoveSpotIndex = this.removeOpponentsPiece("O");
                            this.boardState[tmpRemoveSpotIndex] = null;
                            removingPhase = true;
                        }

                        utilityScore = this.minimaxWithAlphaBetaPruningForPlacingPhase(i, true, depth - 1, alpha, beta);
                        this.boardState[i] = null;
                        --this.numberOfActiveOpponentPieces;
                        if (removingPhase) {
                            if (this.playerColor.equals("white")) {
                                this.boardState[tmpRemoveSpotIndex] = "W";
                            } else {
                                this.boardState[tmpRemoveSpotIndex] = "B";
                            }

                            removingPhase = false;
                        }

                        bestScore = Math.min(utilityScore, bestScore);
                        beta = Math.min(beta, utilityScore);
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }

                return bestScore;
            }
        } else {
            return this.evaluatePlacingPhase(positionOfNewSpot);
        }
    }

    private double minimaxWithAlphaBetaPruningForMovingPhase(int positionOfNewSpot, boolean isMaximizing, int depth, double alpha, double beta) {
        if (depth != 0 && !this.checkIfEndOfPhase()) {
            ArrayList emptyNeighbors;
            double bestScore;
            int tmpRemoveSpotIndex;
            boolean removingPhase;
            int i;
            Integer emptyNeighbor;
            Iterator var15;
            double utilityScore;
            if (isMaximizing) {
                emptyNeighbors = new ArrayList();
                bestScore = Double.NEGATIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;
                if (this.playerColor.equals("white")) {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "W") {
                            emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                            var15 = emptyNeighbors.iterator();

                            while(var15.hasNext()) {
                                emptyNeighbor = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptyNeighbor] = "W";
                                if (this.isItPartOfMill(emptyNeighbor)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActiveOpponentPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, false, depth - 1, alpha, beta);
                                this.boardState[i] = "W";
                                this.boardState[emptyNeighbor] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActiveOpponentPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActiveOpponentPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.max(utilityScore, bestScore);
                                alpha = Math.max(alpha, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptyNeighbors.clear();
                        }
                    }
                } else {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "B") {
                            emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                            var15 = emptyNeighbors.iterator();

                            while(var15.hasNext()) {
                                emptyNeighbor = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptyNeighbor] = "B";
                                if (this.isItPartOfMill(emptyNeighbor)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActiveOpponentPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, false, depth - 1, alpha, beta);
                                this.boardState[i] = "B";
                                this.boardState[emptyNeighbor] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActiveOpponentPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActiveOpponentPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.max(utilityScore, bestScore);
                                alpha = Math.max(alpha, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptyNeighbors.clear();
                        }
                    }
                }

                return bestScore;
            } else {
                emptyNeighbors = new ArrayList();
                bestScore = Double.NEGATIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;
                if (this.playerColor.equals("white")) {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "B") {
                            emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                            var15 = emptyNeighbors.iterator();

                            while(var15.hasNext()) {
                                emptyNeighbor = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptyNeighbor] = "B";
                                if (this.isItPartOfMill(emptyNeighbor)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("O");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActivePlayerPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, true, depth - 1, alpha, beta);
                                this.boardState[i] = "B";
                                this.boardState[emptyNeighbor] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActivePlayerPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActivePlayerPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.min(utilityScore, bestScore);
                                beta = Math.min(beta, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptyNeighbors.clear();
                        }
                    }
                } else {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "W") {
                            emptyNeighbors.addAll(this.getEmptyNeighbors(i));
                            var15 = emptyNeighbors.iterator();

                            while(var15.hasNext()) {
                                emptyNeighbor = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptyNeighbor] = "W";
                                if (this.isItPartOfMill(emptyNeighbor)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("O");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActivePlayerPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptyNeighbor, true, depth - 1, alpha, beta);
                                this.boardState[i] = "W";
                                this.boardState[emptyNeighbor] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActivePlayerPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActivePlayerPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.min(utilityScore, bestScore);
                                beta = Math.min(beta, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptyNeighbors.clear();
                        }
                    }
                }

                return bestScore;
            }
        } else {
            return this.evaluateMovingPhase(positionOfNewSpot);
        }
    }

    private double minimaxWithAlphaBetaPruningForFlyingPhase(int positionOfNewSpot, boolean isMaximizing, int depth, double alpha, double beta) {
        if (depth != 0 && !this.checkIfEndOfPhase()) {
            ArrayList emptySpots;
            double bestScore;
            int tmpRemoveSpotIndex;
            boolean removingPhase;
            int i;
            Integer emptySpot;
            Iterator var15;
            double utilityScore;
            if (isMaximizing) {
                emptySpots = new ArrayList();
                bestScore = Double.NEGATIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;
                if (this.playerColor.equals("white")) {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "W") {
                            emptySpots.addAll(this.getEmptySpots());
                            var15 = emptySpots.iterator();

                            while(var15.hasNext()) {
                                emptySpot = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptySpot] = "W";
                                if (this.isItPartOfMill(emptySpot)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActiveOpponentPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptySpot, false, depth - 1, alpha, beta);
                                this.boardState[i] = "W";
                                this.boardState[emptySpot] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActiveOpponentPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActiveOpponentPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.max(utilityScore, bestScore);
                                alpha = Math.max(alpha, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptySpots.clear();
                        }
                    }
                } else {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "B") {
                            emptySpots.addAll(this.getEmptyNeighbors(i));
                            var15 = emptySpots.iterator();

                            while(var15.hasNext()) {
                                emptySpot = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptySpot] = "B";
                                if (this.isItPartOfMill(emptySpot)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("P");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActiveOpponentPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForMovingPhase(emptySpot, false, depth - 1, alpha, beta);
                                this.boardState[i] = "B";
                                this.boardState[emptySpot] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActiveOpponentPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActiveOpponentPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.max(utilityScore, bestScore);
                                alpha = Math.max(alpha, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptySpots.clear();
                        }
                    }
                }

                return bestScore;
            } else {
                emptySpots = new ArrayList();
                bestScore = Double.NEGATIVE_INFINITY;
                tmpRemoveSpotIndex = 0;
                removingPhase = false;
                if (this.playerColor.equals("white")) {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "B") {
                            emptySpots.addAll(this.getEmptyNeighbors(i));
                            var15 = emptySpots.iterator();

                            while(var15.hasNext()) {
                                emptySpot = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptySpot] = "B";
                                if (this.isItPartOfMill(emptySpot)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("O");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActivePlayerPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForFlyingPhase(emptySpot, true, depth - 1, alpha, beta);
                                this.boardState[i] = "B";
                                this.boardState[emptySpot] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActivePlayerPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActivePlayerPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.min(utilityScore, bestScore);
                                beta = Math.min(beta, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptySpots.clear();
                        }
                    }
                } else {
                    for(i = 0; i < this.boardState.length; ++i) {
                        if (this.boardState[i] == "W") {
                            emptySpots.addAll(this.getEmptyNeighbors(i));
                            var15 = emptySpots.iterator();

                            while(var15.hasNext()) {
                                emptySpot = (Integer)var15.next();
                                this.boardState[i] = null;
                                this.boardState[emptySpot] = "W";
                                if (this.isItPartOfMill(emptySpot)) {
                                    tmpRemoveSpotIndex = this.removeOpponentsPiece("O");
                                    this.boardState[tmpRemoveSpotIndex] = null;
                                    --this.numberOfActivePlayerPieces;
                                    removingPhase = true;
                                }

                                utilityScore = this.minimaxWithAlphaBetaPruningForFlyingPhase(emptySpot, true, depth - 1, alpha, beta);
                                this.boardState[i] = "W";
                                this.boardState[emptySpot] = null;
                                if (removingPhase) {
                                    if (this.playerColor.equals("white")) {
                                        this.boardState[tmpRemoveSpotIndex] = "W";
                                        ++this.numberOfActivePlayerPieces;
                                    } else {
                                        this.boardState[tmpRemoveSpotIndex] = "B";
                                        ++this.numberOfActivePlayerPieces;
                                    }

                                    removingPhase = false;
                                }

                                bestScore = Math.min(utilityScore, bestScore);
                                beta = Math.min(beta, utilityScore);
                                if (alpha >= beta) {
                                    break;
                                }
                            }

                            emptySpots.clear();
                        }
                    }
                }

                return bestScore;
            }
        } else {
            return this.evaluateFlyingPhase(positionOfNewSpot);
        }
    }

    private int removeOpponentsPiece(String player) {
        for(int i = 0; i < this.boardState.length; ++i) {
            if (player.equals("P")) {
                if (this.playerColor.equals("white")) {
                    if (this.boardState[i] == "B" && !this.isItPartOfMill(i)) {
                        return i;
                    }
                } else if (this.boardState[i] == "W" && !this.isItPartOfMill(i)) {
                    return i;
                }
            } else if (this.playerColor.equals("white")) {
                if (this.boardState[i] == "W" && !this.isItPartOfMill(i)) {
                    return i;
                }
            } else if (this.boardState[i] == "B" && !this.isItPartOfMill(i)) {
                return i;
            }
        }

        return 0;
    }

    private String getMoveFromIndex(int index) {
        String move = null;
        switch (index) {
            case 0:
                move = "a1";
                break;
            case 1:
                move = "a4";
                break;
            case 2:
                move = "a7";
                break;
            case 3:
                move = "b2";
                break;
            case 4:
                move = "b4";
                break;
            case 5:
                move = "b6";
                break;
            case 6:
                move = "c3";
                break;
            case 7:
                move = "c4";
                break;
            case 8:
                move = "c5";
                break;
            case 9:
                move = "d1";
                break;
            case 10:
                move = "d2";
                break;
            case 11:
                move = "d3";
                break;
            case 12:
                move = "d5";
                break;
            case 13:
                move = "d6";
                break;
            case 14:
                move = "d7";
                break;
            case 15:
                move = "e3";
                break;
            case 16:
                move = "e4";
                break;
            case 17:
                move = "e5";
                break;
            case 18:
                move = "f2";
                break;
            case 19:
                move = "f4";
                break;
            case 20:
                move = "f6";
                break;
            case 21:
                move = "g1";
                break;
            case 22:
                move = "g4";
                break;
            case 23:
                move = "g7";
        }

        return move;
    }

    private void createCurrentGameState(Player player, Player opponent) {
        List<String> whiteSpots = new ArrayList();
        List<String> blackSpots = new ArrayList();
        if (player.getColor().equals("white")) {
            whiteSpots.addAll(player.getOccupiedSpots());
            blackSpots.addAll(opponent.getOccupiedSpots());
        } else if (opponent.getColor().equals("white")) {
            whiteSpots.addAll(opponent.getOccupiedSpots());
            blackSpots.addAll(player.getOccupiedSpots());
        }

        if (whiteSpots.contains("a1")) {
            this.boardState[0] = "W";
        } else if (blackSpots.contains("a1")) {
            this.boardState[0] = "B";
        } else {
            this.boardState[0] = null;
        }

        if (whiteSpots.contains("a4")) {
            this.boardState[1] = "W";
        } else if (blackSpots.contains("a4")) {
            this.boardState[1] = "B";
        } else {
            this.boardState[1] = null;
        }

        if (whiteSpots.contains("a7")) {
            this.boardState[2] = "W";
        } else if (blackSpots.contains("a7")) {
            this.boardState[2] = "B";
        } else {
            this.boardState[2] = null;
        }

        if (whiteSpots.contains("b2")) {
            this.boardState[3] = "W";
        } else if (blackSpots.contains("b2")) {
            this.boardState[3] = "B";
        } else {
            this.boardState[3] = null;
        }

        if (whiteSpots.contains("b4")) {
            this.boardState[4] = "W";
        } else if (blackSpots.contains("b4")) {
            this.boardState[4] = "B";
        } else {
            this.boardState[4] = null;
        }

        if (whiteSpots.contains("b6")) {
            this.boardState[5] = "W";
        } else if (blackSpots.contains("b6")) {
            this.boardState[5] = "B";
        } else {
            this.boardState[5] = null;
        }

        if (whiteSpots.contains("c3")) {
            this.boardState[6] = "W";
        } else if (blackSpots.contains("c3")) {
            this.boardState[6] = "B";
        } else {
            this.boardState[6] = null;
        }

        if (whiteSpots.contains("c4")) {
            this.boardState[7] = "W";
        } else if (blackSpots.contains("c4")) {
            this.boardState[7] = "B";
        } else {
            this.boardState[7] = null;
        }

        if (whiteSpots.contains("c5")) {
            this.boardState[8] = "W";
        } else if (blackSpots.contains("c5")) {
            this.boardState[8] = "B";
        } else {
            this.boardState[8] = null;
        }

        if (whiteSpots.contains("d1")) {
            this.boardState[9] = "W";
        } else if (blackSpots.contains("d1")) {
            this.boardState[9] = "B";
        } else {
            this.boardState[9] = null;
        }

        if (whiteSpots.contains("d2")) {
            this.boardState[10] = "W";
        } else if (blackSpots.contains("d2")) {
            this.boardState[10] = "B";
        } else {
            this.boardState[10] = null;
        }

        if (whiteSpots.contains("d3")) {
            this.boardState[11] = "W";
        } else if (blackSpots.contains("d3")) {
            this.boardState[11] = "B";
        } else {
            this.boardState[11] = null;
        }

        if (whiteSpots.contains("d5")) {
            this.boardState[12] = "W";
        } else if (blackSpots.contains("d5")) {
            this.boardState[12] = "B";
        } else {
            this.boardState[12] = null;
        }

        if (whiteSpots.contains("d6")) {
            this.boardState[13] = "W";
        } else if (blackSpots.contains("d6")) {
            this.boardState[13] = "B";
        } else {
            this.boardState[13] = null;
        }

        if (whiteSpots.contains("d7")) {
            this.boardState[14] = "W";
        } else if (blackSpots.contains("d7")) {
            this.boardState[14] = "B";
        } else {
            this.boardState[14] = null;
        }

        if (whiteSpots.contains("e3")) {
            this.boardState[15] = "W";
        } else if (blackSpots.contains("e3")) {
            this.boardState[15] = "B";
        } else {
            this.boardState[15] = null;
        }

        if (whiteSpots.contains("e4")) {
            this.boardState[16] = "W";
        } else if (blackSpots.contains("e4")) {
            this.boardState[16] = "B";
        } else {
            this.boardState[16] = null;
        }

        if (whiteSpots.contains("e5")) {
            this.boardState[17] = "W";
        } else if (blackSpots.contains("e5")) {
            this.boardState[17] = "B";
        } else {
            this.boardState[17] = null;
        }

        if (whiteSpots.contains("f2")) {
            this.boardState[18] = "W";
        } else if (blackSpots.contains("f2")) {
            this.boardState[18] = "B";
        } else {
            this.boardState[18] = null;
        }

        if (whiteSpots.contains("f4")) {
            this.boardState[19] = "W";
        } else if (blackSpots.contains("f4")) {
            this.boardState[19] = "B";
        } else {
            this.boardState[19] = null;
        }

        if (whiteSpots.contains("f6")) {
            this.boardState[20] = "W";
        } else if (blackSpots.contains("f6")) {
            this.boardState[20] = "B";
        } else {
            this.boardState[20] = null;
        }

        if (whiteSpots.contains("g1")) {
            this.boardState[21] = "W";
        } else if (blackSpots.contains("g1")) {
            this.boardState[21] = "B";
        } else {
            this.boardState[21] = null;
        }

        if (whiteSpots.contains("g4")) {
            this.boardState[22] = "W";
        } else if (blackSpots.contains("g4")) {
            this.boardState[22] = "B";
        } else {
            this.boardState[22] = null;
        }

        if (whiteSpots.contains("g7")) {
            this.boardState[23] = "W";
        } else if (blackSpots.contains("g7")) {
            this.boardState[23] = "B";
        } else {
            this.boardState[23] = null;
        }

    }

    private boolean checkIfEndOfPhase() {
        if (this.currentGamePhase == 1) {
            if (this.numberOfActivePlayerPieces == 9 && this.numberOfActiveOpponentPieces == 9) {
                return true;
            }
        } else if (this.currentGamePhase == 2) {
            if (this.numberOfActiveOpponentPieces == 3) {
                return true;
            }
        } else if (this.currentGamePhase == 3 && this.numberOfActiveOpponentPieces == 2) {
            return true;
        }

        return false;
    }

    private double evaluatePlacingPhase(int positionOfNewSpot) {
        int formedMill = this.checkIfMillWasFormed(positionOfNewSpot);
        int differenceInMillNumber = this.differenceInMillNumber();
        int differenceInNumberOfBlockedPieces = this.differenceInNumberOfBlockedPieces();
        int differenceInPieceNumber = this.differenceInPieceNumber();
        int differenceIn2PieceConfigs = this.differenceIn2PieceConfigs();
        int differenceIn3PieceConfigs = this.differenceIn3PieceConfigs();
        return (double)(15 * formedMill + 26 * differenceInMillNumber + 1 * differenceInNumberOfBlockedPieces + 10 * differenceInPieceNumber + 10 * differenceIn2PieceConfigs + 5 * differenceIn3PieceConfigs);
    }

    private double evaluateMovingPhase(int positionOfNewSpot) {
        int formedMill = this.checkIfMillWasFormed(positionOfNewSpot);
        int differenceInMillNumber = this.differenceInMillNumber();
        int differenceInNumberOfBlockedPieces = this.differenceInNumberOfBlockedPieces();
        int differenceInPieceNumber = this.differenceInPieceNumber();
        int differenceInDoubleMills = this.differenceInDoubleMills();
        int gameOverState = this.checkIfGameOver();
        return (double)(15 * formedMill + 40 * differenceInMillNumber + 10 * differenceInNumberOfBlockedPieces + 10 * differenceInPieceNumber + 10 * differenceInDoubleMills + 1000 * gameOverState);
    }

    private double evaluateFlyingPhase(int positionOfNewSpot) {
        int formedMill = this.checkIfMillWasFormed(positionOfNewSpot);
        int differenceIn2PieceConfigs = this.differenceIn2PieceConfigs();
        int differenceIn3PieceConfigs = this.differenceIn3PieceConfigs();
        int gameOverState = this.checkIfGameOver();
        return (double)(15 * formedMill + 10 * differenceIn2PieceConfigs + 1 * differenceIn3PieceConfigs + 1200 * gameOverState);
    }

    private double evaluateRemovingPhase() {
        int numberTwo = this.differenceInMillNumber();
        int numberThree = this.differenceInNumberOfBlockedPieces();
        int numberFour = this.differenceInPieceNumber();
        int numberFive = this.differenceIn2PieceConfigs();
        int numberSix = this.differenceIn3PieceConfigs();
        return (double)(26 * numberTwo + 1 * numberThree + 9 * numberFour + 10 * numberFive + 7 * numberSix);
    }

    private int checkIfMillWasFormed(int positionOfNewSpot) {
        switch (positionOfNewSpot) {
            case 0:
                if (this.boardState[1] == this.boardState[0] && this.boardState[2] == this.boardState[0] || this.boardState[9] == this.boardState[0] && this.boardState[21] == this.boardState[0]) {
                    if ((!this.playerColor.equals("white") || this.boardState[0] != "W") && (!this.playerColor.equals("black") || this.boardState[0] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 1:
                if (this.boardState[0] == this.boardState[1] && this.boardState[2] == this.boardState[1] || this.boardState[4] == this.boardState[1] && this.boardState[7] == this.boardState[1]) {
                    return (!this.playerColor.equals("white") || this.boardState[1] != "W") && (!this.playerColor.equals("black") || this.boardState[1] != "B") ? -1 : 1;
                }
                break;
            case 2:
                if (this.boardState[0] == this.boardState[2] && this.boardState[1] == this.boardState[2] || this.boardState[14] == this.boardState[2] && this.boardState[23] == this.boardState[2]) {
                    if ((!this.playerColor.equals("white") || this.boardState[2] != "W") && (!this.playerColor.equals("black") || this.boardState[2] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 3:
                if (this.boardState[4] == this.boardState[3] && this.boardState[5] == this.boardState[3] || this.boardState[10] == this.boardState[3] && this.boardState[18] == this.boardState[3]) {
                    if ((!this.playerColor.equals("white") || this.boardState[3] != "W") && (!this.playerColor.equals("black") || this.boardState[3] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 4:
                if (this.boardState[3] == this.boardState[4] && this.boardState[5] == this.boardState[4] || this.boardState[1] == this.boardState[4] && this.boardState[7] == this.boardState[4]) {
                    if ((!this.playerColor.equals("white") || this.boardState[4] != "W") && (!this.playerColor.equals("black") || this.boardState[4] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 5:
                if (this.boardState[3] == this.boardState[5] && this.boardState[4] == this.boardState[5] || this.boardState[13] == this.boardState[5] && this.boardState[20] == this.boardState[5]) {
                    if ((!this.playerColor.equals("white") || this.boardState[5] != "W") && (!this.playerColor.equals("black") || this.boardState[5] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 6:
                if (this.boardState[7] == this.boardState[6] && this.boardState[8] == this.boardState[6] || this.boardState[11] == this.boardState[6] && this.boardState[15] == this.boardState[6]) {
                    if ((!this.playerColor.equals("white") || this.boardState[6] != "W") && (!this.playerColor.equals("black") || this.boardState[6] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 7:
                if (this.boardState[6] == this.boardState[7] && this.boardState[8] == this.boardState[7] || this.boardState[1] == this.boardState[7] && this.boardState[4] == this.boardState[7]) {
                    if ((!this.playerColor.equals("white") || this.boardState[7] != "W") && (!this.playerColor.equals("black") || this.boardState[7] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 8:
                if (this.boardState[6] == this.boardState[8] && this.boardState[7] == this.boardState[8] || this.boardState[12] == this.boardState[8] && this.boardState[17] == this.boardState[8]) {
                    if ((!this.playerColor.equals("white") || this.boardState[8] != "W") && (!this.playerColor.equals("black") || this.boardState[8] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 9:
                if ((this.boardState[10] != this.boardState[9] || this.boardState[11] != this.boardState[9]) && (this.boardState[0] != this.boardState[9] || this.boardState[21] != this.boardState[9])) {
                    break;
                }

                if ((!this.playerColor.equals("white") || this.boardState[9] != "W") && (!this.playerColor.equals("black") || this.boardState[9] != "B")) {
                    return -1;
                }

                return 1;
            case 10:
                if ((this.boardState[9] != this.boardState[10] || this.boardState[11] != this.boardState[10]) && (this.boardState[3] != this.boardState[10] || this.boardState[18] != this.boardState[10])) {
                    break;
                }

                if ((!this.playerColor.equals("white") || this.boardState[10] != "W") && (!this.playerColor.equals("black") || this.boardState[10] != "B")) {
                    return -1;
                }

                return 1;
            case 11:
                if (this.boardState[9] == this.boardState[11] && this.boardState[10] == this.boardState[11] || this.boardState[6] == this.boardState[11] && this.boardState[15] == this.boardState[11]) {
                    if ((!this.playerColor.equals("white") || this.boardState[11] != "W") && (!this.playerColor.equals("black") || this.boardState[11] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 12:
                if (this.boardState[13] == this.boardState[12] && this.boardState[14] == this.boardState[12] || this.boardState[8] == this.boardState[12] && this.boardState[17] == this.boardState[12]) {
                    if ((!this.playerColor.equals("white") || this.boardState[12] != "W") && (!this.playerColor.equals("black") || this.boardState[12] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 13:
                if (this.boardState[12] == this.boardState[13] && this.boardState[14] == this.boardState[13] || this.boardState[5] == this.boardState[13] && this.boardState[20] == this.boardState[13]) {
                    if ((!this.playerColor.equals("white") || this.boardState[13] != "W") && (!this.playerColor.equals("black") || this.boardState[13] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 14:
                if (this.boardState[12] == this.boardState[14] && this.boardState[13] == this.boardState[14] || this.boardState[2] == this.boardState[14] && this.boardState[23] == this.boardState[14]) {
                    if ((!this.playerColor.equals("white") || this.boardState[14] != "W") && (!this.playerColor.equals("black") || this.boardState[14] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 15:
                if (this.boardState[16] == this.boardState[15] && this.boardState[17] == this.boardState[15] || this.boardState[6] == this.boardState[15] && this.boardState[11] == this.boardState[15]) {
                    if ((!this.playerColor.equals("white") || this.boardState[15] != "W") && (!this.playerColor.equals("black") || this.boardState[15] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 16:
                if ((this.boardState[15] != this.boardState[16] || this.boardState[17] != this.boardState[16]) && (this.boardState[19] != this.boardState[16] || this.boardState[22] != this.boardState[16])) {
                    break;
                }

                if ((!this.playerColor.equals("white") || this.boardState[16] != "W") && (!this.playerColor.equals("black") || this.boardState[16] != "B")) {
                    return -1;
                }

                return 1;
            case 17:
                if ((this.boardState[15] != this.boardState[17] || this.boardState[16] != this.boardState[17]) && (this.boardState[8] != this.boardState[17] || this.boardState[12] != this.boardState[17])) {
                    break;
                }

                if ((!this.playerColor.equals("white") || this.boardState[17] != "W") && (!this.playerColor.equals("black") || this.boardState[17] != "B")) {
                    return -1;
                }

                return 1;
            case 18:
                if (this.boardState[19] == this.boardState[18] && this.boardState[20] == this.boardState[18] || this.boardState[3] == this.boardState[18] && this.boardState[10] == this.boardState[18]) {
                    if ((!this.playerColor.equals("white") || this.boardState[18] != "W") && (!this.playerColor.equals("black") || this.boardState[18] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 19:
                if (this.boardState[18] == this.boardState[19] && this.boardState[20] == this.boardState[19] || this.boardState[16] == this.boardState[19] && this.boardState[22] == this.boardState[19]) {
                    if ((!this.playerColor.equals("white") || this.boardState[19] != "W") && (!this.playerColor.equals("black") || this.boardState[19] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 20:
                if (this.boardState[18] == this.boardState[20] && this.boardState[19] == this.boardState[20] || this.boardState[5] == this.boardState[20] && this.boardState[13] == this.boardState[20]) {
                    if ((!this.playerColor.equals("white") || this.boardState[20] != "W") && (!this.playerColor.equals("black") || this.boardState[20] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 21:
                if (this.boardState[22] == this.boardState[21] && this.boardState[23] == this.boardState[21] || this.boardState[0] == this.boardState[21] && this.boardState[9] == this.boardState[21]) {
                    if ((!this.playerColor.equals("white") || this.boardState[21] != "W") && (!this.playerColor.equals("black") || this.boardState[21] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 22:
                if (this.boardState[21] == this.boardState[22] && this.boardState[23] == this.boardState[22] || this.boardState[16] == this.boardState[22] && this.boardState[19] == this.boardState[22]) {
                    if ((!this.playerColor.equals("white") || this.boardState[22] != "W") && (!this.playerColor.equals("black") || this.boardState[22] != "B")) {
                        return -1;
                    }

                    return 1;
                }
                break;
            case 23:
                if (this.boardState[21] == this.boardState[23] && this.boardState[22] == this.boardState[23] || this.boardState[2] == this.boardState[23] && this.boardState[14] == this.boardState[23]) {
                    if ((!this.playerColor.equals("white") || this.boardState[23] != "W") && (!this.playerColor.equals("black") || this.boardState[23] != "B")) {
                        return -1;
                    }

                    return 1;
                }
        }

        return 0;
    }

    private int differenceInMillNumber() {
        int whiteMills = 0;
        int blackMills = 0;
        if (this.boardState[0] == this.boardState[1] && this.boardState[1] == this.boardState[2]) {
            if (this.boardState[0] == "W") {
                ++whiteMills;
            } else if (this.boardState[0] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[3] == this.boardState[4] && this.boardState[4] == this.boardState[5]) {
            if (this.boardState[3] == "W") {
                ++whiteMills;
            } else if (this.boardState[3] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[6] == this.boardState[7] && this.boardState[7] == this.boardState[8]) {
            if (this.boardState[6] == "W") {
                ++whiteMills;
            } else if (this.boardState[6] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[9] == this.boardState[10] && this.boardState[10] == this.boardState[11]) {
            if (this.boardState[9] == "W") {
                ++whiteMills;
            } else if (this.boardState[9] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[12] == this.boardState[13] && this.boardState[13] == this.boardState[14]) {
            if (this.boardState[12] == "W") {
                ++whiteMills;
            } else if (this.boardState[12] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[15] == this.boardState[16] && this.boardState[16] == this.boardState[17]) {
            if (this.boardState[15] == "W") {
                ++whiteMills;
            } else if (this.boardState[15] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[18] == this.boardState[19] && this.boardState[19] == this.boardState[20]) {
            if (this.boardState[18] == "W") {
                ++whiteMills;
            } else if (this.boardState[18] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[21] == this.boardState[22] && this.boardState[22] == this.boardState[23]) {
            if (this.boardState[21] == "W") {
                ++whiteMills;
            } else if (this.boardState[21] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[0] == this.boardState[9] && this.boardState[9] == this.boardState[21]) {
            if (this.boardState[0] == "W") {
                ++whiteMills;
            } else if (this.boardState[0] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[3] == this.boardState[10] && this.boardState[10] == this.boardState[18]) {
            if (this.boardState[3] == "W") {
                ++whiteMills;
            } else if (this.boardState[3] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[6] == this.boardState[11] && this.boardState[11] == this.boardState[15]) {
            if (this.boardState[6] == "W") {
                ++whiteMills;
            } else if (this.boardState[6] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[1] == this.boardState[4] && this.boardState[4] == this.boardState[7]) {
            if (this.boardState[1] == "W") {
                ++whiteMills;
            } else if (this.boardState[1] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[16] == this.boardState[19] && this.boardState[19] == this.boardState[22]) {
            if (this.boardState[16] == "W") {
                ++whiteMills;
            } else if (this.boardState[16] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[8] == this.boardState[12] && this.boardState[12] == this.boardState[17]) {
            if (this.boardState[8] == "W") {
                ++whiteMills;
            } else if (this.boardState[8] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[5] == this.boardState[13] && this.boardState[13] == this.boardState[20]) {
            if (this.boardState[5] == "W") {
                ++whiteMills;
            } else if (this.boardState[5] == "B") {
                ++blackMills;
            }
        }

        if (this.boardState[2] == this.boardState[14] && this.boardState[14] == this.boardState[23]) {
            if (this.boardState[2] == "W") {
                ++whiteMills;
            } else if (this.boardState[2] == "B") {
                ++blackMills;
            }
        }

        return this.playerColor.equals("white") ? whiteMills - blackMills : blackMills - whiteMills;
    }

    private int differenceInNumberOfBlockedPieces() {
        int blockedWhitePieces = 0;
        int blockedBlackPieces = 0;
        int nullCounter = 0;

        for(int i = 0; i < this.boardState.length; ++i) {
            if (this.boardState[i] != null) {
                int[] neighbours = this.getNeighbours(i);
                int[] var9 = neighbours;
                int var8 = neighbours.length;

                for(int var7 = 0; var7 < var8; ++var7) {
                    int neighbour = var9[var7];
                    if (this.boardState[neighbour] == null) {
                        ++nullCounter;
                    }
                }

                if (nullCounter == 0) {
                    if (this.boardState[i] == "W") {
                        ++blockedWhitePieces;
                    } else if (this.boardState[i] == "B") {
                        ++blockedBlackPieces;
                    }
                }

                nullCounter = 0;
            }
        }

        if (this.playerColor.equals("white")) {
            return blockedBlackPieces - blockedWhitePieces;
        } else {
            return blockedWhitePieces - blockedBlackPieces;
        }
    }

    private int differenceInPieceNumber() {
        int whitePieces = 0;
        int blackPieces = 0;

        for(int i = 0; i < this.boardState.length; ++i) {
            if (this.boardState[i] == "W") {
                ++whitePieces;
            } else if (this.boardState[i] == "B") {
                ++blackPieces;
            }
        }

        if (this.playerColor.equals("white")) {
            return whitePieces - blackPieces;
        } else {
            return blackPieces - whitePieces;
        }
    }

    private int differenceIn2PieceConfigs() {
        int whiteTwoPieceConfigs = 0;
        int blackTwoPieceConfigs = 0;
        if (this.boardState[0] != null) {
            if (this.boardState[0] == this.boardState[1] && this.boardState[2] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[2] && this.boardState[1] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[9] && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[21] && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[1] != null) {
            if (this.boardState[1] == this.boardState[2] && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[1] == this.boardState[4] && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[1] == this.boardState[7] && this.boardState[4] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[2] != null) {
            if (this.boardState[2] == this.boardState[14] && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[2] == this.boardState[23] && this.boardState[14] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[3] != null) {
            if (this.boardState[3] == this.boardState[4] && this.boardState[5] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[5] && this.boardState[4] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[10] && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[18] && this.boardState[10] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[4] != null) {
            if (this.boardState[4] == this.boardState[5] && this.boardState[3] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[4] == this.boardState[7] && this.boardState[1] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[5] != null) {
            if (this.boardState[5] == this.boardState[13] && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[5] == this.boardState[20] && this.boardState[13] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[6] != null) {
            if (this.boardState[6] == this.boardState[7] && this.boardState[8] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[8] && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[11] && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[15] && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[7] != null && this.boardState[7] == this.boardState[8] && this.boardState[6] == null) {
            if (this.getColorOnSpot(this.boardState[7])) {
                ++whiteTwoPieceConfigs;
            } else {
                ++blackTwoPieceConfigs;
            }
        }

        if (this.boardState[8] != null) {
            if (this.boardState[8] == this.boardState[12] && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[8] == this.boardState[17] && this.boardState[12] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[9] != null) {
            if (this.boardState[9] == this.boardState[10] && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[9] == this.boardState[11] && this.boardState[10] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[9] == this.boardState[21] && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[10] != null) {
            if (this.boardState[10] == this.boardState[11] && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[10] == this.boardState[18] && this.boardState[3] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[11] != null && this.boardState[11] == this.boardState[15] && this.boardState[6] == null) {
            if (this.getColorOnSpot(this.boardState[11])) {
                ++whiteTwoPieceConfigs;
            } else {
                ++blackTwoPieceConfigs;
            }
        }

        if (this.boardState[12] != null) {
            if (this.boardState[12] == this.boardState[13] && this.boardState[14] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[12] == this.boardState[14] && this.boardState[13] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[12] == this.boardState[17] && this.boardState[8] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[13] != null) {
            if (this.boardState[13] == this.boardState[14] && this.boardState[12] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[13] == this.boardState[20] && this.boardState[5] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[14] != null && this.boardState[14] == this.boardState[23] && this.boardState[2] == null) {
            if (this.getColorOnSpot(this.boardState[14])) {
                ++whiteTwoPieceConfigs;
            } else {
                ++blackTwoPieceConfigs;
            }
        }

        if (this.boardState[15] != null) {
            if (this.boardState[15] == this.boardState[16] && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[15] == this.boardState[17] && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[16] != null) {
            if (this.boardState[16] == this.boardState[17] && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[16] == this.boardState[19] && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[16] == this.boardState[22] && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[18] != null) {
            if (this.boardState[18] == this.boardState[19] && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[18] == this.boardState[20] && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[19] != null) {
            if (this.boardState[19] == this.boardState[20] && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[19] == this.boardState[22] && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[21] != null) {
            if (this.boardState[21] == this.boardState[22] && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }

            if (this.boardState[21] == this.boardState[23] && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteTwoPieceConfigs;
                } else {
                    ++blackTwoPieceConfigs;
                }
            }
        }

        if (this.boardState[22] != null && this.boardState[22] == this.boardState[23] && this.boardState[21] == null) {
            if (this.getColorOnSpot(this.boardState[22])) {
                ++whiteTwoPieceConfigs;
            } else {
                ++blackTwoPieceConfigs;
            }
        }

        return this.playerColor.equals("white") ? whiteTwoPieceConfigs - blackTwoPieceConfigs : blackTwoPieceConfigs - whiteTwoPieceConfigs;
    }

    private int differenceIn3PieceConfigs() {
        int whiteThreePieceConfigs = 0;
        int blackThreePieceConfigs = 0;
        if (this.boardState[0] != null) {
            if (this.boardState[0] == this.boardState[1] && this.boardState[0] == this.boardState[9] && this.boardState[2] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[1] && this.boardState[0] == this.boardState[21] && this.boardState[2] == null && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[2] && this.boardState[0] == this.boardState[9] && this.boardState[1] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[0] == this.boardState[2] && this.boardState[0] == this.boardState[21] && this.boardState[1] == null && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[0])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[1] != null) {
            if (this.boardState[1] == this.boardState[2] && this.boardState[1] == this.boardState[4] && this.boardState[0] == null && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[1] == this.boardState[0] && this.boardState[1] == this.boardState[4] && this.boardState[2] == null && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[1] == this.boardState[2] && this.boardState[1] == this.boardState[7] && this.boardState[4] == null && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[1] == this.boardState[0] && this.boardState[1] == this.boardState[7] && this.boardState[4] == null && this.boardState[2] == null) {
                if (this.getColorOnSpot(this.boardState[1])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[2] != null) {
            if (this.boardState[2] == this.boardState[14] && this.boardState[2] == this.boardState[1] && this.boardState[23] == null && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[2] == this.boardState[14] && this.boardState[2] == this.boardState[0] && this.boardState[23] == null && this.boardState[1] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[2] == this.boardState[1] && this.boardState[2] == this.boardState[23] && this.boardState[14] == null && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[2] == this.boardState[0] && this.boardState[2] == this.boardState[23] && this.boardState[14] == null && this.boardState[1] == null) {
                if (this.getColorOnSpot(this.boardState[2])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[3] != null) {
            if (this.boardState[3] == this.boardState[4] && this.boardState[3] == this.boardState[10] && this.boardState[5] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[4] && this.boardState[3] == this.boardState[18] && this.boardState[5] == null && this.boardState[10] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[5] && this.boardState[3] == this.boardState[10] && this.boardState[4] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[3] == this.boardState[5] && this.boardState[3] == this.boardState[18] && this.boardState[4] == null && this.boardState[10] == null) {
                if (this.getColorOnSpot(this.boardState[3])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[4] != null) {
            if (this.boardState[4] == this.boardState[5] && this.boardState[4] == this.boardState[7] && this.boardState[1] == null && this.boardState[3] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[4] == this.boardState[3] && this.boardState[4] == this.boardState[7] && this.boardState[1] == null && this.boardState[5] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[4] == this.boardState[1] && this.boardState[4] == this.boardState[5] && this.boardState[3] == null && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[4] == this.boardState[1] && this.boardState[4] == this.boardState[3] && this.boardState[5] == null && this.boardState[7] == null) {
                if (this.getColorOnSpot(this.boardState[4])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[5] != null) {
            if (this.boardState[5] == this.boardState[13] && this.boardState[5] == this.boardState[4] && this.boardState[20] == null && this.boardState[3] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[5] == this.boardState[20] && this.boardState[5] == this.boardState[4] && this.boardState[13] == null && this.boardState[3] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[5] == this.boardState[13] && this.boardState[5] == this.boardState[3] && this.boardState[20] == null && this.boardState[4] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[5] == this.boardState[20] && this.boardState[5] == this.boardState[3] && this.boardState[13] == null && this.boardState[4] == null) {
                if (this.getColorOnSpot(this.boardState[5])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[6] != null) {
            if (this.boardState[6] == this.boardState[7] && this.boardState[6] == this.boardState[11] && this.boardState[8] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[7] && this.boardState[6] == this.boardState[15] && this.boardState[8] == null && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[8] && this.boardState[6] == this.boardState[11] && this.boardState[7] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[6] == this.boardState[8] && this.boardState[6] == this.boardState[15] && this.boardState[7] == null && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[6])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[7] != null) {
            if (this.boardState[7] == this.boardState[6] && this.boardState[7] == this.boardState[4] && this.boardState[1] == null && this.boardState[8] == null) {
                if (this.getColorOnSpot(this.boardState[7])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[7] == this.boardState[6] && this.boardState[7] == this.boardState[1] && this.boardState[4] == null && this.boardState[8] == null) {
                if (this.getColorOnSpot(this.boardState[7])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[7] == this.boardState[8] && this.boardState[7] == this.boardState[4] && this.boardState[1] == null && this.boardState[6] == null) {
                if (this.getColorOnSpot(this.boardState[7])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[7] == this.boardState[8] && this.boardState[7] == this.boardState[1] && this.boardState[4] == null && this.boardState[6] == null) {
                if (this.getColorOnSpot(this.boardState[7])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[8] != null) {
            if (this.boardState[8] == this.boardState[12] && this.boardState[8] == this.boardState[7] && this.boardState[6] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[8] == this.boardState[12] && this.boardState[8] == this.boardState[6] && this.boardState[7] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[8] == this.boardState[17] && this.boardState[8] == this.boardState[6] && this.boardState[7] == null && this.boardState[12] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[8] == this.boardState[17] && this.boardState[8] == this.boardState[7] && this.boardState[12] == null && this.boardState[6] == null) {
                if (this.getColorOnSpot(this.boardState[8])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[9] != null) {
            if (this.boardState[9] == this.boardState[0] && this.boardState[9] == this.boardState[10] && this.boardState[11] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[9] == this.boardState[0] && this.boardState[9] == this.boardState[11] && this.boardState[10] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[9] == this.boardState[21] && this.boardState[9] == this.boardState[10] && this.boardState[0] == null && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[9] == this.boardState[21] && this.boardState[9] == this.boardState[11] && this.boardState[10] == null && this.boardState[0] == null) {
                if (this.getColorOnSpot(this.boardState[9])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[10] != null) {
            if (this.boardState[10] == this.boardState[3] && this.boardState[10] == this.boardState[9] && this.boardState[11] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[10] == this.boardState[3] && this.boardState[10] == this.boardState[11] && this.boardState[9] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[10] == this.boardState[9] && this.boardState[10] == this.boardState[18] && this.boardState[3] == null && this.boardState[11] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[10] == this.boardState[11] && this.boardState[10] == this.boardState[18] && this.boardState[3] == null && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[10])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[11] != null) {
            if (this.boardState[11] == this.boardState[6] && this.boardState[11] == this.boardState[10] && this.boardState[9] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[11])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[11] == this.boardState[6] && this.boardState[11] == this.boardState[9] && this.boardState[10] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[11])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[11] == this.boardState[15] && this.boardState[11] == this.boardState[10] && this.boardState[6] == null && this.boardState[9] == null) {
                if (this.getColorOnSpot(this.boardState[11])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[11] == this.boardState[15] && this.boardState[11] == this.boardState[9] && this.boardState[6] == null && this.boardState[10] == null) {
                if (this.getColorOnSpot(this.boardState[11])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[12] != null) {
            if (this.boardState[12] == this.boardState[8] && this.boardState[12] == this.boardState[13] && this.boardState[14] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[12] == this.boardState[8] && this.boardState[12] == this.boardState[14] && this.boardState[13] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[12] == this.boardState[17] && this.boardState[12] == this.boardState[13] && this.boardState[8] == null && this.boardState[14] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[12] == this.boardState[17] && this.boardState[12] == this.boardState[14] && this.boardState[8] == null && this.boardState[13] == null) {
                if (this.getColorOnSpot(this.boardState[12])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[13] != null) {
            if (this.boardState[13] == this.boardState[5] && this.boardState[13] == this.boardState[12] && this.boardState[14] == null && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[13] == this.boardState[5] && this.boardState[13] == this.boardState[14] && this.boardState[12] == null && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[13] == this.boardState[20] && this.boardState[13] == this.boardState[12] && this.boardState[5] == null && this.boardState[14] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[13] == this.boardState[20] && this.boardState[13] == this.boardState[14] && this.boardState[5] == null && this.boardState[12] == null) {
                if (this.getColorOnSpot(this.boardState[13])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[14] != null) {
            if (this.boardState[14] == this.boardState[2] && this.boardState[14] == this.boardState[13] && this.boardState[12] == null && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[14])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[14] == this.boardState[2] && this.boardState[14] == this.boardState[12] && this.boardState[13] == null && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[14])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[14] == this.boardState[23] && this.boardState[14] == this.boardState[13] && this.boardState[2] == null && this.boardState[12] == null) {
                if (this.getColorOnSpot(this.boardState[14])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[14] == this.boardState[23] && this.boardState[14] == this.boardState[12] && this.boardState[2] == null && this.boardState[13] == null) {
                if (this.getColorOnSpot(this.boardState[14])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[15] != null) {
            if (this.boardState[15] == this.boardState[16] && this.boardState[15] == this.boardState[11] && this.boardState[6] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[15] == this.boardState[16] && this.boardState[15] == this.boardState[6] && this.boardState[11] == null && this.boardState[17] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[15] == this.boardState[17] && this.boardState[15] == this.boardState[6] && this.boardState[11] == null && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[15] == this.boardState[17] && this.boardState[15] == this.boardState[11] && this.boardState[6] == null && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[15])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[16] != null) {
            if (this.boardState[16] == this.boardState[15] && this.boardState[16] == this.boardState[19] && this.boardState[17] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[16] == this.boardState[15] && this.boardState[16] == this.boardState[22] && this.boardState[17] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[16] == this.boardState[17] && this.boardState[16] == this.boardState[19] && this.boardState[15] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[16] == this.boardState[17] && this.boardState[16] == this.boardState[22] && this.boardState[15] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[16])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[17] != null) {
            if (this.boardState[17] == this.boardState[12] && this.boardState[17] == this.boardState[16] && this.boardState[8] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[17])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[17] == this.boardState[12] && this.boardState[17] == this.boardState[15] && this.boardState[8] == null && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[17])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[17] == this.boardState[8] && this.boardState[17] == this.boardState[16] && this.boardState[12] == null && this.boardState[15] == null) {
                if (this.getColorOnSpot(this.boardState[17])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[17] == this.boardState[8] && this.boardState[17] == this.boardState[15] && this.boardState[12] == null && this.boardState[16] == null) {
                if (this.getColorOnSpot(this.boardState[17])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[18] != null) {
            if (this.boardState[18] == this.boardState[10] && this.boardState[18] == this.boardState[19] && this.boardState[3] == null && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[18] == this.boardState[10] && this.boardState[18] == this.boardState[20] && this.boardState[3] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[18] == this.boardState[3] && this.boardState[18] == this.boardState[19] && this.boardState[10] == null && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[18] == this.boardState[3] && this.boardState[18] == this.boardState[20] && this.boardState[10] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[18])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[19] != null) {
            if (this.boardState[19] == this.boardState[16] && this.boardState[19] == this.boardState[18] && this.boardState[20] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[19] == this.boardState[16] && this.boardState[19] == this.boardState[20] && this.boardState[18] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[19] == this.boardState[20] && this.boardState[19] == this.boardState[22] && this.boardState[16] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[19] == this.boardState[18] && this.boardState[19] == this.boardState[22] && this.boardState[16] == null && this.boardState[20] == null) {
                if (this.getColorOnSpot(this.boardState[19])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[20] != null) {
            if (this.boardState[20] == this.boardState[13] && this.boardState[20] == this.boardState[19] && this.boardState[5] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[20])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[20] == this.boardState[13] && this.boardState[20] == this.boardState[18] && this.boardState[5] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[20])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[20] == this.boardState[5] && this.boardState[20] == this.boardState[19] && this.boardState[13] == null && this.boardState[18] == null) {
                if (this.getColorOnSpot(this.boardState[20])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[20] == this.boardState[5] && this.boardState[20] == this.boardState[18] && this.boardState[13] == null && this.boardState[19] == null) {
                if (this.getColorOnSpot(this.boardState[20])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[21] != null) {
            if (this.boardState[21] == this.boardState[9] && this.boardState[21] == this.boardState[22] && this.boardState[0] == null && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[21] == this.boardState[9] && this.boardState[21] == this.boardState[23] && this.boardState[0] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[21] == this.boardState[0] && this.boardState[21] == this.boardState[22] && this.boardState[9] == null && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[21] == this.boardState[0] && this.boardState[21] == this.boardState[23] && this.boardState[9] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[21])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[22] != null) {
            if (this.boardState[22] == this.boardState[21] && this.boardState[22] == this.boardState[19] && this.boardState[16] == null && this.boardState[23] == null) {
                if (this.getColorOnSpot(this.boardState[22])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[22] == this.boardState[21] && this.boardState[22] == this.boardState[16] && this.boardState[19] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[22])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[22] == this.boardState[23] && this.boardState[22] == this.boardState[19] && this.boardState[16] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[22])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[22] == this.boardState[23] && this.boardState[22] == this.boardState[16] && this.boardState[19] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[22])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        if (this.boardState[23] != null) {
            if (this.boardState[23] == this.boardState[22] && this.boardState[23] == this.boardState[14] && this.boardState[2] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[23])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[23] == this.boardState[22] && this.boardState[23] == this.boardState[2] && this.boardState[14] == null && this.boardState[21] == null) {
                if (this.getColorOnSpot(this.boardState[23])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[23] == this.boardState[21] && this.boardState[23] == this.boardState[2] && this.boardState[14] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[23])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }

            if (this.boardState[23] == this.boardState[21] && this.boardState[23] == this.boardState[14] && this.boardState[2] == null && this.boardState[22] == null) {
                if (this.getColorOnSpot(this.boardState[23])) {
                    ++whiteThreePieceConfigs;
                } else {
                    ++blackThreePieceConfigs;
                }
            }
        }

        return this.playerColor.equals("white") ? whiteThreePieceConfigs - blackThreePieceConfigs : blackThreePieceConfigs - whiteThreePieceConfigs;
    }

    private int differenceInDoubleMills() {
        int whiteDoubleMills = 0;
        int blackDoubleMills = 0;
        if (this.boardState[0] != null && this.boardState[0] == this.boardState[1] && this.boardState[0] == this.boardState[2] && this.boardState[0] == this.boardState[9] && this.boardState[0] == this.boardState[21]) {
            if (this.getColorOnSpot(this.boardState[0])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[1] != null && this.boardState[1] == this.boardState[0] && this.boardState[1] == this.boardState[2] && this.boardState[1] == this.boardState[4] && this.boardState[1] == this.boardState[7]) {
            if (this.getColorOnSpot(this.boardState[1])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[2] != null && this.boardState[2] == this.boardState[1] && this.boardState[2] == this.boardState[0] && this.boardState[2] == this.boardState[14] && this.boardState[2] == this.boardState[23]) {
            if (this.getColorOnSpot(this.boardState[2])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[3] != null && this.boardState[3] == this.boardState[4] && this.boardState[3] == this.boardState[5] && this.boardState[3] == this.boardState[10] && this.boardState[3] == this.boardState[18]) {
            if (this.getColorOnSpot(this.boardState[3])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[4] != null && this.boardState[4] == this.boardState[1] && this.boardState[4] == this.boardState[7] && this.boardState[4] == this.boardState[3] && this.boardState[4] == this.boardState[5]) {
            if (this.getColorOnSpot(this.boardState[4])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[5] != null && this.boardState[5] == this.boardState[4] && this.boardState[5] == this.boardState[3] && this.boardState[5] == this.boardState[13] && this.boardState[5] == this.boardState[20]) {
            if (this.getColorOnSpot(this.boardState[5])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[6] != null && this.boardState[6] == this.boardState[7] && this.boardState[6] == this.boardState[8] && this.boardState[6] == this.boardState[11] && this.boardState[6] == this.boardState[15]) {
            if (this.getColorOnSpot(this.boardState[6])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[7] != null && this.boardState[7] == this.boardState[6] && this.boardState[7] == this.boardState[8] && this.boardState[7] == this.boardState[4] && this.boardState[7] == this.boardState[1]) {
            if (this.getColorOnSpot(this.boardState[7])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[8] != null && this.boardState[8] == this.boardState[7] && this.boardState[8] == this.boardState[6] && this.boardState[8] == this.boardState[12] && this.boardState[8] == this.boardState[17]) {
            if (this.getColorOnSpot(this.boardState[8])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[9] != null && this.boardState[9] == this.boardState[10] && this.boardState[9] == this.boardState[11] && this.boardState[9] == this.boardState[0] && this.boardState[9] == this.boardState[21]) {
            if (this.getColorOnSpot(this.boardState[9])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[10] != null && this.boardState[10] == this.boardState[9] && this.boardState[10] == this.boardState[11] && this.boardState[10] == this.boardState[3] && this.boardState[10] == this.boardState[18]) {
            if (this.getColorOnSpot(this.boardState[10])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[11] != null && this.boardState[11] == this.boardState[10] && this.boardState[11] == this.boardState[9] && this.boardState[11] == this.boardState[6] && this.boardState[11] == this.boardState[15]) {
            if (this.getColorOnSpot(this.boardState[11])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[12] != null && this.boardState[12] == this.boardState[8] && this.boardState[12] == this.boardState[17] && this.boardState[12] == this.boardState[13] && this.boardState[12] == this.boardState[14]) {
            if (this.getColorOnSpot(this.boardState[12])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[13] != null && this.boardState[13] == this.boardState[12] && this.boardState[13] == this.boardState[14] && this.boardState[13] == this.boardState[5] && this.boardState[13] == this.boardState[20]) {
            if (this.getColorOnSpot(this.boardState[13])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[14] != null && this.boardState[14] == this.boardState[2] && this.boardState[14] == this.boardState[23] && this.boardState[14] == this.boardState[13] && this.boardState[14] == this.boardState[12]) {
            if (this.getColorOnSpot(this.boardState[14])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[15] != null && this.boardState[15] == this.boardState[16] && this.boardState[15] == this.boardState[17] && this.boardState[15] == this.boardState[11] && this.boardState[15] == this.boardState[6]) {
            if (this.getColorOnSpot(this.boardState[15])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[16] != null && this.boardState[16] == this.boardState[15] && this.boardState[16] == this.boardState[17] && this.boardState[16] == this.boardState[19] && this.boardState[16] == this.boardState[22]) {
            if (this.getColorOnSpot(this.boardState[16])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[17] != null && this.boardState[17] == this.boardState[16] && this.boardState[17] == this.boardState[15] && this.boardState[17] == this.boardState[12] && this.boardState[17] == this.boardState[8]) {
            if (this.getColorOnSpot(this.boardState[17])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[18] != null && this.boardState[18] == this.boardState[19] && this.boardState[18] == this.boardState[20] && this.boardState[18] == this.boardState[10] && this.boardState[18] == this.boardState[3]) {
            if (this.getColorOnSpot(this.boardState[18])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[19] != null && this.boardState[19] == this.boardState[18] && this.boardState[19] == this.boardState[20] && this.boardState[19] == this.boardState[16] && this.boardState[19] == this.boardState[22]) {
            if (this.getColorOnSpot(this.boardState[19])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[20] != null && this.boardState[20] == this.boardState[19] && this.boardState[20] == this.boardState[18] && this.boardState[20] == this.boardState[13] && this.boardState[20] == this.boardState[5]) {
            if (this.getColorOnSpot(this.boardState[20])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[21] != null && this.boardState[21] == this.boardState[22] && this.boardState[21] == this.boardState[23] && this.boardState[21] == this.boardState[9] && this.boardState[21] == this.boardState[0]) {
            if (this.getColorOnSpot(this.boardState[21])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[22] != null && this.boardState[22] == this.boardState[21] && this.boardState[22] == this.boardState[23] && this.boardState[22] == this.boardState[19] && this.boardState[22] == this.boardState[16]) {
            if (this.getColorOnSpot(this.boardState[22])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        if (this.boardState[23] != null && this.boardState[23] == this.boardState[22] && this.boardState[23] == this.boardState[21] && this.boardState[23] == this.boardState[14] && this.boardState[23] == this.boardState[2]) {
            if (this.getColorOnSpot(this.boardState[23])) {
                ++whiteDoubleMills;
            } else {
                ++blackDoubleMills;
            }
        }

        return this.playerColor.equals("white") ? whiteDoubleMills - blackDoubleMills : blackDoubleMills - whiteDoubleMills;
    }

    private int checkIfGameOver() {
        int whitePieces = 0;
        int blackPieces = 0;
        int emptyNeighborsForWhite = 0;
        int emptyNeighborsForBlack = 0;
        boolean whiteLosesDueToPieces = false;
        boolean whiteLosesDueToMoves = false;
        boolean blackLosesDueToPieces = false;
        boolean blackLosesDueToMoves = false;

        for(int i = 0; i < this.boardState.length; ++i) {
            int[] neighbours;
            int neighbour;
            int var12;
            int var13;
            int[] var14;
            if (this.boardState[i] == "W") {
                ++whitePieces;
                if (emptyNeighborsForWhite < 1) {
                    neighbours = this.getNeighbours(i);
                    var14 = neighbours;
                    var13 = neighbours.length;

                    for(var12 = 0; var12 < var13; ++var12) {
                        neighbour = var14[var12];
                        if (this.boardState[neighbour] == null) {
                            ++emptyNeighborsForWhite;
                        }
                    }
                }
            } else if (this.boardState[i] == "B") {
                if (emptyNeighborsForWhite < 1) {
                    neighbours = this.getNeighbours(i);
                    var14 = neighbours;
                    var13 = neighbours.length;

                    for(var12 = 0; var12 < var13; ++var12) {
                        neighbour = var14[var12];
                        if (this.boardState[neighbour] == null) {
                            ++emptyNeighborsForBlack;
                        }
                    }
                }

                ++blackPieces;
            }
        }

        if (blackPieces < 3) {
            blackLosesDueToPieces = true;
        }

        if (whitePieces < 3) {
            whiteLosesDueToPieces = true;
        }

        if (emptyNeighborsForBlack == 0) {
            blackLosesDueToMoves = true;
        }

        if (emptyNeighborsForWhite == 0) {
            whiteLosesDueToMoves = true;
        }

        if (this.playerColor.equals("white")) {
            if (blackLosesDueToPieces || blackLosesDueToMoves) {
                return 1;
            }

            if (whiteLosesDueToPieces || whiteLosesDueToMoves) {
                return -1;
            }
        } else {
            if (whiteLosesDueToPieces || whiteLosesDueToMoves) {
                return 1;
            }

            if (blackLosesDueToPieces || blackLosesDueToMoves) {
                return -1;
            }
        }

        return 0;
    }

    private boolean getColorOnSpot(String spot) {
        return spot == "W";
    }

    private int[] getNeighbours(int spot) {
        switch (spot) {
            case 0:
                return new int[]{1, 9};
            case 1:
                return new int[]{0, 2, 4};
            case 2:
                return new int[]{1, 14};
            case 3:
                return new int[]{4, 10};
            case 4:
                return new int[]{1, 3, 5, 7};
            case 5:
                return new int[]{4, 13};
            case 6:
                return new int[]{7, 11};
            case 7:
                return new int[]{4, 6, 8};
            case 8:
                return new int[]{7, 12};
            case 9:
                return new int[]{0, 10, 21};
            case 10:
                return new int[]{3, 9, 11, 18};
            case 11:
                return new int[]{6, 10, 15};
            case 12:
                return new int[]{8, 13, 17};
            case 13:
                return new int[]{5, 12, 14, 20};
            case 14:
                return new int[]{2, 13, 23};
            case 15:
                return new int[]{11, 16};
            case 16:
                return new int[]{15, 17, 19};
            case 17:
                return new int[]{12, 16};
            case 18:
                return new int[]{10, 19};
            case 19:
                return new int[]{16, 18, 20, 22};
            case 20:
                return new int[]{13, 19};
            case 21:
                return new int[]{9, 22};
            case 22:
                return new int[]{19, 21, 23};
            case 23:
                return new int[]{14, 22};
            default:
                return new int[0];
        }
    }

    private List<Integer> getEmptyNeighbors(int spot) {
        List<Integer> emptyNeighbors = new ArrayList();
        int[] var6;
        int var5 = (var6 = this.getNeighbours(spot)).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            int neighbor = var6[var4];
            if (this.boardState[neighbor] == null) {
                emptyNeighbors.add(neighbor);
            }
        }

        return emptyNeighbors;
    }

    private List<Integer> getEmptySpots() {
        List<Integer> emptySpots = new ArrayList();

        for(int i = 0; i < this.boardState.length; ++i) {
            if (this.boardState[i] == null) {
                emptySpots.add(i);
            }
        }

        return emptySpots;
    }

    private boolean isItPartOfMill(int i) {
        switch (i) {
            case 0:
                if (this.boardState[1] == this.boardState[0] && this.boardState[2] == this.boardState[0] || this.boardState[9] == this.boardState[0] && this.boardState[21] == this.boardState[0]) {
                    return true;
                }
                break;
            case 1:
                if (this.boardState[0] == this.boardState[1] && this.boardState[2] == this.boardState[1] || this.boardState[4] == this.boardState[1] && this.boardState[7] == this.boardState[1]) {
                    return true;
                }
                break;
            case 2:
                if (this.boardState[0] == this.boardState[2] && this.boardState[1] == this.boardState[2] || this.boardState[14] == this.boardState[2] && this.boardState[23] == this.boardState[2]) {
                    return true;
                }
                break;
            case 3:
                if (this.boardState[4] == this.boardState[3] && this.boardState[5] == this.boardState[3] || this.boardState[10] == this.boardState[3] && this.boardState[18] == this.boardState[3]) {
                    return true;
                }
                break;
            case 4:
                if (this.boardState[3] == this.boardState[4] && this.boardState[5] == this.boardState[4] || this.boardState[1] == this.boardState[4] && this.boardState[7] == this.boardState[4]) {
                    return true;
                }
                break;
            case 5:
                if (this.boardState[3] == this.boardState[5] && this.boardState[4] == this.boardState[5] || this.boardState[13] == this.boardState[5] && this.boardState[20] == this.boardState[5]) {
                    return true;
                }
                break;
            case 6:
                if (this.boardState[7] == this.boardState[6] && this.boardState[8] == this.boardState[6] || this.boardState[11] == this.boardState[6] && this.boardState[15] == this.boardState[6]) {
                    return true;
                }
                break;
            case 7:
                if (this.boardState[6] == this.boardState[7] && this.boardState[8] == this.boardState[7] || this.boardState[1] == this.boardState[7] && this.boardState[4] == this.boardState[7]) {
                    return true;
                }
                break;
            case 8:
                if (this.boardState[6] == this.boardState[8] && this.boardState[7] == this.boardState[8] || this.boardState[12] == this.boardState[8] && this.boardState[17] == this.boardState[8]) {
                    return true;
                }
                break;
            case 9:
                if (this.boardState[10] == this.boardState[9] && this.boardState[11] == this.boardState[9] || this.boardState[0] == this.boardState[9] && this.boardState[21] == this.boardState[9]) {
                    return true;
                }
                break;
            case 10:
                if (this.boardState[9] == this.boardState[10] && this.boardState[11] == this.boardState[10] || this.boardState[3] == this.boardState[10] && this.boardState[18] == this.boardState[10]) {
                    return true;
                }
                break;
            case 11:
                if (this.boardState[9] == this.boardState[11] && this.boardState[10] == this.boardState[11] || this.boardState[6] == this.boardState[11] && this.boardState[15] == this.boardState[11]) {
                    return true;
                }
                break;
            case 12:
                if (this.boardState[13] == this.boardState[12] && this.boardState[14] == this.boardState[12] || this.boardState[8] == this.boardState[12] && this.boardState[17] == this.boardState[12]) {
                    return true;
                }
                break;
            case 13:
                if (this.boardState[12] == this.boardState[13] && this.boardState[14] == this.boardState[13] || this.boardState[5] == this.boardState[13] && this.boardState[20] == this.boardState[13]) {
                    return true;
                }
                break;
            case 14:
                if (this.boardState[12] == this.boardState[14] && this.boardState[13] == this.boardState[14] || this.boardState[2] == this.boardState[14] && this.boardState[23] == this.boardState[14]) {
                    return true;
                }
                break;
            case 15:
                if (this.boardState[16] == this.boardState[15] && this.boardState[17] == this.boardState[15] || this.boardState[6] == this.boardState[15] && this.boardState[11] == this.boardState[15]) {
                    return true;
                }
                break;
            case 16:
                if (this.boardState[15] == this.boardState[16] && this.boardState[17] == this.boardState[16] || this.boardState[19] == this.boardState[16] && this.boardState[22] == this.boardState[16]) {
                    return true;
                }
                break;
            case 17:
                if (this.boardState[15] == this.boardState[17] && this.boardState[16] == this.boardState[17] || this.boardState[8] == this.boardState[17] && this.boardState[12] == this.boardState[17]) {
                    return true;
                }
                break;
            case 18:
                if (this.boardState[19] == this.boardState[18] && this.boardState[20] == this.boardState[18] || this.boardState[3] == this.boardState[18] && this.boardState[10] == this.boardState[18]) {
                    return true;
                }
                break;
            case 19:
                if (this.boardState[18] == this.boardState[19] && this.boardState[20] == this.boardState[19] || this.boardState[16] == this.boardState[19] && this.boardState[22] == this.boardState[19]) {
                    return true;
                }
                break;
            case 20:
                if (this.boardState[18] == this.boardState[20] && this.boardState[19] == this.boardState[20] || this.boardState[3] == this.boardState[20] && this.boardState[10] == this.boardState[20]) {
                    return true;
                }
                break;
            case 21:
                if (this.boardState[22] == this.boardState[21] && this.boardState[23] == this.boardState[21] || this.boardState[0] == this.boardState[21] && this.boardState[9] == this.boardState[21]) {
                    return true;
                }
                break;
            case 22:
                if (this.boardState[21] == this.boardState[22] && this.boardState[23] == this.boardState[22] || this.boardState[16] == this.boardState[22] && this.boardState[19] == this.boardState[22]) {
                    return true;
                }
                break;
            case 23:
                if (this.boardState[21] == this.boardState[23] && this.boardState[22] == this.boardState[23] || this.boardState[2] == this.boardState[23] && this.boardState[14] == this.boardState[23]) {
                    return true;
                }
        }

        return false;
    }
}
