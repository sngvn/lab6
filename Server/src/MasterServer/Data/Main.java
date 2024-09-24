package MasterServer.Data;

/**
 * Главный класс с методом main
 *
 */
/*
public class Main {
    */
/**
     * В качесте аргумента ожидается путь к файлу для выгрузки коллекции
     * В случае, если файл не указан, создается новая коллекция
     * @param args
     *//*

    public static void main(String[] args) {
        DataProvider provider = new DataProvider();
        String filePath = null;
        if (args.length > 0){
            filePath = args[0];
        }
        //filePath = "C:\\Users\\User\\Desktop\\docs\\ITMO\\java\\lab_5\\data.csv";
        LinkedList<Ticket> list;
        try {
            list = provider.loadFromFile(filePath);
            if (list == null)
                throw new FileNotFoundException();
        } catch (Exception ex) {
            System.out.println("Нет аргумента для поиска коллекции. Создаем новую...");
            list = new LinkedList<>();
        }
        Server.Data data = new Server.Data(list);
        data.setFilePath(filePath);
        CommandManager commandManager = new CommandManager(data);
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Введите команду");

                String input = scanner.nextLine();
                String[] arguments = input.split(" ");
                String commandName = arguments[0];
                String argument = null;
                if (arguments.length > 1)
                    argument = arguments[1];

                Command command = commandManager.getCommand(commandName);
                if (!commandManager.executeCommand(command, argument))
                    System.out.println("Такой команды нет. Чтобы узнать команды наберите 'help' ");
                else {
                    if (commandManager.getCanReadyCommands())
                        continue;
                    break;
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Получен сигнал завершения работы.");
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
        }
    }
}
*/
