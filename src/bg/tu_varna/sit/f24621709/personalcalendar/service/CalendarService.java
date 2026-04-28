package bg.tu_varna.sit.f24621709.personalcalendar.service;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;
import bg.tu_varna.sit.f24621709.personalcalendar.repository.CalendarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CalendarService {
    private List<String> holidays = new ArrayList<>();
    private final CalendarRepository repository;
    private Calendar currentCalendar;
    private String currentPath;

    private void addHoliday(String date){
        holidays.add(date);
        List<Event> check = currentCalendar.getEventsList().stream().filter(e->e.getDate().equals(date)).toList();

        if (!check.isEmpty()){
            currentCalendar.getEventsList().removeAll(check);
        }

    }
    private boolean isHoliday(String date){
        return holidays.contains(date);
    }
    public void holiday(String date){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }

        if (isHoliday(date)){
            throw new IllegalStateException("The day is already a holiday.");
        }

        addHoliday(date);
        System.out.println("Succesfully added a holiday.");
    }
    public CalendarService(CalendarRepository repository) {
        this.repository = repository;
    }

    public void open(String path) {
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
            System.out.println("Successfully opened " + filePath.getFileName());

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
        System.out.println("Successfully closed file.");
    }
    public void save(){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        repository.save(currentPath, currentCalendar);
        System.out.println("Successfully saved " + currentPath);
    }
    public boolean hasOpenFile() {
        if (currentCalendar == null){
            //throw new IllegalStateException("No file opened.");
            return false;
        }else{
            return true;
        }
    }

    public void book(String date, String starttime, String endtime, String name, String note){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        currentCalendar.addEvent(new Event(date, starttime, endtime, name, note));
        System.out.println("Succesfully added event");
    }

    public List<Event> agenda(String date){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        List<Event> eventList = new ArrayList<>();
        eventList = currentCalendar.getEventsList().stream().filter(x->x.getDate().equals(date)).sorted(Comparator.comparing(Event::getDate)).toList();
        return eventList;
    }

    public List<Event> find(String input){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        List<Event> eventList = new ArrayList<>();
        eventList = currentCalendar.getEventsList().stream().filter(e -> e.getName().equals(input) || e.getNote().equals(input)).toList();
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
            result.add("SATURSAY ->" + saturday + "hours");
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
}
