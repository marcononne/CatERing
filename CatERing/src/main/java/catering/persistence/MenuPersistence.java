package catering.persistence;

import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.MenuEventReceiver;
import catering.businesslogic.menu.Section;

public class MenuPersistence implements MenuEventReceiver {
    @Override
    public void updateSectionAdded(Menu m, Section sec) {}

    @Override
    public void updateMenuItemAdded(Menu m, Section sec) {}

    @Override
    public void updateMenuCreated(Menu m, int servId) {
        m.saveMenu(m, servId);
    }

    @Override
    public void updateMenuDeleted(Menu m) {}

    @Override
    public void updateMenuApproved(Menu m) {}
}
