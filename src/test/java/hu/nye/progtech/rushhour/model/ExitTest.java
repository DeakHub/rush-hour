
package hu.nye.progtech.rushhour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {

    @Test
    void shouldCreateExit() {
        Exit exit = new Exit(
                "A", 2, 11, Orientation.HORIZONTAL
        );

        assertEquals("A", exit.getVehicleId());
        assertEquals(2, exit.getRow());
        assertEquals(11, exit.getColumn());
        assertEquals(
                Orientation.HORIZONTAL,
                exit.getOrientation()
        );
    }

    @Test
    void shouldNotAllowBlankVehicleId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Exit(
                        "", 2, 11, Orientation.HORIZONTAL
                )
        );
    }

    @Test
    void shouldNotAllowNegativePosition() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Exit(
                        "A", -1, 11, Orientation.HORIZONTAL
                )
        );
    }

    @Test
    void shouldNotAllowNullOrientation() {
        assertThrows(
                NullPointerException.class,
                () -> new Exit("A", 2, 11, null)
        );
    }
}
