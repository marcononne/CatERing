package catering.businesslogic.user;

import catering.businesslogic.event.Event;
import catering.businesslogic.exception.UserException;
import catering.businesslogic.summon.Summon;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class User {
    private final String name;
    private final String role;

    private final ArrayList<Event> events;

    private ArrayList<Summon> summons;
    private static int id;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
        this.events = new ArrayList<>();
        if(role.equals("admin")) {
            this.summons = new ArrayList<>();
        }
    }

    public boolean isChef() {
        return role.equals("Chef");
    }

    public boolean isAdmin() {
        return role.equals("Admin");
    }

    public ArrayList<Event> getEvents() {
        return events;
    }


    public void addEvent(Event event) {
        events.add(event);
    }

    public void addSummon(Summon summon) throws UserException {
        if(role.equals("admin")) {
            summons.add(summon);
        } else {
            throw new UserException("Error in addSummon: the chef is not responsible for summons");
        }
    }

    public static User fakeLogin(String name) throws UserException {
        AtomicReference<User> u = new AtomicReference<>();
        PersistenceManager.myExecuteQuery("SELECT * FROM catering.Users WHERE name = '" + name + "' AND role = 'Chef'", rs -> {
            id = rs.getInt(1);
            u.set(new User(rs.getString(2), rs.getString(3)));
        });
        if (u.get() == null) {
            throw new UserException("\n\nUNAUTHORIZED OR ABSENT USER IN DB\n");
        }
        return u.get();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User id = "+id+" {" +
            " name= '" + name + '\'' +
            " role= '" + role + '\'' +
            '}';
    }
}
