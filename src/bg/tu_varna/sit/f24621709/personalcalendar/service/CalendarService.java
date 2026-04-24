package bg.tu_varna.sit.f24621709.personalcalendar.service;

import bg.tu_varna.sit.f24621709.personalcalendar.model.Calendar;
import bg.tu_varna.sit.f24621709.personalcalendar.model.Event;
import bg.tu_varna.sit.f24621709.personalcalendar.repository.CalendarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CalendarService {
    private final CalendarRepository repository;
    private Calendar currentCalendar;
    private String currentPath;

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

    public void Book(String date, String starttime, String endtime, String name, String note){
        if (!hasOpenFile()){
            throw new IllegalStateException("No file opened.");
        }
        currentCalendar.addEvent(new Event(date, starttime, endtime, name, note));
        System.out.println("Succesfully added event");
    }
}
