package catering.businesslogic.menu;

public class Section {
    private String name;
    private Section sec;

    public Section(String name) {
        this.name = name;
        this.sec = this;
    }

    public Section(Section sec ) {
        this.sec = sec;
    }

    public void addItem(MenuItem mi) {}
}
