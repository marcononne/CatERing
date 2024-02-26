package catering.businesslogic.menu;

import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Optional;

public class Menu {
    private final User owner;
    private final String title;
    private boolean published;
    private boolean inUse;
    private boolean cookRequired;
    private boolean onSiteMeals;
    private boolean kitchenRequired;
    private boolean buffet;
    private boolean fingerFood;
    private final String[] features;
    private boolean[] featureValues;
    private ArrayList<Section> sections;
    private int id;
    private Service service;

    public Menu(User owner, String title, String[] features) {
        this.owner = owner;
        this.title = title;
        this.features = features;
        this.sections = new ArrayList<>();
    }

    public void setMenuInUse(boolean val) {
        this.inUse = val;
    }

    public void addSection(String name) {
        Section newSec = new Section(name);
    }

    public void addItem(Recipe recipe, Optional<Section> sec, Optional<String> desc){
        if(sec.isPresent()) {
            /* operations */
        }
        if (desc.isPresent()) {
            /* operations */
        }
        /* operations regarding recipe */
    }

    public void saveMenu(Menu menu, int servId) {
        String query = "INSERT INTO catering.Menu VALUES ("+null+", '"+menu.title+"', '"+(menu.published ? 1:0)+"', '"+(menu.inUse ? 1:0)+"', '"+(menu.cookRequired ? 1:0)+"', '"+(menu.onSiteMeals ? 1:0)+"', '"+(menu.kitchenRequired ? 1:0)+"', '"+(menu.buffet ? 1:0)+"', '"+(menu.fingerFood ? 1:0)+"', '"+servId+"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public void setService(Service serv) { this.service = serv; }

    public boolean isInUse() { return this.inUse; }

    public boolean containsSection(Section sec) { return false; }

    public ArrayList<Procedure> getNeededRecipes() { return null; }

    public String getTitle() { return this.title; }
}
