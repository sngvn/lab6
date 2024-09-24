package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;

import java.util.stream.Collectors;

/**
 * Класс команды, удаляющей элемент по заданному id
 */
public class RemoveById extends Command{
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public RemoveById(Data data) {
        super("Удалить элемент по id", true);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param argument
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String argument)
    {
        try {
            int id = Integer.parseInt(argument);
            Ticket ticket = data.getList().stream().filter(element -> element.getId() == id).collect(Collectors.toList()).get(0);
            data.removeElement(ticket);
            BaseResponse baseResponse = new BaseResponse(true, "Элемент успешно удален");
            return baseResponse;
        }
        catch (Exception exception){
            BaseResponse baseResponse = new BaseResponse(false, "Введен некорректный id");
            return baseResponse;
        }
    }
}
