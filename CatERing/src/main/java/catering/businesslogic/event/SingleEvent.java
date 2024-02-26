package catering.businesslogic.event;

import catering.businesslogic.CatERing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class SingleEvent extends Event {
    private static final SingleEventManager manager = CatERing.getInstance().getSingleEventManager();
    public SingleEvent(String customer, String name, LocalDate sDate, int services, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        super(manager, customer, name, sDate, location, sTime, eTime, participants, services, notes, addDocs);
    }

    public void edit(Optional<LocalDate> sDate, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {}
}
