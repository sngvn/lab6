package MasterServer;

import Common.DTO.BaseResponse;
import Common.DTO.InputRequest;
import Common.DTO.InputResponse;
import Common.DTO.Request;
import Common.Utilities.DtoUtility;
import MasterServer.Commands.Command;
import MasterServer.CustomLogger.MyLogger;
import MasterServer.Data.CommandManager;
import MasterServer.Data.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class MainServer {

    private final int port;
    private final Data data;
    private Instant sendResponseTime;

    private Scanner scanner;
    private CommandManager commandManager;

    private Selector selector;
    private SocketChannel clientChannel;
    private ByteBuffer buffer;

    private static final Logger logger = MyLogger.getLogger(MainServer.class);

    public MainServer(int port, Data data){
        this.port = port;
        this.data = data;
    }

    public void start() throws IOException, ClassNotFoundException {
        commandManager = new CommandManager(this, data);
        scanner = new Scanner(System.in);
        initializeServer(port);
        selectorsHandle();
    }

    private void initializeServer(int port) throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        logger.info("Сервер запущен на порту:" + port);

        sendResponseTime = Instant.now();
    }

    private void selectorsHandle() throws IOException{
        while(true) {
            int readyChannels = selector.selectNow();
            Iterator<SelectionKey> keyIterator;
            if (readyChannels > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                keyIterator = selectedKeys.iterator();
            } else {
                readServerCommands();
                continue;
            }

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                try {
                    if (key.isAcceptable()) {
                        accept(keyIterator, key);
                    }
                    if (key.isReadable()) {
                        read(keyIterator, key);
                    }
                } catch (SocketException e) {
                    logger.info("Соединение сброшено: " + e.getMessage());
                    connectionReset(keyIterator, key);
                } catch (IOException e) {
                    logger.info("Ошибка ввода-вывода: " + e.getMessage());
                    key.cancel();
                    keyIterator.remove();
                }
            }
        }
    }

    private void accept(Iterator<SelectionKey> keyIterator, SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            logger.info("Подключился новый пользователь: " + clientChannel.getRemoteAddress());
            sendResponse(clientChannel, new BaseResponse(true, "Вы подключились"));
        }
        keyIterator.remove();
    }

    private void read(Iterator<SelectionKey> keyIterator, SelectionKey key) throws IOException {
        clientChannel = (SocketChannel) key.channel();

        if (clientChannel != null) {
            buffer = ByteBuffer.allocate(1024);

            int byteCounts = clientChannel.read(buffer);
            if (byteCounts > 0) {
                byte[] bytes = buffer.array();
                readCommand(bytes);
                buffer.clear();
            } else if (byteCounts == -1) {
                logger.info("Клиент отключился");
                clientChannel.close();
            }
            keyIterator.remove();
        }
    }

    private void connectionReset(Iterator<SelectionKey> keyIterator, SelectionKey key){
        key.cancel();
        SocketChannel clientChannel = (SocketChannel) key.channel();
        if (clientChannel != null && clientChannel.isOpen()) {
            try {
                logger.info("Канал с пользователем: " + clientChannel.getRemoteAddress() + " закрыт");
                clientChannel.close();
            } catch (IOException ex) {
                logger.info("Ошибка при закрытии канала: " + ex.getMessage());
            }
        }
        keyIterator.remove();
    }


    private void readCommand(byte[] bytes) throws IOException {
        Request request = DtoUtility.tryGetObject(Request.class, bytes);
        Command command = commandManager.getCommand(Objects.requireNonNull(request).getCommandName());

        if (!commandManager.executeCommand(command, request.getArgument(), false)) {
            sendResponse(clientChannel, new BaseResponse(false, "Такой команды нет. Чтобы узнать команды наберите 'help' "));
        }
        else
        {
            logger.info("execute command -> " + command);
            buffer.clear();
            while(true)
            {
                if (commandManager.getCanReadyCommands()) {
                    sendResponse(clientChannel, commandManager.getCommandResponse());
                    break;
                }
            }
        }
    }

    public void sendInputRequest() throws IOException {
        buffer.clear();
        InputRequest inputRequest = new InputRequest();
        byte[] bytes = DtoUtility.getBytes(inputRequest);
        buffer = ByteBuffer.wrap(Objects.requireNonNull(bytes));
        clientChannel.write(buffer);
        buffer.clear();
        logger.info("send input request");
    }

    public InputResponse awaitInputResponse() throws IOException {
        buffer = ByteBuffer.allocate(1024);

        int byteCounts = 0;
        while (byteCounts == 0) {
            byteCounts = clientChannel.read(buffer);
        }

        byte[] bytes = buffer.array();
        InputResponse inputRequest = DtoUtility.tryGetObject(InputResponse.class, bytes);
        buffer.clear();

        logger.info("get input request");
        return  inputRequest;
    }

    private void sendResponse(SocketChannel clientChannel, BaseResponse response) throws IOException {
        byte[] bytes = DtoUtility.getBytes(response);
        buffer = ByteBuffer.wrap(Objects.requireNonNull(bytes));

        clientChannel.write(buffer);
        buffer.clear();

        logger.info(response.toString());
        sendResponseTime = Instant.now();
    }

    private void tryExecuteCommand() {
        String input = scanner.nextLine();
        String[] arguments = input.split(" ");
        String commandName = arguments[0];
        String argument = null;
        if (arguments.length > 1)
            argument = arguments[1];

        Command command = commandManager.getCommand(commandName);
        if(!commandManager.executeCommand(command, argument, true)){
            logger.info("Попытка вызова команды, которой нет");
        }
        else {
            logger.info("execute server command -> " + command);
            while(true)
            {
                if (commandManager.getCanReadyCommands()) {
                    logger.info(commandManager.getCommandResponse().toString());
                    if(commandManager.getCommandResponse().getShutDown()) {
                        saveAndExit();
                    }
                    break;
                }
            }
        }
    }

    private void readServerCommands() throws IOException {
        if (System.in.available() > 0) {
            if(inputAnalysis() < 1)
            {
                clearInput();
            }
            else
                tryExecuteCommand();
        }
    }

    private long inputAnalysis(){
        Instant availableTime = Instant.now();
        long startTimeInSeconds = sendResponseTime.getEpochSecond();
        long availableTimeInSeconds = availableTime.getEpochSecond();
        return availableTimeInSeconds - startTimeInSeconds;
    }

    private void clearInput() throws IOException {
        int available = System.in.available();
        while (available > 0) {
            System.in.read();
            available--;
        }
    }

    private void saveAndExit(){
        Command save = commandManager.getCommand("save");
        commandManager.executeCommand(save, null,true);
        while(true)
        {
            if(commandManager.getCanReadyCommands()){
                logger.info(commandManager.getCommandResponse().toString());
                logger.info("Завершение работы");
                System.exit(0);
            }
        }
    }
 }
