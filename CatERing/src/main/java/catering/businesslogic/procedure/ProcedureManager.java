package catering.businesslogic.procedure;

import java.util.ArrayList;
import catering.businesslogic.user.User;
import catering.businesslogic.staffMember.StaffMember;

public class ProcedureManager {
    private final ArrayList<Procedure> recipeBook;

    private final ArrayList<ProcedureEventReceiver> receivers;

    public ProcedureManager() {
        this.recipeBook = new ArrayList<>();
        this.receivers = new ArrayList<>();
    }

    public void addReceiver(ProcedureEventReceiver per) {
        receivers.add(per);
    }
    public ArrayList<Procedure> getRecipeBook() {
        return recipeBook;
    }

    public Procedure createProcedure(String name, User author, StaffMember owner, int resultingQty, ArrayList<String> ingredients, ArrayList<Integer> ingredientsQty, String tag, String description, ArrayList<String> instructions) {
        Procedure proc = new Procedure(name, resultingQty, ingredients, ingredientsQty, tag, description, instructions);
        proc.setAuthor(author);
        proc.setOwner(owner);
        recipeBook.add(proc);
        notifyProcedureCreated(proc);
        return proc;
    }

    public void notifyProcedureCreated(Procedure proc) {
        for(ProcedureEventReceiver per : receivers) {
            per.updateProcedureCreated(proc);
        }
    }
}
