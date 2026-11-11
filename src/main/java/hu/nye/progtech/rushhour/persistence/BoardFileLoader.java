
package hu.nye.progtech.rushhour.persistence;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.model.Orientation;
import hu.nye.progtech.rushhour.model.Vehicle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BoardFileLoader {

    public Board load(String fileName) throws IOException {

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            return createDefaultBoard();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        inputStream,
                        StandardCharsets.UTF_8
                ))) {

            String dimensions = reader.readLine();

            if (dimensions == null) {
                throw new IOException("Board file is empty.");
            }

            String[] size = dimensions.split(",");

            if (size.length != 2) {
                throw new IOException("Invalid board dimensions.");
            }

            int rows = Integer.parseInt(size[0].trim());
            int columns = Integer.parseInt(size[1].trim());

            List<Vehicle> vehicles = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != 5) {
                    throw new IOException(
                            "Invalid vehicle data: " + line
                    );
                }

                String id = data[0].trim();
                int length = Integer.parseInt(data[1].trim());
                Orientation orientation =
                        Orientation.valueOf(data[2].trim().toUpperCase());

                int row = Integer.parseInt(data[3].trim());
                int column = Integer.parseInt(data[4].trim());

                Vehicle vehicle = new Vehicle(
                        id,
                        length,
                        orientation,
                        row,
                        column
                );

                vehicles.add(vehicle);
            }

            return new Board(rows, columns, vehicles);

        } catch (IllegalArgumentException exception) {
            throw new IOException(
                    "Invalid data in board file.",
                    exception
            );
        }
    }

    private Board createDefaultBoard() {

        List<Vehicle> vehicles = List.of(
                new Vehicle(
                        "A", 2, Orientation.HORIZONTAL, 2, 1
                ),
                new Vehicle(
                        "B", 3, Orientation.VERTICAL, 0, 4
                ),
                new Vehicle(
                        "C", 2, Orientation.HORIZONTAL, 4, 6
                )
        );

        return new Board(6, 12, vehicles);
    }

}