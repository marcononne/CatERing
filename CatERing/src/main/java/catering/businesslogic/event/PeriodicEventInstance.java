package catering.businesslogic.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class PeriodicEventInstance {

    private LocalDate execDate;
    private String location;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private int services;
    private int participants;
    private PeriodicEvent parent;

    public PeriodicEventInstance(LocalDate startingDate, LocalDate endingDate, int frequency, int i) {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getServices() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setParent(PeriodicEvent pev) {
        this.parent = pev;
    }

    public void edit(Optional<LocalDate> execDate, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> services, Optional<Integer> participants) {}
}
