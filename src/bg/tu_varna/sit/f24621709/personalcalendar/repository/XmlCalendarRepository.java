package bg.tu_varna.sit.f24621709.personalcalendar.repository;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlCalendarRepository implements CalendarRepository {

    @Override
    public Calendar load(String path) {
        System.out.println("Loading from XML: " + path);
        return new Calendar(path);
    }

    @Override
    public void save(String path, Calendar calendar) {
        StringBuilder xml = new StringBuilder();
        xml.append("<calendar>\n");

        for (Event event : calendar.getEventsList()){
            xml.append("\t<event>\n");
            xml.append("\t\t<date>" + event.getDate() + "</date>\n");
            xml.append("\t\t<starttime>" + event.getStarttime() + "</starttime>\n");
            xml.append("\t\t<endtime>" + event.getEndtime() + "</endtime>\n");
            xml.append("\t\t<name>" + event.getName() + "</name>\n");
            xml.append("\t\t<note>" + event.getNote() + "</note>\n");
            xml.append("\t</event>\n");
        }
        xml.append("</calendar>");
        //System.out.println("Saving to XML: " + path);
        try {
            Files.writeString(Path.of(path), xml.toString());
        }catch (IOException e){
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}