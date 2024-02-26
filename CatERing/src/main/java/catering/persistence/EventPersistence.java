package catering.persistence;

import catering.businesslogic.event.*;

public class EventPersistence implements EventEventReceiver {
    @Override
    public void updateEventCreated(Event event) {
        event.saveEvent();
    }

    @Override
    public void updateEventEdited(Event event) {
        event.saveEventEdited();
    }

    @Override
    public void updatePeriodicEventInstanceEdited(PeriodicEvent Event, PeriodicEventInstance instance) {}

    @Override
    public void updateServiceCreated(Event event, Service service) {
        event.saveService(service);
    }

    @Override
    public void updateServiceConfirmed(Event event, Service service) {}
}
