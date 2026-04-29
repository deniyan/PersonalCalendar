package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class ChangeCommand implements Command{
    private final CalendarService service;

    public ChangeCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 5){
            throw new IllegalArgumentException("Illegal command.");
        }
        System.out.println(service.change(args[1], args[2], args[3], args[4]));
    }
}
