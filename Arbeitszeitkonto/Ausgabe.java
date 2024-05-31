import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ausgabe {

    private static final String UTIL_FILE = "Arbeitszeitkonto.txt";

    public static void main(String[] args) {
        try {
            ausgeben();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ausgeben() throws IOException {
        FileReader reader = new FileReader(UTIL_FILE);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        LocalDateTime startZeit;
        LocalDateTime endZeit;
        long gesamtStunden = 0;
        long gesamtMinuten = 0;

        // Datensätze aus der Datei lesen und Zeitdifferenz berechnen
        System.out.println("\nAuflistung der Arbeitszeit für dieses Projekt. Falls die Start- oder Endzeit eines Datensatzes nicht eingetragen werden, wird \"Datensatz Unvollständig!\" ausgegeben.\n");
        while ((line = bufferedReader.readLine()) != null) {
            String[] daten = line.split(",");

            if (daten.length >= 5 && daten[0].equals("START")) { // Überprüfen, ob Datensatz gültig ist
                String startDatum = daten[1].trim();
                String startZeitString = daten[2].trim(); // Startzeit
                String endDatum = daten[3].trim();
                String endZeitString = daten[4].trim(); // Endzeit
                String zeitzone = daten[5].trim();
                String ort = daten[6].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                startZeit = LocalDateTime.parse(startDatum + " " + startZeitString, formatter);
                endZeit = LocalDateTime.parse(endDatum + " " + endZeitString, formatter);

                Duration differenz = Duration.between(startZeit, endZeit);

                // Zeitdifferenz in Stunden und Minuten umwandeln
                long stunden = differenz.toHours();
                long minuten = differenz.minusHours(stunden).toMinutes();

                // Gesamtzeitdifferenz aktualisieren
                gesamtStunden += stunden;
                gesamtMinuten += minuten;

                // Ergebnis ausgeben
                System.out.println(startDatum + ", " + startZeitString + ", " + endDatum + ", " + endZeitString + ", " + zeitzone + ", " + ort + " (" + String.format("%02d", stunden) + ":" + String.format("%02d", minuten) + ")");
            } else {
                System.out.println("Datensatz unvollständig!");
            }
        }

        if (gesamtMinuten >= 60) {
            gesamtStunden += gesamtMinuten / 60; // Stunden hinzufügen
            gesamtMinuten = gesamtMinuten % 60; // Restliche Minuten berechnen
        }

        System.out.println("\nGesamt: (" + String.format("%02d", gesamtStunden) + ":" + String.format("%02d", gesamtMinuten) + ")");
        bufferedReader.close();
    }
}

