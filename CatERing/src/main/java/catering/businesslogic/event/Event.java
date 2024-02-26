package catering.businesslogic.event;

import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public abstract class Event {
    private EventManager manager;
    private String customer;
    private String name;
    private LocalDate startingDate;
    private String location;
    private String status;
    private final LocalTime startingTime;
    private LocalTime endingTime;
    private int participants;
    private int services;
    private final Optional<String> notes;
    private final String addDocs;
    private final ArrayList<Service> eventServices;
    private int id;
    private User chef;


    public Event(EventManager manager, String customer, String name, LocalDate startingDate, String location, LocalTime sTime, LocalTime etime, int participants, int services, Optional<String> notes, String addDocs) {
        this.manager = manager;
        this.customer = customer;
        this.name = name;
        this.startingDate = startingDate;
        this.location = location;
        this.status = "scheduled";
        this.startingTime = sTime;
        this.endingTime = etime;
        this.participants = participants;
        this.services = services;
        this.notes = notes;
        this.addDocs = addDocs;
        this.eventServices = new ArrayList<>();
    }

    public EventManager getManager() {
          return manager;
    }

    public void setChef(User user) {
        this.chef = user;
    }

    public User getChef() {
        return this.chef;
    }

    public void setManager(EventManager manager) {
        this.manager = manager;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime sTime) {
        this.startingDate = LocalDate.from(sTime);
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public void setEtime(LocalTime etime) {
        this.endingTime = etime;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getServicesNumber() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    public boolean containsService(Service service) {
        return eventServices.contains(service);
    }

    public void addService(Service service) {
        eventServices.add(service);
    }

    public void saveService(Service service) {
        service.saveService();
    }

    public void saveEvent() {
        String query = "INSERT INTO catering.Events VALUES (" + null + ", '"+customer+"', '"+name+"', " +
            "'"+ Date.valueOf(startingDate)+"', '"+location+"', '"+status+"', '"+ Time.valueOf(startingTime)+"', " +
            "'"+Time.valueOf(endingTime)+"', '"+participants+"', '"+services+"', '"+(notes.orElse("-"))+"', '"+addDocs+"' )";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public void saveEventEdited() {}

    public int getId() {
        return id;
    }
}
