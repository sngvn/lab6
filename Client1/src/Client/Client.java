package Client;

import Common.DTO.*;
import Common.Utilities.DtoUtility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private SocketChannel socketChannel;
    private ByteBuffer buffer;

    private final ClientDto clientDto;

    public Client(ClientDto clientDto){
        this.clientDto = clientDto;
    }

    public void connect() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), clientDto.getPort());

        socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        socketChannel.configureBlocking(false);

        buffer = ByteBuffer.allocate(1024);

        BaseResponse response = awaitResponse();
        System.out.println(Objects.requireNonNull(response).getDescription());

        if(response.getExecution()) {
            input();
        }
    }

    public void input() throws IOException {
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

                Request request = new Request(commandName, argument);
                sendToServer(request);
                requestHandler();
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Получен сигнал завершения работы.");
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
        }
    }

    public void requireInput(){
        Ticket ticket = Input.inputData();
        InputResponse inputRequest = new InputResponse(ticket);
        sendToServer(inputRequest);
    }

    private void sendToServer(Object object){
        try {
            byte[] bytes = DtoUtility.getBytes(object);
            buffer = ByteBuffer.wrap(Objects.requireNonNull(bytes));
            socketChannel.write(buffer);
            buffer.clear();
        }
        catch (Exception exception) {
            System.out.println("Потеряно соединение с сервером");
        }
    }

    private void requestHandler() throws IOException {
        buffer = ByteBuffer.allocate(1024 * 1024);

        int byteCounts = 0;
        while (byteCounts == 0) {
            byteCounts = socketChannel.read(buffer);
        }

        byte[] bytes = buffer.array();
        buffer.clear();

        BaseResponse response = DtoUtility.tryGetObject(BaseResponse.class, buffer.array());
        if(response != null) {
            String description = response.getDescription();
            if(description != null) {
                System.out.println(description);
            }
            if (response.getShutDown()){
                System.exit(0);
            }
        }
        else {
            InputRequest request = DtoUtility.tryGetObject(InputRequest.class, bytes);
            if(request != null) {
                requireInput();
            }
            requestHandler();
        }
    }

    private BaseResponse awaitResponse() throws IOException {
        buffer = ByteBuffer.allocate(1024 * 1024);

        int byteCounts = 0;
        while (byteCounts == 0) {
            byteCounts = socketChannel.read(buffer);
        }

        BaseResponse response = DtoUtility.tryGetObject(BaseResponse.class, buffer.array());

        buffer.clear();
        return response;
    }
}
