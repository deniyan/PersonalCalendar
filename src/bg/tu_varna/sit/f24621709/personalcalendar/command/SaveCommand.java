package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

import java.nio.file.Path;

public class SaveCommand implements Command{
    private final CalendarService calendarService;

    public SaveCommand(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal command.");
        }
        String filename = calendarService.save();
        System.out.println("Successfully saved " + Path.of(filename).getFileName());
    }
}
