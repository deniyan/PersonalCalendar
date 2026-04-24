package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class CommandFactory {

    private final CalendarService calendarService;

    public CommandFactory(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
    public Command create(String[] tokens) {

        String commandName = tokens[0].toLowerCase();

        switch (commandName) {

            case "help":
                return new HelpCommand();
            case "exit":
                return new ExitCommand();
            case "close":
                return new CloseCommand(calendarService);
            case "open":
                return new OpenCommand(calendarService);
            case "save":
                return new SaveCommand(calendarService);
            case "book":
                return new BookCommand(calendarService);
            default:
                throw new RuntimeException("Unknown command");
        }
    }
}
