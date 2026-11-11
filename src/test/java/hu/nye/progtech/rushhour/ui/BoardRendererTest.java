
package hu.nye.progtech.rushhour.ui;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.model.Orientation;
import hu.nye.progtech.rushhour.model.Vehicle;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardRendererTest {

    @Test
    void shouldRenderEmptyBoard() {

        Board board = new Board(2, 3, List.of());

        BoardRenderer renderer = new BoardRenderer();

        String result = renderer.render(board);

        assertTrue(result.contains("a b c"));
        assertTrue(result.contains("1 | . . . |"));
        assertTrue(result.contains("2 | . . . |"));
    }

    @Test
    void shouldRenderHorizontalVehicle() {

        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 0, 1
        );

        Board board = new Board(2, 4, List.of(vehicle));

        BoardRenderer renderer = new BoardRenderer();

        String result = renderer.render(board);

        assertTrue(result.contains("1 | . A A . |"));
    }

    @Test
    void shouldRenderVerticalVehicle() {

        Vehicle vehicle = new Vehicle(
                "B", 2, Orientation.VERTICAL, 0, 2
        );

        Board board = new Board(3, 4, List.of(vehicle));

        BoardRenderer renderer = new BoardRenderer();

        String result = renderer.render(board);

        assertTrue(result.contains("1 | . . B . |"));
        assertTrue(result.contains("2 | . . B . |"));
    }
}
