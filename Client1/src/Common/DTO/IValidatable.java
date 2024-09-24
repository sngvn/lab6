package Common.DTO;

/**
 * Интерфейс, который реализуют классы со значениями, требующими валидации
 */
public interface IValidatable
{
     /**
      * Метод, возвращающий информацию о прохождении валидации
      * @return
      */
     boolean validate();
}
