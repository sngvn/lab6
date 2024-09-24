package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;

/**
 * Класс команды, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command{
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public Show(Data data) {
        super("Вывести коллекцию", false);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments){
        try {
            StringBuilder builder = new StringBuilder();
            for (Ticket ticket : data.getList()){
                builder.append(ticket.toString()).append("\n");
            }
            return new BaseResponse(true, builder.toString(), false);
        }
        catch (Exception ex)
        {
            return new BaseResponse(false, "Нет коллекции");
        }
    }
}
