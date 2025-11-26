import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    private static final String LOG_FILE = "bobo.txt"; 
    // Se quiser mudar o nome do arquivo de log, altere aqui "bobo.txt"--> "example.txt" (É uma forma de alterar o arquivo de saída em diferentes execuções, para se organizar)

    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public static void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(LocalTime.now().format(FORMATTER) + " - " + message + System.lineSeparator()); // Escreve a mensagem no arquivo de log com uma nova linha 
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        }
    }
}
