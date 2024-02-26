package catering.testing;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Service;
import catering.businesslogic.event.SingleEvent;
import catering.businesslogic.exception.ShiftException;
import catering.businesslogic.exception.SummonException;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.summon.Summon;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.Task;
import catering.persistence.PersistenceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class Test {
    private static Task pizzaTask;
    private static Task suppliTask;
    private static int time;
    private static int quantity;
    private static SingleEvent event;
    private static Service service;
    private static Procedure pizza;
    private static Procedure suppli;
    private static StaffMember cookAppointee;
    private static KitchenShift pizzaShift;
    private static KitchenShift suppliShift;
    private static Shift lunchShift;
    private static Shift dinnerShift;
    private static LocalDate date;
    private static String job;

    public static void main(String[] args) throws UseCaseLogicException {
        if(CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            clearDB();
            setUpCommonInstancesForTest();

            /* Step 1 */
            try {
                System.out.println("\n\n\t--About to create a new summary sheet for event with id = " + event.getId() + " and service with id = " + service.getId() + "--");
                SummarySheet currentSheet = CatERing.getInstance().getTaskManager().createSummarySheet(event, service);
                System.out.println(currentSheet.toString());
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }

            /* Step 2 */
            try {
                System.out.println("\n\n\t--About to create a task referencing procedure with id = " + pizza.getId() + "--");
                pizzaTask = CatERing.getInstance().getTaskManager().addTask(pizza);
                System.out.println(pizzaTask.toString());
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }

            /* Step 3 */
            try {
                suppliTask = CatERing.getInstance().getTaskManager().addTask(suppli);
                System.out.println("\n\n\t--Just added suppli task to SummarySheet, here are the tasks saved in it:--");
                for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                    System.out.println(t.toString());
                }
                System.out.println("\t--About to add priority to task " + suppliTask.getId() + ", now is " + suppliTask.getPriority() + "--");
                CatERing.getInstance().getTaskManager().assignPriority(suppliTask, 10);
                System.out.println("\t--Just edited the task, here are the tasks saved in the Summary Sheet:--");
                for(Task t: CatERing.getInstance().getTaskManager().getCurrentSheet().getTasks()) {
                    System.out.println(t.toString());
                }
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }

            /* Step 4 */
            System.out.println("\n\n\t--About to fetch kitchen shifts in date 2024-02-20. Only shift with id: " + lunchShift.getId() + " and id: " + dinnerShift.getId() + " are good for us:--");
            ArrayList<Shift> kitchenShifts = CatERing.getInstance().getShiftManager().getKitchenShifts(LocalDate.parse("2024-02-20"));
            for (Shift s : kitchenShifts) {
                System.out.println("kitchen Shift: " + s.toString());
            }

            /* Step 5 with the 'job' parameter */
            System.out.println("\n\n\t--About to fetch all summons for date: " + date + " and job: " + job + "--");
            ArrayList<Summon> resWithJob = CatERing.getInstance().getSummonManager().getSummons(date, Optional.ofNullable(job));
            for (Summon s: resWithJob) {
                System.out.println(s.toString());
            }

           /* Step 5 without the 'job' parameter */
            System.out.println("\n\n\t--About to fetch all summons for date: " + date + " without job--");
            ArrayList<Summon> resWithoutJob = CatERing.getInstance().getSummonManager().getSummons(date, Optional.empty());
            for (Summon s: resWithoutJob) {
                System.out.println(s.toString());
            }

            /* Step 6 without the 'cook' parameter */
            try {
                System.out.println("\n\n\t--About to assign task "+pizzaTask.getId()+" in shift "+pizzaShift.getId()+" without cook--");
                CatERing.getInstance().getTaskManager().assignTask(pizzaTask, pizzaShift, Optional.empty());
                System.out.println("Cook for task "+pizzaTask.getId()+" should be null: " + pizzaTask.getCook());
                System.out.println("Shift for task "+pizzaTask.getId()+": " + pizzaTask.getShift().toString());
            } catch (UseCaseLogicException | SummonException e) {
                System.out.println(e.getMessage());
            }

            /* Step 6 with the 'cook' parameter */
            try {
                System.out.println("\n\n\tAbout to assign task "+suppliTask.getId()+" in shift "+suppliShift.getId() + " to cook "+cookAppointee.getId() + "--");
                CatERing.getInstance().getTaskManager().assignTask(suppliTask, suppliShift, Optional.ofNullable(cookAppointee));
                System.out.println("Cook for task "+suppliTask.getId()+": " + suppliTask.getCook().toString());
                System.out.println("Shift for task "+suppliTask.getId()+": " + suppliTask.getShift().toString());
            } catch (UseCaseLogicException | SummonException e) {
                System.out.println(e.getMessage());
            }

            /* Step 7 */
            try {
                System.out.println("\n\n\t--About to set estimated time = " + time + " minutes, and quantity = " + quantity + " portions to task with id: " + suppliTask.getId() + "--");
                System.out.println("Before: " + suppliTask);
                CatERing.getInstance().getTaskManager().setEstTimeAndQuantity(suppliTask, time, quantity);
                System.out.println("After: " + suppliTask);
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            throw new UseCaseLogicException("Login failed");
        }
    }

    public static void setUpCommonInstancesForTest() {
        event = (SingleEvent) CatERing.getInstance().getEventManager().createEvent("Marco", "Cena Aziendale", LocalDate.parse("2024-10-02"),
            Optional.empty(), 1, Optional.empty(),"Peperino", LocalTime.parse("20:00:00"), LocalTime.parse("22:00:00"),
            150, Optional.empty(), "Pizzata");
        event.setChef(CatERing.getInstance().getUserManager().getCurrentUser());
        service = CatERing.getInstance().getEventManager().createService("Dinner", event.getParticipants(), event.getLocation(),
            event.getStartingDate(), event.getStartingTime(), event.getEndingTime());

        Menu menu = CatERing.getInstance().getMenuManager().createMenu("Savory Dinner");
        CatERing.getInstance().getMenuManager().setService(menu, service);

        StaffMember pizzaOwner = CatERing.getInstance().getStaffMemberManager().createMember("Gino Sorbillo", "Cook", "Mastro pizzaiolo");
        StaffMember suppliOwner = CatERing.getInstance().getStaffMemberManager().createMember("Alessandro Bologna", "Cook", "Cook");
        cookAppointee = CatERing.getInstance().getStaffMemberManager().createMember("Bruno Barbieri", "Cook", "Cook");

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

        pizza = CatERing.getInstance().getProcedureManager().createProcedure("Pizza margherita",
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

        suppli = CatERing.getInstance().getProcedureManager().createProcedure("Suppli",
            CatERing.getInstance().getUserManager().getCurrentUser(), suppliOwner, 10, ingredients, ingredientsQty,
            "Piatti romani", "Tradizione romana", instructions);

        Summon cookAppointeeSummon = CatERing.getInstance().getSummonManager().createSummon(event.getStartingDate(),
                event.getStartingDate(), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        CatERing.getInstance().getSummonManager().setMemberInSummon(cookAppointeeSummon, cookAppointee);

        pizzaShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
            event.getStartingDate(), event.getLocation());
        suppliShift = (KitchenShift) CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"),
            event.getStartingDate(), event.getLocation());
        CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("05:00:00"), LocalTime.parse("10:00:00"), LocalDate.parse("2024-02-14"), "Dubai");
        lunchShift = CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("11:00:00"), LocalTime.parse("15:00:00"), LocalDate.parse("2024-02-20"), "Aula Studio");
        dinnerShift = CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"), LocalDate.parse("2024-02-20"), "Palestra Orange Invorio");
        try {
            CatERing.getInstance().getShiftManager().updateShift(dinnerShift);
        } catch (ShiftException e) {
            System.out.println(e.getMessage());
        }
        CatERing.getInstance().getShiftManager().createShift("Service", LocalTime.parse("11:00:00"), LocalTime.parse("17:00:00"), LocalDate.parse("2024-02-20"), "Aula Studio");

        //date = LocalDate.parse("2024-01-31"); //summ1
        //date = LocalDate.parse("2024-01-30"); //summ1 e summ2
        date = LocalDate.parse("2024-02-08"); //summ3 e summ4
        //job = "Waiter"; //summ1 e summ3
        job = "Cook"; //summ2 e summ4
        Summon summ1 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-01-30"), LocalDate.parse("2024-02-01"), LocalTime.parse("12:00:00"), LocalTime.parse("17:00:00"));
        StaffMember member1 = CatERing.getInstance().getStaffMemberManager().createMember("Tullo Ostilio", "Waiter", "Pours wine");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ1, member1);

        Summon summ2 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-01-30"), LocalDate.parse("2024-01-30"), LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"));
        StaffMember member2 = CatERing.getInstance().getStaffMemberManager().createMember("Anco Marzio", "Cook", "Cook");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ2, member2);

        Summon summ3 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-02-07"), LocalDate.parse("2024-02-09"), LocalTime.parse("13:00:00"), LocalTime.parse("16:00:00"));
        StaffMember member3 = CatERing.getInstance().getStaffMemberManager().createMember("Tarquinio Prisco", "Waiter", "Takes away dirty dishes");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ3, member3);

        Summon summ4 = CatERing.getInstance().getSummonManager().createSummon(LocalDate.parse("2024-02-04"), LocalDate.parse("2024-02-14"), LocalTime.parse("13:00:00"), LocalTime.parse("16:00:00"));
        StaffMember member4 = CatERing.getInstance().getStaffMemberManager().createMember("Servio Tullio", "Cook", "Cook");
        CatERing.getInstance().getSummonManager().setMemberInSummon(summ4, member4);

        time = 90;
        quantity = 10;
    }

    public static void clearDB() {
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.SummarySheetTasks");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.SummarySheets");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.SummarySheets AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Menu");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Menu AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Services");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Services AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Events");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Events AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.MembersTasks");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.TasksInShifts");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.MembersShifts");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Shifts");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Shifts AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Tasks");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Tasks AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.MembersSummons");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Summons");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Summons AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Procedures");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.Procedures AUTO_INCREMENT = 0");
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.StaffMembers");
        PersistenceManager.myExecuteUpdate("ALTER TABLE catering.StaffMembers AUTO_INCREMENT = 0");
    }
}
