package catering.businesslogic.availability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AvailabilityManager {
    private final ArrayList<AvailabilityEventReceiver> receivers;
    private final ArrayList<Availability> availabilityBoard;

    public AvailabilityManager() {
        receivers = new ArrayList<>();
        availabilityBoard = new ArrayList<>();
    }

    public void addReceiver(AvailabilityEventReceiver aver) {
        receivers.add(aver);
    }

    public void removeReceiver(AvailabilityEventReceiver aver) {
        receivers.remove(aver);
    }

    private void notifyAvailabilityAdded(Availability av) {}
    private void notifyAvailabilityEdited(Availability av) {}
    private void notifyAvailabilityRemoved(Availability av) {}

    public ArrayList<Availability> getAvailabilityBoard() { return availabilityBoard; }

    public Availability addAvailability(LocalDate stDate, LocalDate endDate, LocalTime stTime, LocalTime endTime) {
        Availability newAv = new Availability(stDate, endDate, stTime, endTime);
        availabilityBoard.add(newAv);
        return newAv;
    }

    public void removeAvailability(Availability av) {
        availabilityBoard.remove(av);
    }
}
