
package hu.nye.progtech.rushhour.service;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.model.Player;
import hu.nye.progtech.rushhour.model.Vehicle;
import hu.nye.progtech.rushhour.model.Exit;
import hu.nye.progtech.rushhour.model.PlayerType;

import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private final Player humanPlayer;
    private final Player computerPlayer;
    private boolean finished;
    private Player winner;

    private final Exit humanExit;
    private final Exit computerExit;

    private boolean humanTurn = true; //Ez jelzi, hogy éppen az emberi játékos következik-e.


    public Game(
            Board board,
            Player humanPlayer,
            Player computerPlayer,
            Exit humanExit,
            Exit computerExit
    ) {

        this.board = Objects.requireNonNull(board);
        this.humanPlayer = Objects.requireNonNull(humanPlayer);
        this.computerPlayer = Objects.requireNonNull(computerPlayer);
        this.finished = false;
        this.winner = null;
        this.humanExit = Objects.requireNonNull(humanExit);
        this.computerExit = Objects.requireNonNull(computerExit);

        if (humanPlayer.getType() != PlayerType.HUMAN) {
            throw new IllegalArgumentException(
                    "First player must be human."
            );
        }

        if (computerPlayer.getType() != PlayerType.COMPUTER) {
            throw new IllegalArgumentException(
                    "Second player must be computer."
            );
        }

        if (humanPlayer.getTargetVehicleId().equals(
                computerPlayer.getTargetVehicleId()
        )) {
            throw new IllegalArgumentException(
                    "Players must have different target vehicles."
            );
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isHumanTurn() {
        return humanTurn;
    }

    private record PossibleMove(
            String vehicleId,
            int steps,
            Board resultingBoard
    ) {
    }

    private List<PossibleMove> findPossibleMoves() {

        List<PossibleMove> possibleMoves = new ArrayList<>();

        for (Vehicle vehicle : board.getVehicles()) {

            if (vehicle.getId().equals(
                    humanPlayer.getTargetVehicleId())) {
                continue;
            }

            Exit exit = null;

            if (vehicle.getId().equals(humanPlayer.getTargetVehicleId())) {
                exit = humanExit;
            } else if (vehicle.getId().equals(computerPlayer.getTargetVehicleId())) {
                exit = computerExit;
            }

            for (int steps = -5; steps <= 5; steps++) {

                if (steps == 0) {
                    continue;
                }

                try {
                    Board resultingBoard;

                    if (exit != null) {
                        resultingBoard = board.moveVehicle(
                                vehicle.getId(), steps, exit
                        );
                    } else {
                        resultingBoard = board.moveVehicle(
                                vehicle.getId(), steps
                        );
                    }

                    possibleMoves.add(new PossibleMove(
                            vehicle.getId(), steps, resultingBoard
                    ));

                } catch (IllegalArgumentException exception) {
                    // Ez a lépés nem szabályos, ezért kihagyjuk.
                }
            }
        }

        return possibleMoves;
    }

    private final Random random = new Random();

    public void moveVehicle(String vehicleId, int steps) {
        if (finished) {
            throw new IllegalStateException("The game is already finished.");
        }

        if (!humanTurn) {
            throw new IllegalStateException("It is not the human player's turn.");
        }

        Exit exit = null;

        if (humanPlayer.getTargetVehicleId().equals(vehicleId)) {
            exit = humanExit;
        } else if (computerPlayer.getTargetVehicleId().equals(vehicleId)) {
            exit = computerExit;
        }

        if (exit != null) {
            board = board.moveVehicle(vehicleId, steps, exit);
        } else {
            board = board.moveVehicle(vehicleId, steps);
        }

        checkWinner();

        if (!finished) {
            humanTurn = false;
        }
    }


    private void checkWinner() {
        boolean humanTargetExists = false;
        boolean computerTargetExists = false;

        for (Vehicle vehicle : board.getVehicles()) {
            if (vehicle.getId().equals(
                    humanPlayer.getTargetVehicleId())) {
                humanTargetExists = true;
            }

            if (vehicle.getId().equals(
                    computerPlayer.getTargetVehicleId())) {
                computerTargetExists = true;
            }
        }

        if (!humanTargetExists) {
            finishGame(humanPlayer);
        } else if (!computerTargetExists) {
            finishGame(computerPlayer);
        }
    }


    public void computerMove() {

        if (finished) {
            throw new IllegalStateException(
                    "The game is already finished."
            );
        }

        if (humanTurn) {
            throw new IllegalStateException(
                    "It is not the computer player's turn."
            );
        }

        List<PossibleMove> possibleMoves = findPossibleMoves();

        if (possibleMoves.isEmpty()) {
            throw new IllegalStateException(
                    "The computer has no possible moves."
            );
        }

        PossibleMove selectedMove = possibleMoves.get(
                random.nextInt(possibleMoves.size())
        );

        board = selectedMove.resultingBoard();

        checkWinner();

        if (!finished) {
            humanTurn = true;
        }
    }


    public Exit getHumanExit() {
        return humanExit;
    }

    public Exit getComputerExit() {
        return computerExit;
    }
    public boolean isTargetVehicle(String vehicleId) {
        return humanPlayer.getTargetVehicleId().equals(vehicleId)
                || computerPlayer.getTargetVehicleId().equals(vehicleId);
    }

    public void finishGame(Player winningPlayer) {

        if (finished) {
            throw new IllegalStateException(
                    "The game is already finished."
            );
        }

        if (winningPlayer == null) {
            throw new IllegalArgumentException(
                    "Winner must not be null."
            );
        }

        if (!winningPlayer.equals(humanPlayer)
                && !winningPlayer.equals(computerPlayer)) {
            throw new IllegalArgumentException(
                    "Winner must be a player in this game."
            );
        }

        winner = winningPlayer;
        finished = true;
    }
}
