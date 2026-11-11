# Rush Hour

A Rush Hour egy Java nyelven készült, parancssoros logikai játék. A játékos célja, hogy a saját járművét a megfelelő kijáraton keresztül kivezesse a pályáról, miközben a többi jármű akadályozhatja a mozgását.

## Funkciók

- 6x12-es játéktábla
- Pálya betöltése külső TXT fájlból
- Alapértelmezett pálya használata, ha a fájl nem tölthető be
- Emberi és számítógép elleni játékmenet
- Járművek mozgatása a saját tengelyük mentén
- Véletlenszerű számítógépes lépések
- Győzelem ellenőrzése
- Konzolos játéktábla-megjelenítés
- SQLite-alapú eredménytábla és győzelmek mentése

## Technológiák

- Java 21
- Maven
- JUnit 5
- Mockito
- SQLite
- Logback
- JaCoCo
- Checkstyle

## Futtatás

A projekt lefordítása és tesztelése:

```bash
mvn clean install
```

A futtatható JAR elindítása:

```bash
java -jar target/rush-hour-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Játékmenet

1. A játékos megadja a nevét.
2. A játékos kiválasztja a mozgatni kívánt járművet.
3. Megadja, hány mezővel szeretné mozgatni.
4. A számítógép végrehajtja a saját lépését.
5. A játék addig folytatódik, amíg valamelyik játékos ki nem vezeti a céljárművét.

A `0` megadásával a játékból ki lehet lépni.

## Adatbázis

A játék SQLite adatbázisban tárolja a játékosok nevét és győzelmeik számát. A toplista a játék végén jelenik meg.

## Fejlesztői környezet

- JDK: Eclipse Temurin 21
- IDE: IntelliJ IDEA
- Build rendszer: Maven