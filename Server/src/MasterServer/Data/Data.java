package MasterServer.Data;

import Common.DTO.Ticket;

import java.util.Date;
import java.util.LinkedList;

/**
 * Класс для хранения коллекции
 */
public class Data {
    /**
     * Коллекция с элементами
     */
    private LinkedList<Ticket> list;
    /**
     * Дата создания коллекции
     */
    private Date date;
    /**
     *
     */
    private String filePath;

    /**
     * Конструктор принимающий в качестве параметра коллекцию
     * @param list
     */
    public Data(LinkedList<Ticket> list){
        this.list = list;
        this.date = new Date();
    }

    /**
     * Метод для получения коллекции
     * @return коллекцию элементов
     */
    public LinkedList<Ticket> getList(){
        return this.list;
    }

    /**
     * Метод для получения информации о дате создания коллекции
     * @return дату создания коллекции
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Добавляет в коллекцию элемент
     * @param ticket элемент, который хотим добавить
     */
    public void addElement(Ticket ticket){
        this.list.add(ticket);
    }

    /**
     * Удаляет элемент из коллекции
     * @param ticket элемент, который хотим удалить
     */
    public void removeElement(Ticket ticket) {this.list.remove(ticket);}

    /**
     * Метод для задания коллекции
     * @param list коллекция, которую хотим сохранить
     */
    public void setList(LinkedList<Ticket> list){
        this.list = list;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
