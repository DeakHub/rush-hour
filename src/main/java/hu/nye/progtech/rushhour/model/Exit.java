
package hu.nye.progtech.rushhour.model;

import java.util.Objects;

public class Exit {

    private final String vehicleId;
    private final int row;
    private final int column;
    private final Orientation orientation;

    public Exit(
            String vehicleId,
            int row,
            int column,
            Orientation orientation
    ) {
        if (vehicleId == null || vehicleId.isBlank()) {
            throw new IllegalArgumentException(
                    "Vehicle ID must not be blank."
            );
        }

        if (row < 0 || column < 0) {
            throw new IllegalArgumentException(
                    "Exit position cannot be negative."
            );
        }

        this.vehicleId = vehicleId;
        this.row = row;
        this.column = column;
        this.orientation = Objects.requireNonNull(orientation);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
