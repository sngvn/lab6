package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;

import java.util.LinkedList;

/**
 * Класс команды, очищающей коллекцию
 */
public class Clear extends Command {
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public Clear(Data data){
        super("Очистить коллекцию", false);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments){
        if(data.getList().isEmpty())
            return new BaseResponse(false, "Коллекция и так пустая");
        data.setList(new LinkedList<Ticket>());
        return new BaseResponse(true, "Коллекция очищена");
    }
}
