
package hu.nye.progtech.rushhour;

import hu.nye.progtech.rushhour.model.Board;
import hu.nye.progtech.rushhour.persistence.BoardFileLoader;
import hu.nye.progtech.rushhour.ui.BoardRenderer;
import hu.nye.progtech.rushhour.model.Exit;
import hu.nye.progtech.rushhour.model.Orientation;
import hu.nye.progtech.rushhour.model.Player;
import hu.nye.progtech.rushhour.model.PlayerType;
import hu.nye.progtech.rushhour.service.Game;

//SQLite adatbázis import
import hu.nye.progtech.rushhour.database.DatabaseManager;
import java.sql.SQLException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("          RUSH HOUR");
        System.out.println("==============================");

        try {
            BoardFileLoader loader = new BoardFileLoader();

            Board board = loader.load("board.txt");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Add meg a neved: ");
            String playerName = scanner.nextLine();

            Player humanPlayer = new Player(
                    playerName,
                    PlayerType.HUMAN,
                    "A"
            );

            Player computerPlayer = new Player(
                    "Számítógép",
                    PlayerType.COMPUTER,
                    "B"
            );

            Exit humanExit = new Exit(
                    "A", 0, 11, Orientation.HORIZONTAL
            );

            Exit computerExit = new Exit(
                    "B", 4, 0, Orientation.HORIZONTAL
            );

            Game game = new Game(
                    board,
                    humanPlayer,
                    computerPlayer,
                    humanExit,
                    computerExit
            );

            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.initializeDatabase();

            BoardRenderer renderer = new BoardRenderer();

            System.out.println();
            System.out.println("Játékos: " + game.getHumanPlayer().getName());
            System.out.println("Ellenfél: " + game.getComputerPlayer().getName());
            System.out.println();
            System.out.println("Kezdőpálya:");
            System.out.println(renderer.render(board));


            while (!game.isFinished()) {

                System.out.println();
                System.out.println("Te következel!");
                System.out.print("Melyik járművet szeretnéd mozgatni? (0 = kilépés): ");

                String vehicleId = scanner.nextLine();

                if (vehicleId.equals("0")) {
                    System.out.println("Kiléptél a játékból.");
                    break;
                }

                System.out.print("Hány mezővel szeretnéd mozgatni? ");

                int steps;

                try {
                    steps = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException exception) {
                    System.out.println("Hibás lépésszám! Próbáld újra.");
                    continue;
                }

                try {
                    game.moveVehicle(vehicleId, steps);

                    System.out.println();
                    System.out.println("Sikeres lépés!");
                    System.out.println(renderer.render(game.getBoard()));

                    if (game.isFinished()) {
                        break;
                    }

                    System.out.println();
                    System.out.println("A számítógép következik...");

                    game.computerMove();

                    System.out.println("A számítógép lépett:");
                    System.out.println(renderer.render(game.getBoard()));

                } catch (IllegalArgumentException exception) {
                    System.out.println("Érvénytelen lépés: "
                            + exception.getMessage());
                } catch (IllegalStateException exception) {
                    System.out.println(exception.getMessage());
                    break;
                }
            }


            if (game.getWinner() != null) {
                System.out.println();
                System.out.println("A játék győztese: "
                        + game.getWinner().getName());

                databaseManager.recordWin(game.getWinner().getName());

                System.out.println();
                databaseManager.printHighScores();
            }


        } catch (IOException exception) {

            System.out.println(
                    "Hiba a pálya betöltésekor: "
                            + exception.getMessage()
            );
        }
        catch (SQLException exception) {
            System.out.println(
                    "Adatbázis hiba: " + exception.getMessage()
            );
        }
    }
}
