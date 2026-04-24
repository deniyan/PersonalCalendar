package bg.tu_varna.sit.f24621709.personalcalendar.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Event> eventList = new ArrayList<>();
    private String path;

    public Calendar(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }

    public List<Event> getEventList() {
        return eventList;
    }
    public void addEvent(Event event){
        eventList.add(event);
    }
}
