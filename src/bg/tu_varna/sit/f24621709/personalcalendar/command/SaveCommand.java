package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class SaveCommand implements Command{
    private final CalendarService calendarService;

    public SaveCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        calendarService.save();
    }
}
