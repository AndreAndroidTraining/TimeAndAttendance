import java.time.LocalDate;
import java.time.LocalTime;

public class DataRow {
    private String name;
    private String surname;
    private LocalDate date;
    private LocalTime inTime;
    private LocalTime outTime;

    public DataRow() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalTime inTime) {
        this.inTime = inTime;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalTime outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "["+this.getDate()+", "+this.getSurname()+","+this.getName()+","+this.getInTime()+","+this.getOutTime()+"]";
    }
}
