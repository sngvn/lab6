package MasterServer.Commands;
import java.util.Scanner;

/**
 * Интерфейс, который реализуют классы команд, использующие класс Scanner
 */
public interface IUseScanner
{
    /**
     * Устанавливает информацию о том, откуда происходит считывание с помощью Scanner
     * @param scanner объект типа Scanner
     * @param isSystemIn параметр true или false
     */
    void setScanner(Scanner scanner, boolean isSystemIn);

}
