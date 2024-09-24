package MasterServer.Data;

import Common.DTO.Ticket;

import java.util.Comparator;

/**
 * Класс для сортировки по делителю
 */
public class GroupComparator implements Comparator<Ticket> {
    /**
     * делитель
     */
    private int divisor;

    /**
     * Конструктор принимающий делитель
     * @param divisor
     */
    public GroupComparator(int divisor){ this.divisor = divisor; }

    /**
     * Метод сравнения
     * В случае если оба элемента делятся без остатка, то записываются в порядке возрастания
     * @param o1 первый объект сравнения
     * @param o2 второй объект сравнения
     * @return результат сравнения
     */
    @Override
    public int compare(Ticket o1, Ticket o2) {
        long firstId =  o1.getId();
        long secondId =  o2.getId();
            if(firstId % divisor == 0 && secondId % divisor != 0)
                return -1;
            else if (firstId % divisor != 0 && secondId % divisor == 0)
                return 1;
            else if (firstId % divisor == 0 && secondId % divisor == 0)
                return firstId > secondId ? -1 : 1;
            return 0;
    }
}
