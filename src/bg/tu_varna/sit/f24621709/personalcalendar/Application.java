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
            }
        }

    }
}
