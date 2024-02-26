package catering.businesslogic.user;

import catering.businesslogic.event.Event;
import catering.businesslogic.exception.UserException;
import catering.businesslogic.summon.Summon;

import java.util.ArrayList;

public class UserManager {
    private User currentUser;
    private final ArrayList<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public boolean fakeLogin(String name) {
        try {
            this.currentUser = User.fakeLogin(name);
            users.add(currentUser);
        } catch (UserException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setAdminForEvent(User user, Event event) { }

    public void addSummon(User user, Summon summon) { }
}
