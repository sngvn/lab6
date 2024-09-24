package MasterServer.Commands;

import Common.DTO.BaseResponse;
import MasterServer.Data.CommandManager;

import java.util.Map;

/**
 * Класс команды, которая выводит справку по доступным командам
 */
public class Help extends Command {
    /**
     * Объект типа commandManager для доступа к информации о командах
     */
    private  CommandManager commandManager;
    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект CommandManager в качестве аргумента
     * @param commandManager
     */
    public Help(CommandManager commandManager) {
        super("Вывести информацию о командах", false);
        this.commandManager = commandManager;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String arguments) {
        StringBuilder commandInfo = new StringBuilder();
        for (Map.Entry<String, Command> pair: commandManager.getCommands().entrySet()){
            if(!pair.getValue().getOnlyServer())
                commandInfo.append(pair.getKey()).append(" - ").append(pair.getValue().getDescription()).append("\n");
        }
        BaseResponse baseResponse = new BaseResponse(true, commandInfo.toString(), false);
        return baseResponse;
    }
}
