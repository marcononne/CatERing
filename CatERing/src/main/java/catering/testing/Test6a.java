package catering.testing;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Service;
import catering.businesslogic.event.SingleEvent;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class Test6a {
    private static Task pizzaTask;
    private static Task suppliTask;

    public static void main(String[] args) throws UseCaseLogicException {
        if(CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            Test.clearDB();
            setUpInstancesForTest();
            try {
                System.out.println("\n\n\t--About to edit quantity in task " + pizzaTask.getId() + ", decrease by 100--");
                CatERing.getInstance().getTaskManager().markProcedureReady(pizzaTask, 100);
                System.out.println("\n\n\t--About to edit quantity in task " + suppliTask.getId() + ", decrease by 100--");
                CatERing.getInstance().getTaskManager().markProcedureReady(suppliTask, 100);

                System.out.println("\n\n\t--Tasks in the current summary sheet after editing:--");
                for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                  System.out.println(t.toString());
                }
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new UseCaseLogicException("Login failed");
        }
    }

    public static void setUpInstancesForTest() {
        SingleEvent event = (SingleEvent) CatERing.getInstance().getEventManager().createEvent("Marco", "Cena Aziendale", LocalDate.parse("2024-10-02"),
          Optional.empty(), 1, Optional.empty(),"Peperino", LocalTime.parse("20:00:00"), LocalTime.parse("22:00:00"),
          150, Optional.empty(), "Pizzata");
        event.setChef(CatERing.getInstance().getUserManager().getCurrentUser());
        Service service = CatERing.getInstance().getEventManager().createService("Dinner", event.getParticipants(), event.getLocation(),
          event.getStartingDate(), event.getStartingTime(), event.getEndingTime());
        Menu menu = CatERing.getInstance().getMenuManager().createMenu("Savory Dinner");
        CatERing.getInstance().getMenuManager().setService(menu, service);

        StaffMember pizzaOwner = CatERing.getInstance().getStaffMemberManager().createMember("Gino Sorbillo", "Cook", "Mastro pizzaiolo");
        StaffMember suppliOwner = CatERing.getInstance().getStaffMemberManager().createMember("Alessandro Bologna", "Cook", "Cook");

        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Acqua");
        ingredients.add("Sale");
        ingredients.add("Farina");
        ingredients.add("Lievito");
        ingredients.add("Passata");
        ingredients.add("Mozzarella");
        ingredients.add("Olio");
        ingredients.add("Basilico");

        ArrayList<Integer> ingredientsQty = new ArrayList<>();
        ingredientsQty.add(100);
        ingredientsQty.add(1);
        ingredientsQty.add(400);
        ingredientsQty.add(7);
        ingredientsQty.add(400);
        ingredientsQty.add(1);
        ingredientsQty.add(15);
        ingredientsQty.add(1);

        ArrayList<String>instructions = new ArrayList<>();
        instructions.add("In una ciotola mescolare acqua e lievito. Lasciare riposare per circa 5 minuti. ");
        instructions.add("In un altra ciotola, mescolare farina e sale. Fare un pozzo al centro e versare il composto di lievito e l olio d oliva. ");
        instructions.add("Mescolare fino a formare un impasto, quindi impastare su superficie infarinata per circa 5-7 min fino a quando diventa liscio ed elastico. ");
        instructions.add("Mettere l impasto in una ciotola leggermente unta, coprirlo con un panno umido e lasciarlo lievitare fino a quando raddoppia di volume. ");
        instructions.add("Mettere in una pentola la passata, il basilico e il sale. Cuocere  fuoco basso per  15-20 minuti. ");
        instructions.add("Preriscaldare il forno a 245°C. ");
        instructions.add("Sgonfiare l impasto lievitato e stenderlo su una superficie infarinata alla spessore desiderato. ");
        instructions.add("Trasferire l impasto steso su una teglia coperta da carta da forno. Spalmare uno strato di salsa sulla pizza e aggiungere la mozzarella tagliata.");
        instructions.add("Mettere la teglia in forno e cuocere per circa 12-15 minuti. ");

        Procedure pizza = CatERing.getInstance().getProcedureManager().createProcedure("Pizza margherita",
            CatERing.getInstance().getUserManager().getCurrentUser(), pizzaOwner, 1, ingredients, ingredientsQty,
            "Pizza", "Pizza al forno", instructions);

        ingredients.clear();
        ingredientsQty.clear();
        instructions.clear();

        ingredients.add("Riso al pomodoro");
        ingredients.add("Mozzarella");
        ingredients.add("Uova");
        ingredients.add("Pangrattato");
        ingredients.add("Olio di semi");
        ingredients.add("Formaggio grattugiato");
        ingredients.add("Pepe");

        ingredientsQty.add(300);
        ingredientsQty.add(150);
        ingredientsQty.add(3);
        ingredientsQty.add(20);
        ingredientsQty.add(500);
        ingredientsQty.add(50);
        ingredientsQty.add(2);

        instructions.add("Tagliare la mozzarella in bastoncini di circa 5 cm. ");
        instructions.add("In una ciotola unire il risotto al pomodoro con un uovo, formaggio grattugiato e pepe e poi mescolare. ");
        instructions.add("Con le mani umide prendere un po di impasto, allargarlo sulla mano, sistemare nel mezzo un bastoncino di mozzarella e ricoprire con altro risotto. ");
        instructions.add("Aprire le uova e sbatterle in un piatto. ");
        instructions.add("Passare i supplì nell uovo e successivamente nel pangrattato, ripetere nuovamente così da avere una doppia panatura. ");
        instructions.add("In una pentola capiente mettere abbondante olio di semi. ");
        instructions.add("Non appena l olio sarà ben caldo, immergere i supplì e lasciarli friggere per 3-4 minuti, rigirandoli di tanto in tanto. ");
        instructions.add("Adagiare i suppli su carta assorbente per asciugare l’eccesso di olio. ");

        Procedure suppli = CatERing.getInstance().getProcedureManager().createProcedure("Suppli",
            CatERing.getInstance().getUserManager().getCurrentUser(), suppliOwner, 10, ingredients, ingredientsQty,
            "Piatti romani", "Tradizione romana", instructions);

        try {
            CatERing.getInstance().getTaskManager().createSummarySheet(event, service);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }

        try {
            pizzaTask = CatERing.getInstance().getTaskManager().addTask(pizza);
            CatERing.getInstance().getTaskManager().setEstTimeAndQuantity(pizzaTask, 5, 150);
            suppliTask = CatERing.getInstance().getTaskManager().addTask(suppli);
            CatERing.getInstance().getTaskManager().setEstTimeAndQuantity(suppliTask, 5, 100);
            System.out.println("\n\n\t--Tasks in the current summary sheet before editing:--");
            for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                System.out.println(t.toString());
            }
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
