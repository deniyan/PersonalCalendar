package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.repository.CalendarRepository;
import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class BusyDays implements Command{
    private final CalendarService calendarService;

    public BusyDays(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal command.");
        }
        System.out.println(calendarService.busyDays(args[1], args[2]));

    }
}
