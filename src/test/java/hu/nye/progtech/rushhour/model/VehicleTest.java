
package hu.nye.progtech.rushhour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void shouldMoveHorizontalVehicleToTheRight() {

        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 2
        );

        Vehicle moved = vehicle.move(2);

        assertEquals(4, moved.getColumn());
        assertEquals(1, moved.getRow());

        // Az eredeti objektum nem változott.
        assertEquals(2, vehicle.getColumn());
    }

    @Test
    void shouldMoveVerticalVehicleDown() {

        Vehicle vehicle = new Vehicle(
                "B", 3, Orientation.VERTICAL, 1, 2
        );

        Vehicle moved = vehicle.move(2);

        assertEquals(3, moved.getRow());
        assertEquals(2, moved.getColumn());
    }

    @Test
    void shouldNotAllowZeroMovement() {

        Vehicle vehicle = new Vehicle(
                "A", 2, Orientation.HORIZONTAL, 1, 2
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.move(0)
        );
    }

    @Test
    void shouldNotAllowNegativePosition() {

        Vehicle vehicle = new Vehicle(
                "B", 2, Orientation.VERTICAL, 0, 2
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.move(-1)
        );
    }

    @Test
    void shouldNotAllowInvalidLength() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Vehicle(
                        "A", 0, Orientation.HORIZONTAL, 0, 0
                )
        );
    }
}
