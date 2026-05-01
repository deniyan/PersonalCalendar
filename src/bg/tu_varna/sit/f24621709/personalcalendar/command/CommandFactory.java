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
                if (tokens.length > 1 && tokens[1].equalsIgnoreCase("as")){
                    return new SaveAsCommand(calendarService);
                }
                return new SaveCommand(calendarService);
            case "book":
                return new BookCommand(calendarService);
            case "agenda":
                return new AgendaCommand(calendarService);
            case "find":
                return new FindCommand(calendarService);
            case "holiday":
                return new HolidayCommand(calendarService);
            case "busydays":
                return new BusyDaysCommand(calendarService);
            case "findslot":
                return new FindSlotCommand(calendarService);
            case "unbook":
                return new UnBookCommand(calendarService);
            case "change":
                return new ChangeCommand(calendarService);
            case "findslotwith":
                return new FindSlotWithCommand(calendarService);
            default:
                throw new RuntimeException("Unknown command");
        }
    }
}
