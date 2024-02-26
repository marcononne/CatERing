package catering.testing;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Service;
import catering.businesslogic.event.SingleEvent;
import catering.businesslogic.exception.SummonException;
import catering.businesslogic.exception.TaskException;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.summon.Summon;
import catering.businesslogic.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class Test6d {
    private static Task pizzaTask;
    private static Task suppliTask;

    public static void main(String[] args) throws UseCaseLogicException {
        if(CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            Test.clearDB();
            setUpInstancesForTest();
            try {
                System.out.println("\n\n\t--Tasks in the current summary sheet:--");
                for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                    System.out.println(t.toString());
                    System.out.println("\tCook: " + t.getCook());
                    System.out.println("\tShift: " + t.getShift());
                }
                System.out.println("\n\t--About to delete task " + suppliTask.getId() + "--");
                CatERing.getInstance().getTaskManager().deleteTask(suppliTask, suppliTask.getShift(), Optional.empty());
                System.out.println("\tShift: " + suppliTask.getShift());

                System.out.println("\n\t--About to delete task " + pizzaTask.getId() + "--");
                CatERing.getInstance().getTaskManager().deleteTask(pizzaTask, pizzaTask.getShift(), Optional.ofNullable(pizzaTask.getCook()));
                System.out.println("\tCook: " + pizzaTask.getCook());
                System.out.println("\tShift: " + pizzaTask.getShift());

                System.out.println("\n\n\t--Tasks in the current summary sheet after deleting: --");
                for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                  System.out.println(t.toString());
                }
            } catch (UseCaseLogicException | TaskException e) {
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
        StaffMember tiramisuOwner = CatERing.getInstance().getStaffMemberManager().createMember("Iginio Massari", "Cook", "Pasticcere");

        StaffMember pizzaAppointee = CatERing.getInstance().getStaffMemberManager().createMember("Antonino Cannavaciuolo", "Cook", "Apprendista pizzaiolo");

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
            CatERing.getInstance().getUserManager().getCurrentUser(), suppliOwner,10, ingredients, ingredientsQty,
            "Piatti romani", "Tradizione romana", instructions);

        ingredients.clear();
        ingredientsQty.clear();
        instructions.clear();

        ingredients.add("Mascarpone");
        ingredients.add("Savoiardi");
        ingredients.add("Caffe");
        ingredients.add("Uova medie");
        ingredients.add("Zucchero");
        ingredients.add("Cacao in polvere");

        ingredientsQty.add(750);
        ingredientsQty.add(250);
        ingredientsQty.add(300);
        ingredientsQty.add(5);
        ingredientsQty.add(120);
        ingredientsQty.add(2);

        instructions.add("Preparare 300g di caffe e lasciar raffreddare in una ciotola bassa e ampia.");
        instructions.add("Separare in due ciotole tuorli e albumi delle uova. Montare i tuorli con una frusta, aggiungendo metà dose di zucchero gradualmente. ");
        instructions.add("Non appena il composto diventa spumoso, aggiungere gradualmente il mascarpone. ");
        instructions.add("Montare gli albumi con una frusta e aggiungere gradualmente la restante parte di zucchero. ");
        instructions.add("Aggiungere gradualmente il composto di albumi al composto di tuorli, mescolando per incorporare. ");
        instructions.add("Spennellare la base di una pirofila con un po della crema al mascarpone ottenuta. ");
        instructions.add("Inzuppare i savoiardi nel caffè per pochi secondi, per poi adagiarli nella pirofila fino a ricoprire il fondo. ");
        instructions.add("Ricoprire lo stato di savoiardi con altra crema al mascarpone e ripetere i passaggi.");
        instructions.add("Una volta terminati i biscotti e spalmato l ultimo strato di crema, spolverare con il cacao e lasciar riposare in frigo per un paio d ore. ");

        Procedure tiramisu = CatERing.getInstance().getProcedureManager().createProcedure("Tiramisu",
            CatERing.getInstance().getUserManager().getCurrentUser(), tiramisuOwner, 1, ingredients,
            ingredientsQty, "Dolce", "Il dolce migliore al mondo", instructions);

        try {
            CatERing.getInstance().getTaskManager().createSummarySheet(event, service);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }

        KitchenShift pizzaShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
            event.getStartingDate(), event.getLocation());
        KitchenShift suppliShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
            event.getStartingDate(), event.getLocation());

        Summon pizzaAppointeeSummon = CatERing.getInstance().getSummonManager().createSummon(event.getStartingDate(),
            event.getStartingDate(), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        CatERing.getInstance().getSummonManager().setMemberInSummon(pizzaAppointeeSummon, pizzaAppointee);

        try {
            pizzaTask = CatERing.getInstance().getTaskManager().addTask(pizza);
            suppliTask = CatERing.getInstance().getTaskManager().addTask(suppli);
            CatERing.getInstance().getTaskManager().addTask(tiramisu);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }

        try {
            CatERing.getInstance().getTaskManager().assignTask(pizzaTask, pizzaShift, Optional.ofNullable(pizzaAppointee));
            CatERing.getInstance().getTaskManager().assignTask(suppliTask, suppliShift, Optional.empty());
        } catch (UseCaseLogicException | SummonException e) {
            System.out.println(e.getMessage());
        }
    }
}
