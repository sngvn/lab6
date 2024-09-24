package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;
import MasterServer.MainServer;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Класс команды, обновляющей значение элемента коллекции, id которого равен заданному
 */
public class UpdateId extends Command implements IUseScanner{
    /**
     * Элемент типа Server.Data для перезаписи в коллекцию элемента
     */
    private final Data data;
    /**
     * Нужен для корректной работы с логикой команды executeScript
     */
    private Scanner scanner;
    /**
     * Информация о том, будет ли выполнятся быстрое создание нового элемента
     */
    private boolean useFastCreate = false;

    private final MainServer mainServer;

    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public UpdateId(MainServer mainServer, Data data) {
        super( "Обновить элемент по индексу", true);
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
    public BaseResponse execute(String arguments) {
        try{
            int id = Integer.parseInt(arguments);
            Ticket oldTicket = data.getList().stream().filter(element -> element.getId() == id).collect(Collectors.toList()).get(0);
            data.removeElement(oldTicket);
            Ticket ticket = CreateElement.updateTicket(mainServer, scanner, id, useFastCreate);
            if(ticket == null)
                return new BaseResponse(false, "Не удалось добавить элемент");
            data.addElement(ticket);
            return new BaseResponse(true, "Элемент обновлен успешно");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new BaseResponse(false, "Не удалось обновить элемент");
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
