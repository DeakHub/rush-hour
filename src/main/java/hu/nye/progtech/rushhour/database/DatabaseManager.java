
package hu.nye.progtech.rushhour.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final String databaseUrl;

    public DatabaseManager() {
        this("jdbc:sqlite:rushhour.db");
    }

    public DatabaseManager(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void initializeDatabase() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS high_scores (
                    player_name TEXT PRIMARY KEY,
                    wins INTEGER NOT NULL DEFAULT 0
                )
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }

    public void recordWin(String playerName) throws SQLException {

        String sql = """
            INSERT INTO high_scores (player_name, wins)
            VALUES (?, 1)
            ON CONFLICT(player_name)
            DO UPDATE SET wins = wins + 1
            """;

        try (Connection connection = connect();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, playerName);
            statement.executeUpdate();
        }
    }

    public void printHighScores() throws SQLException {

        String sql = """
            SELECT player_name, wins
            FROM high_scores
            ORDER BY wins DESC, player_name ASC
            """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             java.sql.ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println();
            System.out.println("========= TOPLISTA =========");

            int position = 1;

            while (resultSet.next()) {
                System.out.println(
                        position + ". "
                                + resultSet.getString("player_name")
                                + " - "
                                + resultSet.getInt("wins")
                                + " győzelem"
                );

                position++;
            }

            if (position == 1) {
                System.out.println("Még nincs rögzített eredmény.");
            }

            System.out.println("============================");
        }
    }


}
