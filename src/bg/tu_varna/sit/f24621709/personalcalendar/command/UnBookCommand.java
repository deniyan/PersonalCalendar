package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class UnBookCommand implements Command{
    private final CalendarService service;

    public UnBookCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 4){
            throw new IllegalArgumentException("Illegal command.");
        }
        System.out.println(service.unbook(args[1], args[2], args[3]));
    }
}
