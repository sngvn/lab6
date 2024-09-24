package MasterServer.CustomLogger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new CustomLogFormatter());

        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        return logger;
    }
}
