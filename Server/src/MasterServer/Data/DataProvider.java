package MasterServer.Data;

import Common.DTO.*;
import Common.DateFormatter;
import Common.SeparatorSettings;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс для работы с файлом и элементом
 */
public class DataProvider {
    /**
     * Метод получения коллекции из файла
     * @param filePath путь к файлу
     * @return коллекцию с билетами
     */
    public LinkedList<Ticket> loadFromFile(String filePath){
        try(FileReader reader = new FileReader(filePath)){
            Scanner scanner = new Scanner(reader);
            LinkedList<Ticket> tickets = new LinkedList<>();
            String ticketStr = scanner.nextLine();
            while(ticketStr != null){
                boolean isValidateValue = true;
                String[] firstSplit = ticketStr.split(SeparatorSettings.fieldSeparator);
                HashMap<String, String> dictionary = new HashMap<>();
                for(String s : firstSplit){
                    if(s.isEmpty())
                        continue;
                    String[] keyValue = s.split(SeparatorSettings.valueSeparator);
                    if(keyValue.length > 2 && keyValue[0].equals("creationDate")){
                        for(int i = 2; i < keyValue.length; i++)
                            keyValue[1] += SeparatorSettings.valueSeparator + keyValue[i];
                    }
                    else if (keyValue.length > 2)
                    {
                        System.out.println("Не удалось добавить элемент, так как его данные были повреждены");
                        isValidateValue = false;
                        break;
                    }
                    dictionary.put(keyValue[0], keyValue[1]);
                }
                if(isValidateValue)
                {
                    Ticket ticket = parseTicket(dictionary);
                    if(ticket != null)
                        tickets.add(ticket);
                }
                ticketStr = scanner.hasNextLine() ? scanner.nextLine() : null;
            }
            return tickets;
        }
        catch(Exception e){
            //System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Сохраняет коллекцию в файл
     * @param filePath путь к файлу
     * @param list сохраняемая коллекция
     */
    public void saveToFile(String filePath, LinkedList<Ticket> list){
        try(FileOutputStream out = new FileOutputStream(filePath); BufferedOutputStream bos = new BufferedOutputStream(out))
        {
            for(Ticket ticket : list){
                String ticketData = ticket.toString();
                bos.write(ticketData.getBytes());
                bos.write("\r\n".getBytes());
            }
            bos.flush();
        }catch (Exception e) {
            System.out.println("Такого файла не существует");
        }
    }

    /**
     * Метод для создания элемента по строке из файла
     * @param dictionary словарь с значениями полученными из строки в файле
     * @return элемент
     * @throws ParseException
     */
    private Ticket parseTicket(HashMap<String, String> dictionary) throws ParseException {
        Ticket ticket = new Ticket();
        try{
            ticket.setId(Long.parseLong(dictionary.get("id")));
            ticket.setName(dictionary.get("name"));
            double x = Double.parseDouble(dictionary.get("x"));
            double y = Double.parseDouble(dictionary.get("y"));
            Coordinates coordinates = new Coordinates(x, y);
            ticket.setCoordinates(coordinates);

            Date date = DateFormatter.getDate((dictionary.get("creationDate")));
            ticket.setCreationDate(date);
            ticket.setPrice(Float.parseFloat(dictionary.get("price")));
            ticket.setComment(dictionary.get("comment"));
            ticket.setRefundable(Boolean.parseBoolean(dictionary.get("refundable")));

            TicketType typeValue = dictionary.get("type").equals("null") ? null : TicketType.valueOf(dictionary.get("type"));
            ticket.setType(typeValue);

            if(!dictionary.containsKey("venueId"))
                ticket.setVenue(null);
            else{
                Venue venue = new Venue();
                venue.setId(Integer.parseInt(dictionary.get("venueId")));
                venue.setName(dictionary.get("venueName"));
                venue.setCapacity(Integer.parseInt(dictionary.get("venueCapacity")));
                venue.setType(VenueType.valueOf(dictionary.get("venueType")));
                ticket.setVenue(venue);
            }
            if(!ticket.validate())
                throw new Exception("Элемент не прошел валидацию");
        }
       catch (Exception e){
            System.out.println("Не удалось добавить элемент, так как его данные были повреждены");
            System.out.println(e.getMessage());
            return null;
       }
        return ticket;
    }
}
