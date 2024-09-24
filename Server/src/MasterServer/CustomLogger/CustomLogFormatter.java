package MasterServer.CustomLogger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        return "[" + formattedTime + "]: " + record.getMessage() + System.lineSeparator();
    }
}
