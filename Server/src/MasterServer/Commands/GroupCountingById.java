package MasterServer.Commands;

import Common.DTO.BaseResponse;
import MasterServer.Data.Data;
import MasterServer.Data.GroupComparator;

/**
 * Класс команды, группирующей элементы в коллекции в зависимости от делимости их id на заданный делитель
 */
public class GroupCountingById extends Command {
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public GroupCountingById(Data data) {
        super("Сгрупировать элементы", true);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments) {
        try {
            int divisor = Integer.parseInt(arguments);
            var list = data.getList();
            list.sort(new GroupComparator(divisor));
            return new BaseResponse(true, "Элементы сгрупированы");
        }
        catch (Exception ex) { return new BaseResponse(false); }
    }
}
