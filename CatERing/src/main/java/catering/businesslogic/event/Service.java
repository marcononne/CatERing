package catering.businesslogic.event;

import catering.businesslogic.CatERing;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.task.SummarySheet;
import catering.persistence.PersistenceManager;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Service {
    private final String type;
    private String status;
    private final int participants;
    private final String location;
    private final LocalDate date;
    private final LocalTime startingTime;
    private final LocalTime endingTime;
    private final ArrayList<UpdateSuggestion> suggestions;
    private int id;
    private SummarySheet currentSheet;

    private Menu menu;

    public Service(String type, int participants, String location, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.type = type;
        this.status = "pending";
        this.participants = participants;
        this.location = location;
        this.date = date;
        this.startingTime = startTime;
        this.endingTime = endTime;
        this.suggestions = new ArrayList<>();
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setSummSheet(SummarySheet newSheet) {
        this.currentSheet = newSheet;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
    this.status = status;
  }

    public int getParticipants() {
        return participants;
    }

    public String getLocation() {
        return location;
    }
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public int getId() {
        return id;
    }

    public void saveService() {
        String query = "INSERT INTO catering.Services VALUES ("+null+", '"
            + CatERing.getInstance().getEventManager().getCurrentEvent().getId() +"', '"+type+"', '"+status+"', " +
            "'"+participants+"', '"+location+ "', '"+ Date.valueOf(date)+"', " +
            "'"+ Time.valueOf(startingTime)+"', '"+Time.valueOf(endingTime)+"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
