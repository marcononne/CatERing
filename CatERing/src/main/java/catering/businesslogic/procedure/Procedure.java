package catering.businesslogic.procedure;

import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import java.util.ArrayList;

public class Procedure {
    private final String name;
    private final int resultingQty;
    private final ArrayList<String> ingredients;
    private final ArrayList<Integer> ingredientsQty;
    private final String tag;
    private final String description;
    private final ArrayList<String> instructions;
    private final boolean published;
    private int id;
    private StaffMember owner;
    private User author;

    public Procedure(String name, int resultingQty, ArrayList<String> ingredients, ArrayList<Integer> ingredientsQty, String tag, String description, ArrayList<String> instructions) {
        this.name = name;
        this.resultingQty = resultingQty;
        this.ingredients = ingredients;
        this.ingredientsQty = ingredientsQty;
        this.tag = tag;
        this.description = description;
        this.instructions = instructions;
        this.published = false;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setOwner (StaffMember owner) {
        this.owner = owner;
    }

    public void saveProcedure() {
        String fullListOfIngredients = "", fullListOfIngredientsQuantity = "", fullListOfInstructions = "";

        fullListOfIngredients = String.join(",", ingredients);
        fullListOfIngredientsQuantity = ingredientsQty.stream()
            .map(Object::toString)
            .reduce((s1, s2) -> s1 + ", " + s2)
            .orElse("");
        fullListOfInstructions = String.join(",", instructions);

        String query = "INSERT INTO catering.Procedures" +
            " (id, owner, author, name, resultingQty, ingredientsQty, ingredients, tag, description, instructions, published)" +
            " VALUES ("+null+", '"+owner.getId()+"', " +
            " '"+author.getId()+"' , '"+name+"', '"+resultingQty+"', '"+fullListOfIngredientsQuantity+"', " +
            " '"+fullListOfIngredients+ "', '"+tag+"', '"+description+"', '"+fullListOfInstructions+"',  " +
            " '"+(published ? 1:0)+"' )";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public int getId() {
        return id;
    }
}
