package MasterServer.Commands;

import Common.DTO.BaseResponse;
import MasterServer.Data.CommandManager;

import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Класс команды, выполняющей ссчитывание и исполнение скрипта из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */
public class ExecuteScript extends Command{
    /**
     * Объект CommandManager для работы с командами из скрипта
     */
    private final CommandManager commandManager;
    /**
     * объект типа Scanner для считывания из файла
     */
    private Scanner executeScanner;

    /**
     * Конструктор обращается к родительскому конструктору абстрактного класса и принимает объект CommandManager в качестве аргумента
     * @param commandManager Объект CommandManager для работы с командами из скрипта
     */
    public ExecuteScript(CommandManager commandManager) {
        super("Считать команды из указанного файла (укажите имя файла)", true);
        this.commandManager = commandManager;
    }
    /**
     * В методе прописана логика выполнения команды
     * @param argument
     * @return информацию о результате выполнения
     */
    @Override
    public BaseResponse execute(String argument) {
        try {
            executeScanner = new Scanner(new FileInputStream(argument));
            String executeFileName = argument;
            List<String> list = commandManager.getExecuteCommnads();
            String line = executeScanner.nextLine();
            while (line != null) {
                String[] arguments = line.split(" ");
                String commandName = arguments[0];
                String argumentCommand = null;
                if (arguments.length > 1)
                    argumentCommand = arguments[1];

                Command command = commandManager.getCommand(commandName);
                if((command.getClass().equals(ExecuteScript.class) && executeFileName.equals(argumentCommand)) || list.contains(executeFileName)){
                    System.out.println("Была попытка вызова рекурсии");
                    try {
                        line = executeScanner.nextLine();
                        continue;
                    }
                    catch (Exception ex) {
                        System.out.println("Файл прочтен");
                        break;
                    }
                }

                IUseScanner iUseScanner = command instanceof IUseScanner ? ((IUseScanner) command) : null;
                if(iUseScanner != null)
                    iUseScanner.setScanner(executeScanner, false);
                list.add(executeFileName);
                boolean execution = commandManager.executeCommand(command, argumentCommand, false);
                if(!execution)
                    System.out.println("Такой команды нет. Строчка: " + line);
                else{
                    if(iUseScanner != null)
                    {
                        iUseScanner.setScanner(new Scanner(System.in), true);
                    }
                }
                try { line = executeScanner.nextLine(); }
                catch (Exception ex) {
                    System.out.println("Файл прочтен");
                    break;
                }
            }
            executeScanner.close();
            return new BaseResponse(true);
        } catch (Exception ex) {
            return new BaseResponse(false, ex.getMessage());
        }
    }
}
