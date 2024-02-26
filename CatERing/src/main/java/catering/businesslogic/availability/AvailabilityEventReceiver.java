package catering.businesslogic.availability;

public interface AvailabilityEventReceiver {
    void updateAvailabilityAdded(Availability newAv);
    void updateAvailabilityEdited(Availability av);
    void updateAvailabilityRemoved(Availability av);

}
