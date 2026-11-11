
package hu.nye.progtech.rushhour;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void shouldStartAndExitGame() throws Exception {

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                    "TesztJatekos\n0\n"
                            .getBytes(StandardCharsets.UTF_8)
            ));

            System.setOut(new PrintStream(
                    output, true, StandardCharsets.UTF_8
            ));

            Main.main(new String[0]);

            String result = output.toString(
                    StandardCharsets.UTF_8
            );

            assertTrue(result.contains("RUSH HOUR"));
            assertTrue(result.contains("TesztJatekos"));
            assertTrue(result.contains("Kiléptél a játékból."));

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
