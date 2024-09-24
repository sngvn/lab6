package Client;

import Common.DTO.*;

import java.util.Scanner;

public class Input {

    public static Ticket inputData()
    {
        Ticket ticket = new Ticket();
        Scanner scanner = new Scanner(System.in);

        try {
            while (true){
                System.out.println("Введите название билета");
                String name = scanner.nextLine();
                if (name == null || name.trim().isEmpty()){
                    System.out.println("Имя не может быть null или пустой строкой");
                }else {
                    ticket.setName(name);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }

        while (true){
            try {
                while (true){
                    System.out.println("Введите координаты");
                    String coordinatesStr = scanner.nextLine();

                    if (coordinatesStr == null)
                        System.out.println("Координаты не могут быть null");
                    String[] coordinate = coordinatesStr.split(" ");
                    if(coordinate.length != 2)
                    {
                        System.out.println("Ввести можно только две координаты");
                        throw  new Exception();
                    }
                    double x = Double.parseDouble(coordinate[0]);
                    double y = Double.parseDouble(coordinate[1]);
                    if (x > 528 || y < -517){
                        System.out.println("x не может быть больше 528 и y не может быть меньше -517");
                    }else{
                        Coordinates coordinates = new Coordinates(x,y);
                        ticket.setCoordinates(coordinates);
                        break;
                    }
                } break;
            }catch (Exception e){
                System.out.println("Ошибка ввода");
            }
        }

        while (true){
            try{
                while (true){
                    System.out.println("Введите цену билета");
                    String nextLine = scanner.nextLine();
                    float price = Float.parseFloat(nextLine);
                    if ((nextLine.charAt(0) == '0') || (price <= 0)){
                        System.out.println("Цена не может быть такой");
                    }else{
                        ticket.setPrice(price);
                        break;
                    }
                }break;
            }catch(NumberFormatException e){
                System.out.println("Ошибка ввода");
            }
        }
        try {
            while (true){
                System.out.println("Введите комментарий");
                String comment = scanner.nextLine();
                if (comment == null || comment.trim().isEmpty()){
                    System.out.println("Комментарий не может быть null или пустой строкой");
                }else {
                    ticket.setComment(comment);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }

        try {
            while (true){
                System.out.println("Билет подлежит возврату? Введите Да/Нет");
                String refund = scanner.nextLine();
                if (!refund.equals("Да") && !refund.equals("Нет")){
                    System.out.println("Требуется ввести Да/Нет");
                }else{
                    boolean refundable = refund.equals("Да");
                    ticket.setRefundable(refundable);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }

        try{
            while (true){
                System.out.println("Введите тип билета VIP/USUAL/BUDGETARY/CHEAP");
                String ticketType = scanner.nextLine();
                if (!ticketType.trim().isEmpty() && !ticketType.equals("VIP") && !ticketType.equals("USUAL") && !ticketType.equals("BUDGETARY") && !ticketType.equals("CHEAP")){
                    System.out.println("Тип билета должен быть задан как VIP/USUAL/BUDGETARY/CHEAP или null");

                }else{
                    if (!ticketType.trim().isEmpty()){
                        TicketType type = TicketType.valueOf(ticketType);
                        ticket.setType(type);
                    }
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }

        try {
            while (true){
                System.out.println("Вы хотите указать местоположение? Введите Да/Нет");
                String answer = scanner.nextLine();
                if (!answer.equals("Да") && !answer.equals("Нет")){
                    System.out.println("Требуется ввести Да/Нет");
                }else{
                    if (answer.equals("Нет")){
                        return ticket;
                    }
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }

        Venue venue = new Venue();
        try {
            while (true){
                System.out.println("Введите название места проведения мероприятия");
                String name = scanner.nextLine();
                if (name == null || name.trim().isEmpty()){
                    System.out.println("Название не может быть null или пустой строкой");
                }else {
                    venue.setName(name);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }
        while (true){
            try{
                while (true){
                    System.out.println("Введите вместимость площадки мероприятия");
                    String nextLine = scanner.nextLine();
                    int capacity = Integer.parseInt(nextLine);
                    if ((nextLine.charAt(0) == '0') || (capacity <= 0)){
                        System.out.println("Вместимость не может быть нулевой или меньше нуля");
                    }else{
                        venue.setCapacity(capacity);
                        break;
                    }
                }break;
            }catch(NumberFormatException e){
                System.out.println("Ошибка ввода");
            }
        }
        try{
            while (true){
                System.out.println("Введите место проведения THEATRE/CINEMA/STADIUM");
                String venueType = scanner.nextLine();
                if (!venueType.equals("THEATRE") && !venueType.equals("CINEMA") && !venueType.equals("STADIUM")){
                    System.out.println("Место проведения должно быть задано как THEATRE/CINEMA/STADIUM и не может быть null");

                }else{
                    VenueType type = VenueType.valueOf(venueType);
                    venue.setType(type);
                    ticket.setVenue(venue);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Ошибка ввода");
        }
        return ticket;
    }
}
