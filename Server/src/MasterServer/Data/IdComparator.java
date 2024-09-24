package MasterServer.Data;

import Common.DTO.Ticket;

import java.util.Comparator;

/**
 * Класс для сравнения двух объектов по id
 */
public class IdComparator implements Comparator<Ticket> {
    /**
     * Метод для сравнения
     * @param o1 первый объект сравнения
     * @param o2 второй объект сравнения
     * @return результат сравнения
     */
    @Override
    public int compare(Ticket o1, Ticket o2) {
        if (o1.getId() > o2.getId()){ // можно заменить на Long.compareTo(o)
            return 1;
        } else if (o1.getId() < o2.getId()) {
            return -1;
        } else{
            return 0;
        }
    }
}





