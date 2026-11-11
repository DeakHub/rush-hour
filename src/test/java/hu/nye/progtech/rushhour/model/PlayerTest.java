
package hu.nye.progtech.rushhour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldCreateHumanPlayer() {

        Player player = new Player(
                "Játékos 1",
                PlayerType.HUMAN,
                "A"
        );

        assertEquals("Játékos 1", player.getName());
        assertEquals(PlayerType.HUMAN, player.getType());
        assertEquals("A", player.getTargetVehicleId());
    }

    @Test
    void shouldCreateComputerPlayer() {

        Player player = new Player(
                "Computer",
                PlayerType.COMPUTER,
                "B"
        );

        assertEquals(PlayerType.COMPUTER, player.getType());
        assertEquals("B", player.getTargetVehicleId());
    }

    @Test
    void shouldNotAllowEmptyPlayerName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(
                        "",
                        PlayerType.HUMAN,
                        "A"
                )
        );
    }

    @Test
    void shouldNotAllowEmptyTargetVehicleId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(
                        "Player",
                        PlayerType.HUMAN,
                        ""
                )
        );
    }

    @Test
    void shouldNotAllowNullPlayerType() {

        assertThrows(
                NullPointerException.class,
                () -> new Player(
                        "Player",
                        null,
                        "A"
                )
        );
    }
}
