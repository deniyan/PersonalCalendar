package bg.tu_varna.sit.f24621709.personalcalendar.repository;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;

public class XmlCalendarRepository implements CalendarRepository {

    @Override
    public Calendar load(String path) {
        System.out.println("Loading from XML: " + path);
        return new Calendar(path);
    }

    @Override
    public void save(String path, Calendar calendar) {
        System.out.println("Saving to XML: " + path);
    }
}