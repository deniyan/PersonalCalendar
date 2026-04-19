package bg.tu_varna.sit.f24621709.personalcalendar.repository;


import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;

public interface CalendarRepository {

    Calendar load(String path);

    void save(String path, Calendar calendar);
}