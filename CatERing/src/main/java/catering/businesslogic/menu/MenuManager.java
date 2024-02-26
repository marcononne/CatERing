package catering.businesslogic.menu;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.Optional;

public class MenuManager {
    private final ArrayList<MenuEventReceiver> receivers;
    private String[] features;
    private ArrayList<Procedure> recipeBook;
    private Menu newMenu;

    public MenuManager() {
        this.receivers = new ArrayList<>();
        /* this.recipeBook = CatERing.getInstance().getProcedureManager().getRecipeBook(); */
    }

    private void notifySectionAdded(Menu m, Section sec) {}

    private void notifyMenuItemAdded(Menu m, MenuItem mi) {}

    public void addReceiver(MenuEventReceiver mer) { receivers.add(mer); }

    private void notifyMenuCreated(Menu m, int servId) {
        for(MenuEventReceiver mer: receivers) {
            mer.updateMenuCreated(m, servId);
        }
    }

    private void notifyMenuDeleted(Menu m) { }

    private void notifyMenuApproved(Menu m) { }


    public void setMenuInUse(Menu m, boolean val) { }

    public Section defineSection(String name) { return null; }

    public void insertMenuItem(Recipe recipe, Optional<Section> sec, Optional<String> desc) {
        if(sec.isPresent()) {
            /* operations */
        }
        if (desc.isPresent()) {
            /* operations */
        }
        /* operations regarding recipe */
    }

    public Menu createMenu(String title) {
        User currUser = CatERing.getInstance().getUserManager().getCurrentUser();
        newMenu = new Menu(currUser, title, null);
        return newMenu;
    }

    public void setService(Menu m, Service serv) {
        m.setService(serv);
        serv.setMenu(m);
        notifyMenuCreated(m, serv.getId());
    }

    public ArrayList<Procedure> getRecipeBook() { return recipeBook;}
}
