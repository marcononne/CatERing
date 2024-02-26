package catering.businesslogic.event;

public class UpdateSuggestion {
    private final String type;
    private final boolean approved;

    public UpdateSuggestion(String type, boolean approved) {
        this.type = type;
        this.approved = approved;
    }
}
