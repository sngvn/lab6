package Common.DTO;

import Common.DateFormatter;
import Common.SeparatorSettings;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Содержит информацию о главном элементе, который помещается в коллекцию
 */
public class Ticket implements Serializable, Comparable<Ticket>, IValidatable {

    @Serial
    private static final long serialVersionUID = 2137534596269284911L;

    /**
     * id билета. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private Long id;
    /**
     * Название билета. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Координаты места проведения. Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Дата генерации билета. Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
    private Date creationDate;
    /**
     * Цена билета. Значение поля должно быть больше 0
     */
    private float price;
    /**
     * Комментарий. Строка не может быть пустой, Поле не может быть null
     */
    private String comment;
    /**
     * Возможность вернуть билет.
     */
    private boolean refundable;
    /**
     * Тип билета. Поле может быть null
     */
    private TicketType type;
    /**
     * Место проведения. Поле может быть null
     */
    private Venue venue;
    /**
     * Устанавливает id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Устанавливает имя
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Устанавливает комментарий
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Устанавливает координаты
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /**
     * Устанавливает дату генерации
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Устанавливает цену
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }
    /**
     * Устанавливает возможность возврата
     * @param refundable
     */
    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }
    /**
     * Устанавливает тип билета
     * @param type
     */
    public void setType(TicketType type) {
        this.type = type;
    }
    /**
     * Устанавливает место проведения
     * @param venue
     */
    public void setVenue(Venue venue){
        this.venue = venue;;
    }
    /**
     * Возвращает тип билета
     */
    public TicketType getType(){
        return this.type;
    }

    public Venue getVenue() { return this.venue;}
    /**
     * Возвращает цену билета
     */
    public float getPrice(){
        return this.price;
    }
    /**
     * Возвращает id билета
     */
    public Long getId(){
        return this.id;
    }
    /**
     * Возвращает название билета
     */
    public String getName(){ return this.name.replaceAll(" ", ""); }
    /**
     * Используется для записи в файл в строковом представлении
     * @return строковую информацию об объекте с разделителями между полями и значениями
     */
    @Override
    public String toString() {
        String venueText = this.venue == null ? SeparatorSettings.getFieldSeparator("venue", "Null") : venue.toString();
        return  SeparatorSettings.getFieldSeparator("id", id) +
                SeparatorSettings.getFieldSeparator("name", name) +
                coordinates.toString() +
                SeparatorSettings.getFieldSeparator("creationDate", DateFormatter.getDateString(creationDate)) +
                SeparatorSettings.getFieldSeparator("price", price)+
                SeparatorSettings.getFieldSeparator("comment", comment) +
                SeparatorSettings.getFieldSeparator("refundable", refundable) +
                SeparatorSettings.getFieldSeparator("type", type) +
                venueText;
    }

    /**
     * Сравнивает этот билет и передаваемый по цене
     * @param o объект сравнения
     * @return результат сравнения
     */
    @Override
    public int compareTo(Ticket o) {
        return Float.compare(this.price, o.getPrice());
    }
    /**
     * Метод для проверки валидности поля
     * @return true или false в завимисоти от прохождения валидации
     */
    @Override
    public boolean validate() {
        if (name == null || name.trim().isEmpty())
            return false;
        if(!coordinates.validate())
            return false;
        if((String.valueOf(price).charAt(0) == '0') || (price <= 0))
            return false;
        if(comment == null || comment.trim().isEmpty())
            return false;
        return true;
    }
}
