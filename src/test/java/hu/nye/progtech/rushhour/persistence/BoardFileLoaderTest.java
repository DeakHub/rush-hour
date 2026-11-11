
package hu.nye.progtech.rushhour.persistence;

import hu.nye.progtech.rushhour.model.Board;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileLoaderTest {

    @Test
    void shouldCreateDefaultBoardWhenFileDoesNotExist() throws Exception {

        BoardFileLoader loader = new BoardFileLoader();

        Board board = loader.load("missing-board.txt");

        assertEquals(6, board.getRows());
        assertEquals(12, board.getColumns());
        assertEquals(3, board.getVehicles().size());
    }
    @Test
    void shouldLoadBoardFromFile() throws Exception {

        BoardFileLoader loader = new BoardFileLoader();

        Board board = loader.load("test-board.txt");

        assertEquals(6, board.getRows());
        assertEquals(12, board.getColumns());
        assertEquals(2, board.getVehicles().size());

        assertEquals("A", board.getVehicles().get(0).getId());
        assertEquals("B", board.getVehicles().get(1).getId());
    }
    @Test
    void shouldThrowExceptionWhenFileIsEmpty() {

        BoardFileLoader loader = new BoardFileLoader();

        assertThrows(
                IOException.class,
                () -> loader.load("empty-board.txt")
        );
    }
}
