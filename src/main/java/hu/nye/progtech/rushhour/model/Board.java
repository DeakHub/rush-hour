
package hu.nye.progtech.rushhour.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.ArrayList;

public class Board {

    private final int rows;
    private final int columns;
    private final List<Vehicle> vehicles;

    public Board(int rows, int columns, List<Vehicle> vehicles) {

        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                    "Board dimensions must be positive."
            );
        }

        this.rows = rows;
        this.columns = columns;
        this.vehicles = List.copyOf(
                Objects.requireNonNull(vehicles)
        );

        validateVehicles();
    }

    private void validateVehicles() {

        Set<String> vehicleIds = new HashSet<>();

        for (Vehicle vehicle : vehicles) {

            if (!vehicleIds.add(vehicle.getId())) {
                throw new IllegalArgumentException(
                        "Vehicle IDs must be unique."
                );
            }

            if (!isVehicleInsideBoard(vehicle)) {
                throw new IllegalArgumentException(
                        "Vehicle is outside the board."
                );
            }
        }

        for (int i = 0; i < vehicles.size(); i++) {

            for (int j = i + 1; j < vehicles.size(); j++) {

                if (vehiclesOverlap(
                        vehicles.get(i),
                        vehicles.get(j)
                )) {
                    throw new IllegalArgumentException(
                            "Vehicles cannot overlap."
                    );
                }
            }
        }
    }

    private boolean isVehicleInsideBoard(Vehicle vehicle) {

        if (vehicle.getOrientation() == Orientation.HORIZONTAL) {

            return vehicle.getRow() < rows
                    && vehicle.getColumn() + vehicle.getLength()
                    <= columns;
        }

        return vehicle.getColumn() < columns
                && vehicle.getRow() + vehicle.getLength() <= rows;
    }

    private boolean vehiclesOverlap(
            Vehicle first,
            Vehicle second
    ) {

        for (int i = 0; i < first.getLength(); i++) {

            int firstRow = first.getRow();
            int firstColumn = first.getColumn();

            if (first.getOrientation() == Orientation.HORIZONTAL) {
                firstColumn += i;
            } else {
                firstRow += i;
            }

            for (int j = 0; j < second.getLength(); j++) {

                int secondRow = second.getRow();
                int secondColumn = second.getColumn();

                if (second.getOrientation() == Orientation.HORIZONTAL) {
                    secondColumn += j;
                } else {
                    secondRow += j;
                }

                if (firstRow == secondRow
                        && firstColumn == secondColumn) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCellOccupied(int row, int column) {

        if (row < 0 || row >= rows
                || column < 0 || column >= columns) {
            throw new IllegalArgumentException(
                    "Cell is outside the board."
            );
        }

        for (Vehicle vehicle : vehicles) {

            if (occupiesCell(vehicle, row, column)) {
                return true;
            }
        }

        return false;
    }

    private boolean occupiesCell(
            Vehicle vehicle,
            int row,
            int column
    ) {

        for (int i = 0; i < vehicle.getLength(); i++) {

            int vehicleRow = vehicle.getRow();
            int vehicleColumn = vehicle.getColumn();

            if (vehicle.getOrientation() == Orientation.HORIZONTAL) {
                vehicleColumn += i;
            } else {
                vehicleRow += i;
            }

            if (vehicleRow == row && vehicleColumn == column) {
                return true;
            }
        }

        return false;
    }

    public Board moveVehicle(String vehicleId, int steps) {

        if (steps == 0) {
            throw new IllegalArgumentException(
                    "Movement cannot be zero."
            );
        }

        Vehicle selectedVehicle = null;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle not found: " + vehicleId
            );
        }

        int direction = Integer.signum(steps);

        for (int distance = 1; distance <= Math.abs(steps); distance++) {

            Vehicle intermediateVehicle =
                    selectedVehicle.move(direction * distance);

            if (!isVehicleInsideBoard(intermediateVehicle)) {
                throw new IllegalArgumentException(
                        "Vehicle cannot leave the board."
                );
            }

            for (Vehicle otherVehicle : vehicles) {

                if (!otherVehicle.getId().equals(vehicleId)
                        && vehiclesOverlap(
                        intermediateVehicle,
                        otherVehicle
                )) {

                    throw new IllegalArgumentException(
                            "Movement is blocked by another vehicle."
                    );
                }
            }
        }

        List<Vehicle> movedVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getId().equals(vehicleId)) {
                movedVehicles.add(selectedVehicle.move(steps));
            } else {
                movedVehicles.add(vehicle);
            }
        }

        return new Board(rows, columns, movedVehicles);
    }

//Ha a megfelelő céljármű eléri a saját kijáratát, eltávolítja a tábláról.
//Minden más esetben a korábbi mozgási szabályok érvényesek.
    public Board moveVehicle(String vehicleId, int steps, Exit exit) {

        Objects.requireNonNull(exit);

        if (steps == 0) {
            throw new IllegalArgumentException(
                    "Movement cannot be zero."
            );
        }

        Vehicle selectedVehicle = null;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle not found: " + vehicleId
            );
        }

        if (!exit.getVehicleId().equals(vehicleId)) {
            throw new IllegalArgumentException(
                    "This vehicle does not belong to this exit."
            );
        }

        int direction = Integer.signum(steps);

        for (int distance = 1; distance <= Math.abs(steps); distance++) {

            int newRow = selectedVehicle.getRow();
            int newColumn = selectedVehicle.getColumn();

            if (selectedVehicle.getOrientation() == Orientation.HORIZONTAL) {
                newColumn += direction * distance;
            } else {
                newRow += direction * distance;
            }

            boolean outsideBoard =
                    newRow < 0
                            || newColumn < 0
                            || (selectedVehicle.getOrientation() == Orientation.HORIZONTAL
                            && newColumn + selectedVehicle.getLength() > columns)
                            || (selectedVehicle.getOrientation() == Orientation.VERTICAL
                            && newRow + selectedVehicle.getLength() > rows);

            if (outsideBoard) {

                boolean canExit =
                        selectedVehicle.getOrientation() == exit.getOrientation()
                                && (
                                // Bal oldali kijárat
                                selectedVehicle.getOrientation() == Orientation.HORIZONTAL
                                        && direction < 0
                                        && exit.getColumn() == 0
                                        && newRow == exit.getRow()
                                        && newColumn == -1

                                        ||

                                        // Jobb oldali kijárat
                                        selectedVehicle.getOrientation() == Orientation.HORIZONTAL
                                                && direction > 0
                                                && exit.getColumn() == columns - 1
                                                && newRow == exit.getRow()
                                                && newColumn + selectedVehicle.getLength() == columns + 1

                                        ||

                                        // Felső kijárat
                                        selectedVehicle.getOrientation() == Orientation.VERTICAL
                                                && direction < 0
                                                && exit.getRow() == 0
                                                && newColumn == exit.getColumn()
                                                && newRow == -1

                                        ||

                                        // Alsó kijárat
                                        selectedVehicle.getOrientation() == Orientation.VERTICAL
                                                && direction > 0
                                                && exit.getRow() == rows - 1
                                                && newColumn == exit.getColumn()
                                                && newRow + selectedVehicle.getLength() == rows + 1
                        );

                if (canExit) {

                    List<Vehicle> remainingVehicles = new ArrayList<>();

                    for (Vehicle vehicle : vehicles) {
                        if (!vehicle.getId().equals(vehicleId)) {
                            remainingVehicles.add(vehicle);
                        }
                    }

                    return new Board(rows, columns, remainingVehicles);
                }

                throw new IllegalArgumentException(
                        "Vehicle cannot leave the board here."
                );
            }

            Vehicle movedVehicle = selectedVehicle.move(direction * distance);

            for (Vehicle otherVehicle : vehicles) {
                if (!otherVehicle.getId().equals(vehicleId)
                        && vehiclesOverlap(movedVehicle, otherVehicle)) {
                    throw new IllegalArgumentException(
                            "Movement is blocked by another vehicle."
                    );
                }
            }
        }
        List<Vehicle> movedVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                movedVehicles.add(selectedVehicle.move(steps));
            } else {
                movedVehicles.add(vehicle);
            }
        }

        return new Board(rows, columns, movedVehicles);
    }


    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
