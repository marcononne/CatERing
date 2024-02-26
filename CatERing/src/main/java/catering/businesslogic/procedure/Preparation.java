package catering.businesslogic.procedure;

import java.util.ArrayList;

public class Preparation extends Procedure {

    public Preparation(String name, int resultingQty, ArrayList<String> ingredients, ArrayList<Integer> ingredientsQty, String tag, String description, ArrayList<String> instructions) {
        super(name, resultingQty, ingredients, ingredientsQty, tag, description, instructions);
    }

}
