package MasterServer;

import Common.DTO.Ticket;
import MasterServer.Data.Data;
import MasterServer.Data.DataProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class ServerBootstrap {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String filePath = null;
        filePath = "C:\\Users\\User\\Desktop\\lab_5\\data.csv";
        if (args.length > 0){
            filePath = args[0];
        }

        LinkedList<Ticket> collection = tryFoundCollection(filePath, args);

        Data data = new Data(collection);
        data.setFilePath(filePath);

        MainServer mainServer = new MainServer(6340, data);

        mainServer.start();
    }

    private static LinkedList<Ticket> tryFoundCollection(String filePath, String[] args){
        DataProvider provider = new DataProvider();

        LinkedList<Ticket> list;
        try {
            list = provider.loadFromFile(filePath);
            if (list == null)
                throw new FileNotFoundException();
            else
                System.out.println("Коллекция успешно загрузилась");
        } catch (Exception ex) {
            System.out.println("Нет аргумента для поиска коллекции. Создаем новую...");
            list = new LinkedList<>();
        }
        return list;
    }
}
