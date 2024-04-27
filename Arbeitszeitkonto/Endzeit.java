import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Endzeit {

    private static final String UTIL_FILE = "Arbeitszeitkonto.txt";

    public static void main(String[] args) throws IOException {
        ende();
    }

    public static void ende() throws IOException {
        // holt Ort (z.B. Europe/Berlin)
        ZoneId ort = ZoneId.systemDefault();

        // holt Uhrzeit
        LocalTime uhrzeit = LocalTime.now();
        // formatiert Uhrzeit
        DateTimeFormatter zeitFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // speichert formatierte Uhrzeit
        String endZeitFormatiert = uhrzeit.atDate(LocalDate.now()).atZone(ort).format(zeitFormatter);

        // formatiert Zeitzone (z.B. MESZ)
        DateTimeFormatter zeitZonenFormatter = DateTimeFormatter.ofPattern("zzz");
        // speichert formatierte Zeitzone
        String zeitZoneFormatiert = uhrzeit.atDate(LocalDate.now()).atZone(ort).format(zeitZonenFormatter);

        endeSpeichern(endZeitFormatiert, zeitZoneFormatiert, ort);
    }

    public static void endeSpeichern(String uhrzeit, String zeitzone, ZoneId ort) throws IOException {
        zeitkontoExistiert();

        // Daten in die Datei schreiben
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UTIL_FILE, true))) {
            writer.write(uhrzeit + ", " + zeitzone + ", " + ort);
            writer.newLine();
            System.out.println("Schreibvorgang abgeschlossen. Hoffentlich war die Session erfolgreich!");
            System.out.println("\nArbeitsende um " + uhrzeit + ".");
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