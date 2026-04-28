package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class SaveAsCommand implements Command{
    private final CalendarService service;

    public SaveAsCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal command.");
        }
        service.saveAs(args[2]);
    }
}
