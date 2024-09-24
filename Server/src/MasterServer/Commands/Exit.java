package MasterServer.Commands;

import Common.DTO.BaseResponse;

/**
 * Класс команды, завершающей программу (без сохранения в файл)
 */
public class Exit extends Command{
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса
     */
    public Exit() {
        super("Закрыть приложение", false);
        setGeneral(true);
    }

    @Override
    public BaseResponse execute(String arguments){
        BaseResponse response = new BaseResponse(true);
        response.setShutDown();
        return response;
    }
}
