import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Startzeit {

    private static final String UTIL_FILE = "Arbeitszeitkonto.txt";

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {
        // holt Datum
        LocalDate datum = LocalDate.now();
        // formatiert Datum
        DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        // speichert formatiertes Datum
        String datumFormatiert = datum.format(datumFormatter);

        // holt Uhrzeit
        LocalTime uhrzeit = LocalTime.now();
        // formatiert Uhrzeit
        DateTimeFormatter zeitFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // speichert formatierte Uhrzeit
        String startZeitFormatiert = uhrzeit.atDate(LocalDate.now()).format(zeitFormatter);

        startSpeichern(datumFormatiert, startZeitFormatiert);
    }

    public static void startSpeichern(String datum, String uhrzeit) throws IOException {
        zeitkontoExistiert();

        // Daten in die Datei schreiben
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UTIL_FILE, true))) {
            writer.write("START," + datum + ", " + uhrzeit + ", ");
            System.out.println("Schreibvorgang abgeschlossen. Du bist Startklar!");
            System.out.println("\nArbeitsbeginn am " + datum + " um " + uhrzeit + ".");
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void zeitkontoExistiert() throws IOException {
        File file = new File(UTIL_FILE);
        if (file.exists()) {
            System.out.println("\nArbeitszeitkonto existiert. Daten werden geschrieben...");
        } else {
            System.out.println("\nKein Zeitkonto erkannt. Erstelle neues Arbeitszeitkonto...");
            zeitkontoErstellen();
        }
    }

    public static void zeitkontoErstellen() throws IOException {
        File utilDatei = new File(UTIL_FILE);
        if (utilDatei.createNewFile()) {
            System.out.println("Konto angelegt: (" + utilDatei.getName() + ")    Daten werden geschrieben...");
        } else {
            System.out.println("Ein fehler ist aufgetreten. Versuche Konto erneut anzulegen...");
            zeitkontoErstellen();
        }
    }
}