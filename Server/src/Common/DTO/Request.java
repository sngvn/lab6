package Common.DTO;
import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = -124246000590638646L;

    private String commandName;
    private String argument;

    public Request(String commandName) {
        this.commandName = commandName;
    }

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return "Request [commandName=" + commandName + "]";
    }
}
