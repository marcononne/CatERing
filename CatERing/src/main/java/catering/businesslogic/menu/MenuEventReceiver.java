package catering.businesslogic.menu;

public interface MenuEventReceiver {
    void updateSectionAdded(Menu m, Section sec);
    void updateMenuItemAdded(Menu m, Section sec);
    void updateMenuCreated(Menu m, int servId);
    void updateMenuDeleted(Menu m);
    void updateMenuApproved(Menu m);

}
