package bg.tu_varna.sit.f24621709.personalcalendar.service;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Holiday;
import bg.tu_varna.sit.f24621709.personalcalendar.repository.CalendarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CalendarService {
    private final CalendarRepository repository;
    private Calendar currentCalendar;
    private String currentPath;

    private void addHoliday(String date){
        currentCalendar.addHoliday(new Holiday(date));
        List<Event> check = currentCalendar.getEventsList().stream().filter(e->e.getDate().equals(date)).toList();

        if (!check.isEmpty()){
            currentCalendar.getEventsList().removeAll(check);
        }
    }

    private boolean isHoliday(String date){
        return currentCalendar.getHolidayList().stream().anyMatch(e->e.getDate().equals(date));
    }
    public String holiday(String date){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }

        if (isHoliday(date)){
            throw new IllegalStateException("The day is already a holiday.");
        }

        addHoliday(date);
        return "Successfully added a holiday.";
    }
    public CalendarService(CalendarRepository repository) {
        this.repository = repository;
    }

    public String open(String path) {
        if (hasOpenFile()) {
            throw new IllegalStateException("File already opened");
        }

        try {
            Path filePath = Path.of(path);

            if (Files.exists(filePath)) {
                currentCalendar = repository.load(path);
            } else {
                Files.createFile(filePath);
                currentCalendar = new Calendar(path);
            }

            currentPath = path;
            return "Successfully opened " + filePath.getFileName();

        } catch (IOException e) {
            throw new RuntimeException("Error opening file: " + e.getMessage());
        }
    }

    public void close() {
        if (!hasOpenFile()) {
            throw new IllegalStateException("No file opened.");
        }
        currentCalendar = null;
        currentPath = null;
    }
    public String save(){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        repository.save(currentPath, currentCalendar);
        return currentPath;
    }
    public boolean hasOpenFile() {
        if (currentCalendar == null){
            //throw new IllegalStateException("No file opened.");
            return false;
        }else{
            return true;
        }
    }

    public String book(String date, String starttime, String endtime, String name, String note){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        currentCalendar.addEvent(new Event(date, starttime, endtime, name, note));
        return "Successfully added event";
    }

    public List<Event> agenda(String date){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        List<Event> eventList = currentCalendar.getEventsList().stream().filter(x->x.getDate().equals(date)).sorted(Comparator.comparing(Event::getStarttime)).toList();
        return eventList;
    }

    public List<Event> find(String input){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        List<Event> eventList = currentCalendar.getEventsList().stream().filter(e -> e.getName().contains(input) || e.getNote().contains(input)).toList();
        return eventList;
    }

    public List<String> busyDays(String from, String to){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        List<String> result = new ArrayList<>();

        double monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDay = LocalDate.parse(to);

        for (Event event : currentCalendar.getEventsList()){
            LocalDate date = LocalDate.parse(event.getDate());

            if (!date.isBefore(fromDate) && !date.isAfter(toDay)){
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                double hours = calculateHours(event.getStarttime(), event.getEndtime());
                switch (dayOfWeek){
                    case DayOfWeek.MONDAY:
                        monday += hours;
                        break;
                    case  DayOfWeek.TUESDAY:
                        tuesday += hours;
                        break;
                    case DayOfWeek.WEDNESDAY:
                        wednesday += hours;
                        break;
                    case DayOfWeek.THURSDAY:
                        thursday += hours;
                        break;
                    case DayOfWeek.FRIDAY:
                        friday += hours;
                        break;
                    case DayOfWeek.SATURDAY:
                        saturday += hours;
                        break;
                    case DayOfWeek.SUNDAY:
                        sunday += hours;
                        break;
                    default:
                        throw new IllegalArgumentException("Невалидна дата");
                }
            }
        }
        if (monday > 0){
            result.add("MONDAY ->" + monday + "hours");
        }
        if (tuesday > 0) {
            result.add("TUESDAY ->" + tuesday + "hours");
        }
        if (wednesday > 0) {
            result.add("WEDNESDAY ->" + wednesday + "hours");
        }
        if (thursday > 0) {
            result.add("THURSDAY ->" + thursday + "hours");
        }
        if (friday > 0) {
            result.add("FRIDAY ->" + friday + "hours");
        }
        if (saturday > 0) {
            result.add("SATURDAY ->" + saturday + "hours");
        }
        if (sunday > 0) {
            result.add("SUNDAY ->" + sunday + "hours");
        }
        return result;
    }
    private double calculateHours(String start, String end){
        String[] s = start.split(":");
        String[] t = end.split(":");

        int startTime = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
        int endTime = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
        return (endTime - startTime) / 60.0;
    }

    public String findSlot(String fromDate, String hours){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        LocalDate date = LocalDate.parse(fromDate);
        int duration = Integer.parseInt(hours) * 60;

        while (true){
            LocalDate currentDate = date;
            List<Event> eventList = currentCalendar.getEventsList().stream().filter(e -> e.getDate().equals(currentDate.toString())).toList();

            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY){
                date = date.plusDays(1);
                continue;
            }

            if (isHoliday(date.toString())){
                date = date.plusDays(1);
                continue;
            }

            for (int start = 8*60; start <= 17*60 - duration; start++) {
                int endTime = start + duration;

                if (isFree(eventList, start, endTime)){
                    return date + " " + String.format("%02d:%02d", start / 60, start % 60) +
                            "-" +
                            String.format("%02d:%02d", endTime / 60, endTime % 60);
                }
            }
            date = date.plusDays(1);
        }
    }
    private boolean isFree(List<Event> events, int start, int end){
        for (Event e : events) {
            String[] s = e.getStarttime().split(":");
            String[] t = e.getEndtime().split(":");

            int startTimeToMin = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
            int endTimeToMin = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);

            if (start < endTimeToMin && end > startTimeToMin) {
                return false;
            }
        }
        return true;
    }
    public String unbook(String date, String starttime, String endtime){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }

        for (Event event : currentCalendar.getEventsList()){
            if (event.getDate().equals(date) && event.getStarttime().equals(starttime) && event.getEndtime().equals(endtime)){
                currentCalendar.removeEvent(event);
                return "Successfully removed event.";
            }
        }
        return "No event found.";
    }
    public String saveAs(String path){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        repository.save(path, currentCalendar);
        return path;
    }

    private int toMinutes(String time){
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }
    public String change(String date, String starttime, String option, String newValue){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        for (Event e : currentCalendar.getEventsList()){
            if (e.getDate().equals(date) && e.getStarttime().equals(starttime)){
                String newDate = e.getDate();
                String newStarttime = e.getStarttime();
                String newEndTime = e.getEndtime();

                switch (option){
                    case "date":
                        newDate = newValue;
                        break;
                    case "starttime":
                        newStarttime = newValue;
                        break;
                    case "endtime":
                       newEndTime = newValue;
                        break;
                    case "name":
                        e.setName(newValue);
                        return "Changed";
                    case "note":
                        e.setNote(newValue);
                        return "Changed";
                    default:
                        throw new IllegalArgumentException("Invalid command");
                }


                if (toMinutes(newStarttime) >= toMinutes(newEndTime)){
                    return "Impossible time range!";
                }

                for (Event other : currentCalendar.getEventsList()){
                    if (other == e){
                        continue;
                    }
                    if (other.getDate().equals(newDate)){
                        int otherStart = toMinutes(other.getStarttime());
                        int otherEnd = toMinutes(other.getEndtime());

                        int newStart = toMinutes(newStarttime);
                        int newEnd = toMinutes(newEndTime);

                        if (newStart < otherEnd && newEnd > otherStart){
                            return "There is time conflict.";
                        }
                    }
                }
                e.setDate(newDate);
                e.setStarttime(newStarttime);
                e.setEndtime(newEndTime);
                return "Changed successfully.";
            }
        }
        return "Event not found";
    }
}
