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

public class Test6b {
    private static KitchenShift pizzaShift;
    private static StaffMember member4;
    private static Task pizzaTask = null;
    private static Task suppliTask = null;

    public static void main(String[] args) throws UseCaseLogicException {
        if (CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            Test.clearDB();
            setUpForTest();

            if (CatERing.getInstance().getTaskManager().getCurrentSheet() != null) {
                try {
                    System.out.println("\n\n\t--Test con NEW TASK: --");
                    System.out.println("Old Task: " + suppliTask.toString());
                    System.out.println("Old Shift: " + suppliTask.getShift().toString());
                    System.out.println("Old Cook: " + suppliTask.getCook().toString());
                    CatERing.getInstance().getTaskManager().editSelectedTask(suppliTask, Optional.ofNullable(pizzaTask), Optional.empty(), Optional.empty());
                    System.out.println("\nNew Task: " + pizzaTask.toString());
                    System.out.println("New Shift: " + pizzaTask.getShift().toString());
                    System.out.println("New Cook: " + pizzaTask.getCook().toString());
                } catch (UseCaseLogicException | SummonException | TaskException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    System.out.println("\n\n\t--Test con NEW SHIFT: --");
                    System.out.println("Old Task: " + pizzaTask.toString());
                    System.out.println("Old Shift: " + pizzaTask.getShift().toString());
                    System.out.println("Old Cook: " + pizzaTask.getCook().toString());
                    CatERing.getInstance().getTaskManager().editSelectedTask(pizzaTask, Optional.empty(), Optional.ofNullable(pizzaShift), Optional.empty());
                    System.out.println("\nNew Task: " + pizzaTask.toString());
                    System.out.println("New Shift: " + pizzaTask.getShift().toString());
                    System.out.println("New Cook: " + pizzaTask.getCook().toString());
                } catch (UseCaseLogicException | SummonException | TaskException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    System.out.println("\n\n\t--Test con NEW COOK: --");
                    System.out.println("Old Task: " + pizzaTask.toString());
                    System.out.println("Old Shift: " + pizzaTask.getShift().toString());
                    System.out.println("Old Cook: " + pizzaTask.getCook().toString());
                    CatERing.getInstance().getTaskManager().editSelectedTask(pizzaTask, Optional.empty(), Optional.empty(), Optional.ofNullable(member4));
                    System.out.println("\nNew Task: " + pizzaTask.toString());
                    System.out.println("New Shift: " + pizzaTask.getShift().toString());
                    System.out.println("New Cook: " + pizzaTask.getCook().toString());
                } catch (UseCaseLogicException | SummonException | TaskException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                throw new UseCaseLogicException("Edit Task - No summary sheet found");
            }
        }
        else {
            throw new UseCaseLogicException("LOGIN FAILED!");
        }
    }

    public static void setUpForTest() {
        SingleEvent event = (SingleEvent) CatERing.getInstance().getEventManager().createEvent("Marco", "Cena Aziendale", LocalDate.parse("2024-10-02"),
                Optional.empty(), 1, Optional.empty(), "Peperino", LocalTime.parse("20:00:00"), LocalTime.parse("22:00:00"),
                150, Optional.empty(), "Pizzata");
        event.setChef(CatERing.getInstance().getUserManager().getCurrentUser());
        Service service = CatERing.getInstance().getEventManager().createService("Dinner", event.getParticipants(), event.getLocation(),
                event.getStartingDate(), event.getStartingTime(), event.getEndingTime());

        Menu menu = CatERing.getInstance().getMenuManager().createMenu("Savory Dinner"); //qui ancora non è nel db
        CatERing.getInstance().getMenuManager().setService(menu, service);

        StaffMember pizzaOwner = CatERing.getInstance().getStaffMemberManager().createMember("Gino Sorbillo", "Cook", "Mastro pizzaiolo");
        StaffMember suppliOwner = CatERing.getInstance().getStaffMemberManager().createMember("Alessandro Bologna", "Cook", "Cook");
        StaffMember cookAppointee = CatERing.getInstance().getStaffMemberManager().createMember("Bruno Barbieri", "Cook", "Cook");

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

        ArrayList<String> instructions = new ArrayList<>();
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

        Summon cookAppointeeSummon = CatERing.getInstance().getSummonManager().createSummon(event.getStartingDate(),
                event.getStartingDate(), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        CatERing.getInstance().getSummonManager().setMemberInSummon(cookAppointeeSummon, cookAppointee);
        Summon cookAppointeeSummon2 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-10-03"),
                LocalDate.parse("2024-10-03"), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        CatERing.getInstance().getSummonManager().setMemberInSummon(cookAppointeeSummon2, cookAppointee);

        pizzaShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
                LocalDate.parse("2024-10-03"), "Palestra Rosselli");
        KitchenShift suppliShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
                event.getStartingDate(), "Dubai lounge bar");

        Summon summ1 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-01-30"), LocalDate.parse("2024-02-01"), LocalTime.parse("12:00:00"), LocalTime.parse("17:00:00"));
        StaffMember member1 = CatERing.getInstance().getStaffMemberManager().createMember("Tullo Ostilio", "Waiter", "Pours wine");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ1, member1);

        Summon summ2 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-01-30"), LocalDate.parse("2024-01-30"), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        StaffMember member2 = CatERing.getInstance().getStaffMemberManager().createMember("Anco Marzio", "Cook", "Cook");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ2, member2);

        Summon summ3 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-02-07"), LocalDate.parse("2024-02-09"), LocalTime.parse("13:00:00"), LocalTime.parse("16:00:00"));
        StaffMember member3 = CatERing.getInstance().getStaffMemberManager().createMember("Tarquinio Prisco", "Waiter", "Takes away dirty dishes");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ3, member3);

        Summon summ4 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-10-01"), LocalDate.parse("2024-10-04"), LocalTime.parse("13:00:00"), LocalTime.parse("16:00:00"));
        member4 = CatERing.getInstance().getStaffMemberManager().createMember("Servio Tullio", "Cook", "Cook");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ4, member4);

        try {
            CatERing.getInstance().getTaskManager().createSummarySheet(event, service);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
        try {
            suppliTask = CatERing.getInstance().getTaskManager().addTask(suppli);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
        try {
            pizzaTask = CatERing.getInstance().getTaskManager().addTask(pizza);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
        try {
            CatERing.getInstance().getTaskManager().assignTask(suppliTask, suppliShift, Optional.ofNullable(cookAppointee));
        } catch (UseCaseLogicException | SummonException e) {
            System.out.println(e.getMessage());
        }

        try {
            CatERing.getInstance().getTaskManager().assignTask(pizzaTask, suppliShift, Optional.ofNullable(cookAppointee));
        } catch (UseCaseLogicException | SummonException e) {
            System.out.println(e.getMessage());
        }
    }
}
