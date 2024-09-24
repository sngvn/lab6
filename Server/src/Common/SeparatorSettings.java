package Common;

/**
 * Класс для обработки полей при парсинге в файл и хранения разделителей
 */
public class SeparatorSettings {
    /**
     * Разделитель между полем и значением
     */
    public static final String valueSeparator = ":";
    /**
     * Разделитель между полями
     */
    public static final String fieldSeparator = ",";

    /**
     * Метод для записи полей
     * @param field Поле
     * @param value Значение
     * @return Обработанное поле
     */
    public static String getFieldSeparator(String field, Object value) {
        return field + valueSeparator + value + fieldSeparator;
    }
}
