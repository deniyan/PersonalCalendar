package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class BusyDaysCommand implements Command{
    private final CalendarService calendarService;

    public BusyDaysCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal command.");
        }
        for (String result : calendarService.busyDays(args[1], args[2])) {
            System.out.println(result);
        }
    }
}
