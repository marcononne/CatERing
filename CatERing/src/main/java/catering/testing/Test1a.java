package catering.testing;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Service;
import catering.businesslogic.event.SingleEvent;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.task.SummarySheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class Test1a {
    private static SingleEvent event;
    private static Service service;

    public static void main(String[] args) throws UseCaseLogicException{
        if(CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            Test.clearDB();
            setUpInstancesForTest();
            try {
                System.out.println("\n\n\t--About to fetch an existing summary sheet for event with id = " + event.getId() + " and service with id = " + service.getId() + "--");
                SummarySheet currentSheet = CatERing.getInstance().getTaskManager().openSummarySheet(event, service);
                System.out.println(currentSheet.toString());
            } catch (UseCaseLogicException e) {
                System.out.println(e.getMessage());
            }

        }
        else {
            throw new UseCaseLogicException("LOGIN FAILED!");
        }
    }


    public static void setUpInstancesForTest() {
        event = (SingleEvent) CatERing.getInstance().getEventManager().createEvent("Marco", "Cena Aziendale", LocalDate.parse("2024-10-02"),
                Optional.empty(), 1, Optional.empty(),"Peperino", LocalTime.parse("20:00:00"), LocalTime.parse("22:00:00"),
                150, Optional.empty(), "Pizzata");
        event.setChef(CatERing.getInstance().getUserManager().getCurrentUser());
        service = CatERing.getInstance().getEventManager().createService("Dinner", event.getParticipants(), event.getLocation(),
                event.getStartingDate(), event.getStartingTime(), event.getEndingTime());
        Menu menu = CatERing.getInstance().getMenuManager().createMenu("Savory Dinner");
        CatERing.getInstance().getMenuManager().setService(menu, service);
        try {
            CatERing.getInstance().getTaskManager().createSummarySheet(event, service);
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());;
        }
    }
}
