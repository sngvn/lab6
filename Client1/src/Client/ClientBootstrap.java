package Client;

import Common.DTO.ClientDto;

import java.io.IOException;

public class ClientBootstrap {

    public static void main(String[] args) throws IOException {

        ClientDto clientDto = new ClientDto("Client1", "127.0.0.1", 6340);

        Client client = new Client(clientDto);

        client.connect();
    }
}
