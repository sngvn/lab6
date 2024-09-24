package Common;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Класс для форматирования даты
 */
public class DateFormatter {
    /**
     * Формат даты
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Форматирует дату по заданному паттерну
     * @param date передаваемая дата
     * @return дату в строковом представлении
     */
    public static String getDateString(Date date) { return dateFormat.format(date); }
    /**
     * Переводит дату обратно из строкового представления
     * @param dateString дата в строковом представлении
     * @return
     */
    public static Date getDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
