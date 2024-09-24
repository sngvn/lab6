package Common.DTO;

import Common.SeparatorSettings;

import java.io.Serial;
import java.io.Serializable;

/**
 * Содержит информацию о координатах, указанных в билете
 */
public class Coordinates implements Serializable, IValidatable {

    @Serial
    private static final long serialVersionUID = 3899875694901481520L;
    /**
     * Координата x. Максимальное значение поля: 528
     */
    private double x;
    /**
     * Координата y. Значение поля должно быть больше -516
     */
    private double y;

    /**
     * Конструктор принимающий в себя две координаты
     * @param x Координата x
     * @param y Координата y
     */
    public Coordinates(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return информацию об объекте
     */
    @Override
    public String toString(){
        return SeparatorSettings.getFieldSeparator("x", x) + SeparatorSettings.getFieldSeparator("y", y);
    }

    /**
     * Метод для проверки валидности поля
     * @return true или false в завимисоти от прохождения валидации
     */
    @Override
    public boolean validate() {
        return !(this.x > 528) && !(this.y < -517);
    }
}
