package MasterServer.Commands;

import Common.DTO.BaseResponse;
import Common.DTO.Ticket;
import MasterServer.Data.Data;

import java.util.Collections;
import java.util.LinkedList;
/**
 * Класс команды, которая выводит элементы коллекции в порядке убывания по цене
 */
public class PrintFieldDescendingPrice extends Command{
    /**
     * Элемент типа Server.Data для доступа к коллекции
     */
    private Data data;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект Server.Data в качестве аргумента
     * @param data
     */
    public PrintFieldDescendingPrice(Data data) {
        super( "Вывести отсортированные элементы по цене",false);
        this.data = data;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments){
        LinkedList<Ticket> list = data.getList();
        Collections.sort(list);
        Collections.reverse(list);
        for (Ticket ticket : list){
            System.out.println(ticket.getPrice());
        }
        BaseResponse baseResponse = new BaseResponse(true, "Элементы отсортированы по цене в обратном порядке");
        return baseResponse;
    }
}
