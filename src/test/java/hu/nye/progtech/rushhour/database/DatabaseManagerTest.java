
package hu.nye.progtech.rushhour.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DatabaseManagerTest {

    @Test
    void shouldInitializeDatabaseWithoutException() {

        DatabaseManager databaseManager =
                new DatabaseManager("jdbc:sqlite:target/test-highscores.db");

        assertDoesNotThrow(
                databaseManager::initializeDatabase
        );
    }

    @Test
    void shouldRecordPlayerWin() throws SQLException {

        DatabaseManager databaseManager =
                new DatabaseManager("jdbc:sqlite:target/test-highscores.db");

        databaseManager.initializeDatabase();

        databaseManager.recordWin("Teszt Játékos");
        databaseManager.recordWin("Teszt Játékos");

        assertDoesNotThrow(
                databaseManager::printHighScores
        );
    }

}
