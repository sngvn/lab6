package MasterServer.Data;

import Common.DTO.BaseResponse;
import MasterServer.Commands.*;
import MasterServer.MainServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс для хранения и вызова команд
 */
public class CommandManager {
    /**
     * Словарь для хранения команд, где ключ - строковая запись команды, значение - объект типа этой команды
     */
    private HashMap<String, Command> commands = new HashMap<>();
    /**
     * Элемент типа Server.Data
     */
    private Data data;
    /**
     * Информация о том, выполнилась ли команда
     */
    private boolean executeСurrentСommand = false;
    /**
     * Элемент BaseResponse (информация о выполнении команды и описание)
     */
    private BaseResponse commandResponse;
    /**
     * Массив для хранения информации об использованных скриптах для избежания рекурсии
     */
    private List<String> executeCommnads = new ArrayList<>();

    /**
     * Конструктор принимает в качестве параметра элемент Server.Data, а также записывает в словарь существующие команды
     *
     * @param data
     */
    public CommandManager(MainServer server, Data data){
        this.data = data;

        commands.put("help", new Help(this));
        commands.put("info", new Info(data));
        commands.put("show", new Show(data));
        commands.put("add", new AddElement(server,data));
        commands.put("clear", new Clear(data));
        commands.put("save", new Save(data));
        commands.put("exit", new Exit());
        commands.put("updateId", new UpdateId(server, data));
        commands.put("addIfMax", new AddIfMax(server, data));
        commands.put("addIfMin", new AddIfMin(server, data));
        commands.put("removeById", new RemoveById(data));
        commands.put("removeLower", new RemoveLower(data));
        commands.put("executeScript", new ExecuteScript(this));
        commands.put("printAscending", new PrintAscending(data));
        commands.put("groupCountingById", new GroupCountingById(data));
        commands.put("printFieldDescendingPrice", new PrintFieldDescendingPrice(data));
    }

    /**
     * Метод возвращает информацию о выполнении команды
     * @return
     */
    public boolean getCanReadyCommands(){
        return executeСurrentСommand;
    }

    /**
     * Возвращает команду по ключу
     *
     * @param commandName ключ-название команды
     * @return элемент команды
     */
    public Command getCommand(String commandName){
        if(commands.containsKey(commandName))
            return commands.get(commandName);
        return null;
    }

    /**
     * Метод проверяет, существуют ли такая команда с заданными аргументами и выполняет ее
     * @param command
     * @param argument
     * @return
     */

    public boolean executeCommand(Command command, String argument, boolean isServer){
        if(command == null || !commands.containsValue(command))
            return false;

        if(!command.getGeneral() && ((command.getOnlyServer() && !isServer) || (!command.getOnlyServer() && isServer)))
            return false;

        if(!command.getHasArgument() && argument != null)
        {
            System.out.println("Данная команда не принимает аргументов");
            return false;
        }

        commandResponse = command.execute(argument);
        executeСurrentСommand = commandResponse.getExecution();
        if(!commandResponse.getExecution())
        {
            System.out.println("Операция не была проведена");
            System.out.println(commandResponse.getDescription());
            executeСurrentСommand = true;
        }
        return true;
    }

    public BaseResponse getCommandResponse(){
        return commandResponse;
    }

    /**
     * Метод возвращает словарь с командами
     * @return
     */
    public HashMap<String, Command> getCommands()
    {
        return commands;
    }

    public List<String> getExecuteCommnads(){ return executeCommnads; }
}
