package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class FindSlotCommand implements Command{
    private final CalendarService service;

    public FindSlotCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal command.");
        }

        System.out.println(service.findSlot(args[1], args[2]));
    }
}
