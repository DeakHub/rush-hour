
package hu.nye.progtech.rushhour.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldCreateEmptyBoard() {
        Board board = new Board(6, 12, List.of());

        assertEquals(6, board.getRows());
        assertEquals(12, board.getColumns());
        assertTrue(board.getVehicles().isEmpty());
    }

    @Test
    void shouldNotAllowInvalidDimensions() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(0, 12, List.of())
        );
    }

    @Test
    void shouldNotAllowVehicleOutsideBoard() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 0, 11
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(6, 12, List.of(vehicle))
        );
    }

    @Test
    void shouldNotAllowOverlappingVehicles() {
        Vehicle first = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 1
        );

        Vehicle second = new Vehicle(
                "B", 2, Orientation.VERTICAL, 0, 2
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(6, 12, List.of(first, second))
        );
    }

    @Test
    void shouldNotAllowDuplicateVehicleIds() {
        Vehicle first = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 0, 0
        );

        Vehicle second = new Vehicle(
                "A", 2, Orientation.VERTICAL, 2, 2
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(6, 12, List.of(first, second))
        );
    }

    @Test
    void shouldReportOccupiedCell() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 2
        );

        Board board = new Board(6, 12, List.of(vehicle));

        assertTrue(board.isCellOccupied(1, 2));
        assertTrue(board.isCellOccupied(1, 3));
    }

    @Test
    void shouldReportEmptyCell() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 2
        );

        Board board = new Board(6, 12, List.of(vehicle));

        assertFalse(board.isCellOccupied(4, 4));
    }

    @Test
    void shouldNotAllowCheckingCellOutsideBoard() {
        Board board = new Board(6, 12, List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> board.isCellOccupied(6, 0)
        );
    }

    @Test
    void shouldMoveVehicleToTheRight() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 1
        );

        Board board = new Board(6, 12, List.of(vehicle));
        Board movedBoard = board.moveVehicle("A", 2);

        assertEquals(3, movedBoard.getVehicles().get(0).getColumn());
        assertEquals(1, board.getVehicles().get(0).getColumn());
    }

    @Test
    void shouldMoveVehicleToTheLeft() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 3
        );

        Board board = new Board(6, 12, List.of(vehicle));
        Board movedBoard = board.moveVehicle("A", -2);

        assertEquals(1, movedBoard.getVehicles().get(0).getColumn());
    }

    @Test
    void shouldMoveVerticalVehicleDown() {
        Vehicle vehicle = new Vehicle(
                "B", 2, Orientation.VERTICAL, 1, 2
        );

        Board board = new Board(6, 12, List.of(vehicle));
        Board movedBoard = board.moveVehicle("B", 2);

        assertEquals(3, movedBoard.getVehicles().get(0).getRow());
    }

    @Test
    void shouldNotAllowMovingThroughAnotherVehicle() {
        Vehicle first = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 1
        );

        Vehicle obstacle = new Vehicle(
                "B", 2, Orientation.VERTICAL, 0, 3
        );

        Board board = new Board(6, 12, List.of(first, obstacle));

        assertThrows(
                IllegalArgumentException.class,
                () -> board.moveVehicle("A", 3)
        );
    }

    @Test
    void shouldNotAllowMovingOutsideBoard() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 9
        );

        Board board = new Board(6, 12, List.of(vehicle));

        assertThrows(
                IllegalArgumentException.class,
                () -> board.moveVehicle("A", 2)
        );
    }

    @Test
    void shouldNotAllowMovingUnknownVehicle() {
        Board board = new Board(6, 12, List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> board.moveVehicle("X", 1)
        );
    }

    @Test
    void shouldNotAllowZeroMovementOnBoard() {
        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 1
        );

        Board board = new Board(6, 12, List.of(vehicle));

        assertThrows(
                IllegalArgumentException.class,
                () -> board.moveVehicle("A", 0)
        );
    }
}
