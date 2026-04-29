package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class CloseCommand implements Command{
    private final CalendarService calendarService;

    public CloseCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void execute(String[] args) {
        this.calendarService.close();
        System.out.println("Successfully closed file.");
    }
}
