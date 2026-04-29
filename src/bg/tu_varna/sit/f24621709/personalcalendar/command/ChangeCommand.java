package bg.tu_varna.sit.f24621709.personalcalendar.command;

import bg.tu_varna.sit.f24621709.personalcalendar.service.CalendarService;

public class ChangeCommand implements Command{
    private final CalendarService service;

    public ChangeCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {

    }
}
