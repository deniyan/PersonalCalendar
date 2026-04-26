package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;
import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class FindCommand implements Command{
    private final CalendarService calendarService;

    public FindCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Illegal command.");
        }

        for (Event event : calendarService.find(args[1])) {
            System.out.println(event.getDate() + " " + event.getStarttime() + "-" + event.getEndtime() + " | " +
                    event.getName() + " | " +
                    event.getNote());
        }
    }
}
