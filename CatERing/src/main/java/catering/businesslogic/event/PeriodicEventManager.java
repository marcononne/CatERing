package catering.businesslogic.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class PeriodicEventManager extends EventManager {
    public static Event createPeriodicEvent(String customer, String name, LocalDate sDate, LocalDate eDate, int services, int frequency, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        return new PeriodicEvent(customer, name, sDate, eDate, services, frequency, location, sTime, eTime, participants, notes, addDocs);
    }

    public void editPeriodicEvent(PeriodicEvent event, Optional<LocalDate> sDate, Optional<LocalDate> eDate, Optional<Integer> services, Optional<Integer> frequency, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {
        event.edit(event, sDate, eDate, services, frequency, location, sTime, eTime, participants);
    }

    public void editPeriodicEventInstance(PeriodicEvent pev, PeriodicEventInstance instance, Optional<LocalDate> execDate, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> services, Optional<Integer> participants) {}

    private void notifyPeriodicEventInstanceEdited(PeriodicEvent pev, PeriodicEventInstance instance) {}
}
