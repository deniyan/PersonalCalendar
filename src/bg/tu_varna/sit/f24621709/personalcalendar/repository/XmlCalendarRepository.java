package bg.tu_varna.sit.f24621709.personalcalendar.repository;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Holiday;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlCalendarRepository implements CalendarRepository {

    private String getText(String text, String tagStart, String tagEnd){
        int start = text.indexOf(tagStart);
        int end = text.indexOf(tagEnd);

        if (start == -1 || end == -1){
            return "";
        }
        return text.substring(start + tagStart.length(), end).trim();
    }

    @Override
    public Calendar load(String path) {
        Calendar calendar = new Calendar(path);

        try {
            String files = Files.readString(Path.of(path));

            String[] holidays = files.split("<holiday>");
            for (int i = 1; i < holidays.length; i++) {
                String e = holidays[i];
                String date = getText(e, "<date>", "</date>");

                calendar.addHoliday(new Holiday(date));
            }


            String[] events = files.split("<event>");
            for (int i = 1; i < events.length; i++) {
                String e = events[i];
                String date = getText(e, "<date>", "</date>");
                String starttime = getText(e, "<starttime>", "</starttime>");
                String endtime = getText(e, "<endtime>", "</endtime>");
                String name = getText(e, "<name>", "</name>");
                String note = getText(e, "<note>", "</note>");

                calendar.addEvent(new Event(date, starttime, endtime, name, note));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return calendar;
    }

    @Override
    public void save(String path, Calendar calendar) {
        StringBuilder xml = new StringBuilder();
        xml.append("<calendar>\n");

        xml.append("\t<holidays>\n");
        for (Holiday holiday : calendar.getHolidayList()){
            xml.append("\t\t<holiday>\n");
            xml.append("\t\t\t<date>" + holiday.getDate() + "</date>\n");
            xml.append("\t\t</holiday>\n");
        }
        xml.append("\t</holidays>\n");


        xml.append("\t<events>\n");
        for (Event event : calendar.getEventsList()){
            xml.append("\t\t<event>\n");
            xml.append("\t\t\t<date>" + event.getDate() + "</date>\n");
            xml.append("\t\t\t<starttime>" + event.getStarttime() + "</starttime>\n");
            xml.append("\t\t\t<endtime>" + event.getEndtime() + "</endtime>\n");
            xml.append("\t\t\t<name>" + event.getName() + "</name>\n");
            xml.append("\t\t\t<note>" + event.getNote() + "</note>\n");
            xml.append("\t\t</event>\n");
        }
        xml.append("\t</events>\n");
        xml.append("</calendar>");
        try {
            Files.writeString(Path.of(path), xml.toString());
        }catch (IOException e){
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}