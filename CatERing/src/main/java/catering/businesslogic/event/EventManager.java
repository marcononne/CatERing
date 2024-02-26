package catering.businesslogic.event;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.summon.Summon;
import catering.businesslogic.task.Task;
import catering.businesslogic.user.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class EventManager {
    private Event currentEvent;
    private final ArrayList<Event> events;
    private final ArrayList<EventEventReceiver> receivers;

    public EventManager() {
        this.events = new ArrayList<>();
        this.receivers = new ArrayList<>();
    }

    public Event createEvent(String customer, String name, LocalDate sDate, Optional<LocalDate> eDate, int services, Optional<Integer> frequency, String location, LocalTime sTime, LocalTime eTime, int participants, Optional<String> notes, String addDocs) {
        Event ev;
        if(frequency.isPresent() && eDate.isPresent()) {
            ev = PeriodicEventManager.createPeriodicEvent(customer, name, sDate, eDate.get(), services, frequency.get(), location, sTime, eTime, participants, notes, addDocs);
        } else if (eDate.isPresent()) {
            ev = ComplexEventManager.createComplexEvent(customer, name, sDate, eDate.get(), services, location, sTime, eTime, participants, notes, addDocs);
        } else {
            ev = SingleEventManager.createSingleEvent(customer, name, sDate, location, sTime, eTime, participants, notes, addDocs);
        }
        currentEvent = ev;
        notifyEventCreated(ev);
        return ev;
    }

    public void editEvent(Event event, Optional<LocalDate> sDate, Optional<LocalDate> eDate, Optional<Integer> services, Optional<Integer> frequency, Optional<String> location, Optional<LocalTime> sTime, Optional<LocalTime> eTime, Optional<Integer> participants) {
        event.getManager().editEvent(event, sDate, eDate, services, frequency, location, sTime, eTime, participants);
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addReceiver(EventEventReceiver receiver) {
        receivers.add(receiver);
    }

    public void removeReceiver(EventEventReceiver receiver) {
        receivers.remove(receiver);
    }

    private void notifyEventCreated(Event event) {
        for(EventEventReceiver ev: receivers) {
            ev.updateEventCreated(event);
        }
    }

    private void notifyEventEdited(Event event) {
        for(EventEventReceiver ev: receivers) {
            ev.updateEventEdited(event);
        }
    }

    private void notifyServiceCreated(Event event, Service service) {
        for(EventEventReceiver ev: receivers) {
            ev.updateServiceCreated(event, service);
        }
    }

    private void notifyServiceConfirmed(Event event, Service service) {}

    public ArrayList<Event> getEventsPerDate(LocalDate date) {
        return events;
    }

    public Service createService(String type, int participants, String location, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Service serv = new Service(type, participants, location, date, startTime, endTime);
        setServiceForEvent(currentEvent, serv);
        notifyServiceCreated(currentEvent, serv);
        notifyEventEdited(currentEvent);
        return serv;
    }

    public void setServiceForEvent(Event event, Service service) {
        event.addService(service);
    }

    public Summon convokeStaff(Service currService, StaffMember person, LocalDate sDate, LocalDate eDate, LocalTime sTime, LocalTime eTime) {
        return new Summon(sDate, eDate, sTime, eTime);
    }

    public void approveMenu(Service service) {}

    public void setServiceStatus(Service service, String status) {}

    public void setEventStatus(Event event, String status) {}

    public void cancelEvent(Event event, Optional<String> notes, Optional<String> addDocs) {}

    public void deleteEvent(Event event) {}

    public void closeEvent(Event event, Optional<String> notes, Optional<String> addDocs) {}

    public void assignChefForEvent(Event event, User chef) {}

    public void editChefForEvent(Event event, User chef) {}

    public void assignTaskForServiceOfEvent(Event event, Service service, StaffMember person, Optional<Task> task, Optional<String> role, Shift shift) {}
}
