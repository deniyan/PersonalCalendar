package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class BookCommand implements Command{
    private final CalendarService calendarService;

    public BookCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 6) {
            throw new IllegalArgumentException("Illegal file.");
        }

        System.out.println(calendarService.book(args[1], args[2], args[3], args[4], args[5]));
    }
}
