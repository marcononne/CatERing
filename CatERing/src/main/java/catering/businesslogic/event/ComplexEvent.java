package catering.businesslogic.event;

import catering.businesslogic.CatERing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ComplexEvent extends Event {
    private static final ComplexEventManager manager = CatERing.getInstance().getComplexEventManager();
    private LocalDate endingDate;
    public ComplexEvent(String customer, String name, LocalDate sDate, LocalDate eDate, int services, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        super(manager, customer, name, sDate, location, sTime, eTime, participants, services, notes, addDocs);
        this.endingDate = eDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public void edit(Optional<LocalDate> sDate, Optional<LocalDate> eDate, Optional<Integer> services, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {}
}
