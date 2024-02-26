package catering.businesslogic.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ComplexEventManager extends EventManager {
    public static Event createComplexEvent(String customer, String name, LocalDate sDate, LocalDate eDate, int services, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        return new ComplexEvent(customer, name, sDate, eDate, services, location, sTime, eTime, participants, notes, addDocs);
    }

    public void editComplexEvent(ComplexEvent event, Optional<LocalDate> sDate, Optional<LocalDate> eDate, Optional<Integer> services, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {
        event.edit(sDate, eDate, services, location, sTime, eTime, participants);
    }
}
