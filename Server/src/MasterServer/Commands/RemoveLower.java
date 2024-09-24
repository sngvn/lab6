package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;

import java.util.stream.Collectors;

/**
 * Класс команды, удаляющей из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends Command{
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public RemoveLower(Data data) {
        super("Удалить элементы меньше заданного(пишите имя элемента без пробелов)", true);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments) {
        try
        {
            Ticket startElement = data.getList().stream().filter(element -> element.getName().equals(arguments)).collect(Collectors.toList()).get(0);
            if(!data.getList().contains(startElement))
                return new BaseResponse(false, "Такого элементы не сушествует");

            data.getList().removeIf(element -> element.compareTo(startElement) < 0);
            return new BaseResponse(true, "Элементы успешно удалены");
        }
        catch (Exception ex){
            return new BaseResponse(false, "Не удалось удалить элементы");
        }
    }

}
