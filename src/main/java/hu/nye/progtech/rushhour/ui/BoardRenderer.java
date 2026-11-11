
package hu.nye.progtech.rushhour.ui;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.model.Vehicle;

import java.util.Arrays;
import java.util.Objects;

public class BoardRenderer {

    public String render(Board board) {

        Objects.requireNonNull(board);

        char[][] cells = new char[board.getRows()][board.getColumns()];

        // A tábla üres mezőinek jelölése ponttal.
        for (char[] row : cells) {
            Arrays.fill(row, '.');
        }

        // A járművek elhelyezése a megjelenítendő táblán.
        for (Vehicle vehicle : board.getVehicles()) {

            char symbol = vehicle.getId().charAt(0);

            for (int i = 0; i < vehicle.getLength(); i++) {

                int row = vehicle.getRow();
                int column = vehicle.getColumn();

                if (vehicle.getOrientation().name().equals("HORIZONTAL")) {
                    column += i;
                } else {
                    row += i;
                }

                cells[row][column] = symbol;
            }
        }

        StringBuilder result = new StringBuilder();

        // Oszlopok betűjelzése.
        result.append("   ");

        for (int column = 0; column < board.getColumns(); column++) {
            result.append((char) ('a' + column)).append(' ');
        }

        result.append(System.lineSeparator());

        // Sorok és mezők megjelenítése.
        for (int row = 0; row < board.getRows(); row++) {

            result.append(String.format("%2d | ", row + 1));

            for (int column = 0; column < board.getColumns(); column++) {
                result.append(cells[row][column]).append(' ');
            }

            result.append("|").append(System.lineSeparator());
        }

        return result.toString();
    }
}
