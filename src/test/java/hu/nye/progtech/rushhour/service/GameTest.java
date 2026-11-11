
package hu.nye.progtech.rushhour.service;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.model.Player;
import hu.nye.progtech.rushhour.model.PlayerType;
import hu.nye.progtech.rushhour.model.Orientation;
import hu.nye.progtech.rushhour.model.Vehicle;
import hu.nye.progtech.rushhour.model.Exit;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldCreateGame() {
        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );
        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Game game = new Game(board, human, computer, humanExit, computerExit);

        assertEquals(board, game.getBoard());
        assertEquals(human, game.getHumanPlayer());
        assertEquals(computer, game.getComputerPlayer());
        assertFalse(game.isFinished());
        assertNull(game.getWinner());

        //assertEquals(humanExit, game.getHumanExit());
        //assertEquals(computerExit, game.getComputerExit());
        assertSame(humanExit, game.getHumanExit());
        assertSame(computerExit, game.getComputerExit());

    }

    @Test
    void shouldNotAllowComputerAsHumanPlayer() {

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Board board = new Board(6, 12, List.of());

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "A"
        );

        Player secondComputer = new Player(
                "Computer 2", PlayerType.COMPUTER, "B"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(board, computer, secondComputer, humanExit, computerExit)
        );
    }

    @Test
    void shouldNotAllowSameTargetVehicle() {

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Player", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "A"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(board, human, computer,humanExit, computerExit)
        );
    }

    @Test
    void shouldMoveVehicle() {

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 2, 1
        );

        Board board = new Board(6, 12, List.of(vehicle));

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Game game = new Game(board, human, computer, humanExit, computerExit);

        game.moveVehicle("A", 1);

        assertEquals(2, game.getBoard().getVehicles()
                .get(0).getColumn());

        assertEquals(1, board.getVehicles()
                .get(0).getColumn());
    }

    @Test
    void shouldRecognizeHumanTargetVehicle() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        assertTrue(game.isTargetVehicle("A"));
    }

    @Test
    void shouldRecognizeComputerTargetVehicle() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        assertTrue(game.isTargetVehicle("B"));
    }

    @Test
    void shouldNotRecognizeNormalVehicleAsTarget() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        assertFalse(game.isTargetVehicle("C"));
    }

    @Test
    void shouldFinishGameWithHumanWinner() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.finishGame(human);

        assertTrue(game.isFinished());
        assertEquals(human, game.getWinner());
    }

    @Test
    void shouldNotFinishGameWithUnknownWinner() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Player unknown = new Player(
                "Ismeretlen", PlayerType.HUMAN, "C"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> game.finishGame(unknown)
        );

        assertFalse(game.isFinished());
    }

    @Test
    void shouldNotFinishGameTwice() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.finishGame(human);

        assertThrows(
                IllegalStateException.class,
                () -> game.finishGame(computer)
        );
    }

    @Test
    void shouldFinishGameWhenHumanReachesExit() {

        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 2, 9
        );

        Board board = new Board(6, 12, List.of(vehicle));

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.moveVehicle("A", 2);

        assertTrue(game.isFinished());
        assertEquals(human, game.getWinner());
    }

    @Test
    void shouldFinishGameWhenComputerReachesExit() {

        Vehicle humanVehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 2, 0
        );

        Vehicle computerVehicle = new Vehicle(
                "B", 2, Orientation.VERTICAL, 3, 4
        );

        Board board = new Board(
                6, 12, List.of(humanVehicle, computerVehicle)
        );

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 4, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        for (int i = 0; i < 100 && !game.isFinished(); i++) {
            game.moveVehicle("A", i % 2 == 0 ? 1 : -1);

            if (!game.isFinished()) {
                game.computerMove();
            }
        }

        assertTrue(game.isFinished());
        assertEquals(computer, game.getWinner());
    }

    @Test
    void shouldMoveComputerVehicle() {

        Vehicle vehicleA = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 0, 0
        );

        Vehicle vehicleB = new Vehicle(
                "B", 2, Orientation.HORIZONTAL, 2, 0
        );

        Board board = new Board(
                6, 12, List.of(vehicleA, vehicleB)
        );

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 0, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 2, 0, Orientation.HORIZONTAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.moveVehicle("A", 1);

        List<Vehicle> boardAfterHumanMove =
                game.getBoard().getVehicles();

        game.computerMove();

        assertNotEquals(
                boardAfterHumanMove,
                game.getBoard().getVehicles()
        );
    }

    @Test
    void shouldSwitchTurnsAfterComputerMove() {
        Board board = new Board(
                6,
                6,
                List.of(
                        new Vehicle(
                                "A", 2,
                                Orientation.HORIZONTAL, 0, 0
                        ),
                        new Vehicle(
                                "B", 2,
                                Orientation.HORIZONTAL, 3, 2
                        )
                )
        );

        Player human = new Player(
                "Player", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Game game = new Game(
                board,
                human,
                computer,
                new Exit("A", 0, 5, Orientation.HORIZONTAL),
                new Exit("B", 3, 0, Orientation.HORIZONTAL)
        );

        if (!game.isFinished()) {
            assertTrue(game.isHumanTurn());
        }
        game.moveVehicle("A", 1);

        assertFalse(game.isHumanTurn());

        game.computerMove();
    }

    @Test
    void shouldNotAllowHumanToMoveTwice() {
        Board board = new Board(
                6,
                6,
                List.of(
                        new Vehicle(
                                "A", 2,
                                Orientation.HORIZONTAL, 0, 0
                        ),
                        new Vehicle(
                                "B", 2,
                                Orientation.HORIZONTAL, 3, 0
                        )
                )
        );

        Player human = new Player(
                "Player", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Game game = new Game(
                board,
                human,
                computer,
                new Exit("A", 0, 5, Orientation.HORIZONTAL),
                new Exit("B", 5, 0, Orientation.VERTICAL)
        );

        game.moveVehicle("A", 1);

        assertThrows(
                IllegalStateException.class,
                () -> game.moveVehicle("A", 1)
        );
    }
    @Test
    void shouldNotAllowMoveAfterGameFinished() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.finishGame(human);

        assertThrows(
                IllegalStateException.class,
                () -> game.moveVehicle("A", 1)
        );
    }
    @Test
    void shouldNotAllowComputerMoveAfterGameFinished() {

        Board board = new Board(6, 12, List.of());

        Player human = new Player(
                "Játékos 1", PlayerType.HUMAN, "A"
        );

        Player computer = new Player(
                "Computer", PlayerType.COMPUTER, "B"
        );

        Exit humanExit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        Exit computerExit = new Exit(
                "B", 0, 0, Orientation.VERTICAL
        );

        Game game = new Game(
                board, human, computer, humanExit, computerExit
        );

        game.finishGame(human);

        assertThrows(
                IllegalStateException.class,
                game::computerMove
        );
    }

}
