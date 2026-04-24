package bg.tu_varna.sit.f24621709.personalcalendar.model;

public class Event {
    private String date;
    private String starttime;
    private String endtime;
    private String name;
    private String note;

    public Event(String date, String starttime, String endtime, String name, String note) {
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.name = name;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
