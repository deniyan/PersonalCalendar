package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class OpenCommand implements Command{
    private final CalendarService service;

    public OpenCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Illegal file.");
        }
        this.service.open(args[1]);
    }
}
