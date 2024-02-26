package catering.businesslogic.event;

public interface EventEventReceiver {
    void updateEventCreated(Event event);

    void updateEventEdited(Event event);

    void updatePeriodicEventInstanceEdited(PeriodicEvent Event, PeriodicEventInstance instance);

    void updateServiceCreated(Event event, Service service);

    void updateServiceConfirmed(Event event, Service service);
}
