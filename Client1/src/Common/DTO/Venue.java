package Common.DTO;

import Common.SeparatorSettings;

import java.io.Serial;
import java.io.Serializable;

/**
 * Содержит информацию о месте проведения мероприятия
 */
public class Venue implements Serializable, IValidatable {
    @Serial
    private static final long serialVersionUID = 2596599976513897126L;
    /**
     * Уникальный id мероприятия. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private Integer id;
    /**
     * Название мероприятия. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Вместимость мероприятия. Поле может быть null, Значение поля должно быть больше 0
     */
    private Integer capacity;
    /**
     * Тип мероприятия. Поле не может быть null
     */
    private VenueType type;

    /**
     * Устанавливает значение id
     * @param id
     */
    public void setId(Integer id){
        this.id = id;
    }
    /**
     * Устанавливает название
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Устанавливает значение вместимости
     * @param capacity
     */
    public void setCapacity(Integer capacity){
        this.capacity = capacity;
    }
    /**
     * Устанавливает тип
     * @param type
     */
    public void setType(VenueType type){
        this.type = type;
    }
    /**
     *
     * @return информацию об объекте
     */
    @Override
    public String toString() {
        return SeparatorSettings.getFieldSeparator("venueId", id) +
                SeparatorSettings.getFieldSeparator("venueName", name) +
                SeparatorSettings.getFieldSeparator("venueCapacity", capacity) +
                SeparatorSettings.getFieldSeparator("venueType", type);
    }
    /**
     * Метод для проверки валидности поля
     * @return true или false в завимисоти от прохождения валидации
     */
    @Override
    public boolean validate() {
        if (name == null || name.trim().isEmpty())
            return  false;
        if ((String.valueOf(capacity).charAt(0) == '0') || (capacity <= 0))
            return false;
        return true;
    }
}
