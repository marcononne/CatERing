package catering.businesslogic.event;

import catering.businesslogic.CatERing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class PeriodicEvent extends Event {

    private static final PeriodicEventManager manager = CatERing.getInstance().getPeriodicEventManager();
    private final LocalDate endingDate;
    private final int frequency;

    public PeriodicEvent(String customer, String name, LocalDate sDate, LocalDate eDate, int services, int frequency, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        super(manager, customer, name, sDate, location, sTime, eTime, participants, services, notes, addDocs);
        this.endingDate = eDate;
        this.frequency = frequency;
    }

    public void edit(PeriodicEvent event, Optional<LocalDate> sDate, Optional<LocalDate> eDate, Optional<Integer> services, Optional<Integer> frequency, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {}

    public void editInstance(PeriodicEventInstance instance, Optional<LocalDate> execDate, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> services, Optional<Integer> participants) {
        instance.edit(execDate, location, sTime, eTime, services, participants);
    }
}
