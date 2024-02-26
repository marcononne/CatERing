package catering.businesslogic.menu;

import catering.businesslogic.procedure.Recipe;

import java.util.Optional;

public class MenuItem {
    private String description;
    private MenuItem menuItem;

    public MenuItem(Recipe rec, Optional<String> desc) {
        if (desc.isPresent()) {
            this.description = desc.get();
        }
        this.menuItem = this;
        /* operations regarding recipe */
    }

    public MenuItem(MenuItem mi) {
        this.menuItem = mi;
    }
}
