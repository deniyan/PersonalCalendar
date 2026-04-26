package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class HolidayCommand implements Command{
    private final CalendarService calendarService;

    public HolidayCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2){
            throw new IllegalArgumentException("Illegal command.");
        }
        calendarService.holiday(args[1]);
    }
}
