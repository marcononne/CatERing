package catering.businesslogic.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class SingleEventManager extends EventManager {
    public static Event createSingleEvent(String customer, String name, LocalDate sDate, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        return new SingleEvent(customer, name, sDate, 1, location, sTime, eTime, participants, notes, addDocs);
    }

    public void editSingleEvent(SingleEvent event, Optional<LocalDate> sDate, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {
        event.edit(sDate, location, sTime, eTime, participants);
    }
}
