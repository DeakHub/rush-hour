package hu.nye.progtech.rushhour.model;

import java.util.Objects;

public class Vehicle {

    private final String id;
    private final int length;
    private final Orientation orientation;
    private final int row;
    private final int column;

    public Vehicle(String id, int length,
                   Orientation orientation, int row, int column) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Vehicle ID must not be blank."
            );
        }

        if (length <= 0) {
            throw new IllegalArgumentException(
                    "Vehicle length must be positive."
            );
        }

        if (row < 0 || column < 0) {
            throw new IllegalArgumentException(
                    "Vehicle position cannot be negative."
            );
        }

        this.id = id;
        this.length = length;
        this.orientation = Objects.requireNonNull(orientation);
        this.row = row;
        this.column = column;
    }

    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Vehicle move(int steps) {

        if (steps == 0) {
            throw new IllegalArgumentException(
                    "Movement cannot be zero."
            );
        }

        int newRow = row;
        int newColumn = column;

        if (orientation == Orientation.HORIZONTAL) {
            newColumn += steps;
        } else {
            newRow += steps;
        }

        return new Vehicle(
                id,
                length,
                orientation,
                newRow,
                newColumn
        );
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Vehicle vehicle)) {
            return false;
        }

        return length == vehicle.length
                && row == vehicle.row
                && column == vehicle.column
                && id.equals(vehicle.id)
                && orientation == vehicle.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, orientation, row, column);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", length=" + length +
                ", orientation=" + orientation +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}