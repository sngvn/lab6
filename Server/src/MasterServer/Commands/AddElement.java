package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;
import MasterServer.MainServer;

import java.util.Scanner;

/**
 * Класс команды, добавляющей элемент в коллекцию
 */
public class AddElement extends Command implements IUseScanner {
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private final Data data;
    /**
     * Scanner для считывания параметров элемента
     */
    private Scanner scanner;
    /**
     * Информация о том, используется ли быстрое создание объекта
     */
    private boolean useFastCreate = false;

    private final MainServer mainServer;

    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public AddElement(MainServer mainServer, Data data) {
        super("Добавить новый элемент в коллекцию", false);
        this.data = data;
        this.mainServer = mainServer;
        scanner = new Scanner(System.in);
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments){
        try {
            Ticket ticket = CreateElement.getNewTicket(mainServer, scanner, useFastCreate);
            if(ticket == null)
                throw new Exception();
            data.addElement(ticket);
            return new BaseResponse(true, "Элемент добавлен");
        } catch (Exception e) {
            return new BaseResponse(false, "Не удалось добавить элемент");
        }
    }
    /**
     * Устанавливается быстрое/обыкновенное создание нового элемента, в зависимости от использования в скрипте/консоли
     * @param scanner объект типа Scanner
     * @param isSystemIn параметр true или false
     */
    @Override
    public void setScanner(Scanner scanner, boolean isSystemIn) {
        this.scanner = scanner;
        useFastCreate = !isSystemIn;
    }

}
