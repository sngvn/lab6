package Common.DTO;

import java.io.Serial;
import java.io.Serializable;

public class InputResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 528740532177160917L;

    private final Ticket ticket;

    public  InputResponse(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket()
    {
        return this.ticket;
    }
}
