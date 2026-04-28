package bg.tu_varna.sit.f24621709.personalcalendar.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Event> eventsList = new ArrayList<>();
    private String path;

    public Calendar(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }
    public void addEvent(Event event){
        eventsList.add(event);
    }
    public void removeEvent(Event event){
        eventsList.remove(event);
    }
}
