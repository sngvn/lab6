package Common.DTO;
import java.io.Serial;
import java.io.Serializable;

public class ClientDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7718203504461986667L;

    private final String name;
    private final String address;
    private final int port;

    public ClientDto(String name, String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Client " + name + " at " + address;
    }
}
