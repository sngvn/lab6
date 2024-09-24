package MasterServer.Commands;

import Common.DTO.*;
import MasterServer.Data.IdGenerator;
import MasterServer.MainServer;

import java.util.Date;
import java.util.Scanner;

/**
 * Класс для генерации элемента класса Ticket
 */
public class CreateElement{
    /**
     * Возвращает элемент созданный путем вызова методов fastCreate/create в зависимости от переданных параметров
     * @param scanner
     * @param fastCreate
     * @return элемент типа Ticket
     */
    public static Ticket getNewTicket(MainServer server, Scanner scanner, boolean fastCreate){
        long id = IdGenerator.generateId(100);
        if(fastCreate)
            return fastCreate(scanner, id);
        else
            return create(server,scanner, id);
    }

    /**
     * Возвращает элемент созданный путем вызова методов fastCreate/create в зависимости от переданных параметров
     * В отличии от getNewTicket не генерирует id, а принимает в качестве параметра, что нужно для команды updateId
     * @param scanner
     * @param id
     * @param fastCreate
     * @return элемент типа Ticket
     */
    public static Ticket updateTicket(MainServer server, Scanner scanner, long id, boolean fastCreate){
        if(fastCreate)
            return fastCreate(scanner, id);
        else
            return create(server, scanner, id);
    }

    /**
     * Инициализация объекта Ticket (заполнения всех его полей) из терминала
     * @param scanner объект типа Scanner
     * @param id id элемента
     * @return элемент типа Ticket
     */
    private static Ticket create(MainServer server, Scanner scanner, long id)
    {
        Ticket ticket = new Ticket();
        Ticket requestTicket = null;

        try{
            server.sendInputRequest();
            requestTicket = server.awaitInputResponse().getTicket();
            if(requestTicket == null){
                return null;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        ticket = requestTicket;

        ticket.setId(id);

        Date date = new Date();
        ticket.setCreationDate(date);

        if(requestTicket.getVenue() != null)
        {
            int venueId = IdGenerator.generateId(100);
            ticket.getVenue().setId(venueId);
        }

        return ticket;
    }
    /**
     * Инициализация объекта Ticket (заполнения всех его полей) из скрипта
     * @param scanner объект типа Scanner
     * @param id id элемента
     * @return элемент типа Ticket
     */
    private static Ticket fastCreate(Scanner scanner, long id){
        Ticket ticket = new Ticket();
        boolean result = true;

        ticket.setId(id);
        ticket.setName(scanner.nextLine());

        String coordinatesStr = scanner.nextLine();
        if (coordinatesStr == null)
            result = false;
        String[] coordinate = coordinatesStr.split(" ");
        if(coordinate.length != 2)
            result = false;
        double x = Double.parseDouble(coordinate[0]);
        double y = Double.parseDouble(coordinate[1]);
        Coordinates coordinates = new Coordinates(x,y);
        ticket.setCoordinates(coordinates);
        ticket.setCreationDate(new Date());

        try{
            String nextLine = scanner.nextLine();
            ticket.setPrice(Float.parseFloat(nextLine));
            ticket.setComment(scanner.nextLine());
        }
        catch (Exception ex){ result = false; }

        String refund = scanner.nextLine();
        if (!refund.equals("Да") && !refund.equals("Нет"))
            result = false;
        ticket.setRefundable(refund.equals("Да"));

        String ticketType = scanner.nextLine();
        if (!ticketType.trim().isEmpty() && !ticketType.equals("VIP") && !ticketType.equals("USUAL") && !ticketType.equals("BUDGETARY") && !ticketType.equals("CHEAP"))
            result = false;
        if (!ticketType.trim().isEmpty()){
            TicketType type = TicketType.valueOf(ticketType);
            ticket.setType(type);
        }

        String answer = scanner.nextLine();
        if (!answer.equals("Да") && !answer.equals("Нет"))
            result = false;

        if(answer.equals("Нет"))
        {
            if(!result || !ticket.validate())
                return null;
            return ticket;
        }

        Venue venue = new Venue();
        int venueId = IdGenerator.generateId(100);
        venue.setId(venueId);
        venue.setName(scanner.nextLine());
        venue.setCapacity(Integer.parseInt(scanner.nextLine()));
        String venueType = scanner.nextLine();
        if (!venueType.equals("THEATRE") && !venueType.equals("CINEMA") && !venueType.equals("STADIUM"))
            result = false;
        venue.setType(VenueType.valueOf(scanner.nextLine()));
        ticket.setVenue(venue);

        if(!result || !ticket.validate())
            return null;
        return ticket;
    }
}
