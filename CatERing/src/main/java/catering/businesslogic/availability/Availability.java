package catering.businesslogic.availability;

import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {
    private LocalDate startingDate;
    private LocalDate endingDate;
    private LocalTime startingTime;
    private LocalTime endingTime;

    public Availability(LocalDate startingDate, LocalDate endingDate, LocalTime startingTime, LocalTime endingTime) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }


    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalTime endingTime) {
        this.endingTime = endingTime;
    }
}
