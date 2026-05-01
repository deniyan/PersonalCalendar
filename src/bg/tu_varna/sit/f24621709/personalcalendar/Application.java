package bg.tu_varna.sit.f24621709.personalcalendar;

import bg.tu_varna.sit.f24621709.personalcalendar.command.Command;
import bg.tu_varna.sit.f24621709.personalcalendar.command.CommandFactory;
import bg.tu_varna.sit.f24621709.personalcalendar.repository.XmlCalendarRepository;
import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        XmlCalendarRepository xmlCalendarRepository = new XmlCalendarRepository();
        CalendarService calendarService = new CalendarService(xmlCalendarRepository);
        Scanner scanner = new Scanner(System.in);
        CommandFactory factory = new CommandFactory(calendarService);

        //temp for testing
        //calendarService.open("calendar.xml");
        //calendarService.book("2025-04-21", "09:00", "11:00", "Meeting", "Work");
        //calendarService.book("2025-04-22", "10:00", "12:00", "Gym", "Gym");
        //calendarService.book("2025-04-22", "13:00", "15:00", "Study", "School");
        //calendarService.book("2025-04-23", "08:30", "10:00", "Coffee", "Shop");
        //


        while (true) {

            System.out.print("> ");
            String line = scanner.nextLine();

            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            try {

                String[] tokens = line.trim().split("\\s+");

                Command command = factory.create(tokens);

                command.execute(tokens);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

    }
}
