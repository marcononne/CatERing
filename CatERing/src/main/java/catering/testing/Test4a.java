package catering.testing;

import catering.businesslogic.CatERing;
import catering.businesslogic.exception.ShiftException;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.Shift;

import java.time.LocalDate;
import java.time.LocalTime;

public class Test4a {
    private static Shift lunchShift;
    private static Shift dinnerShift;

    public static void main(String[] args) throws UseCaseLogicException {
        if (CatERing.getInstance().getUserManager().fakeLogin("Alessandro")) {
            Test.clearDB();
            setUpForTest();
            try {
                System.out.println("\n\n\t--Here is the kitchen shift board: --");
                for(Shift s : CatERing.getInstance().getShiftManager().getKitchenShiftBoard()) {
                    System.out.println(s);
                }
                System.out.println("\n\n\t--About to change 'full' in shifts: "+lunchShift.getId()+" and "+dinnerShift.getId()+":--\n");
                CatERing.getInstance().getShiftManager().updateShift(lunchShift);
                CatERing.getInstance().getShiftManager().updateShift(dinnerShift);
                System.out.println("\n\n\t--Here is the kitchen shift board after editing: --");
                for(Shift s : CatERing.getInstance().getShiftManager().getKitchenShiftBoard()) {
                    System.out.println(s);
                }
            } catch (ShiftException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            throw new UseCaseLogicException("LOGIN FAILED!");
        }
    }

    public static void setUpForTest() {
        Shift breakfastShift = CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("05:00:00"), LocalTime.parse("10:00:00"), LocalDate.parse("2024-02-14"), "Dubai");
        lunchShift = CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("11:00:00"), LocalTime.parse("15:00:00"), LocalDate.parse("2024-02-20"), "Aula Studio");
        dinnerShift = CatERing.getInstance().getShiftManager().createShift("Kitchen", LocalTime.parse("18:00:00"), LocalTime.parse("23:00:00"), LocalDate.parse("2024-02-20"), "Palestra Orange Invorio");
        try {
            CatERing.getInstance().getShiftManager().updateShift(dinnerShift);
        } catch (ShiftException e) {
            System.out.println(e.getMessage());
        }
        Shift serviceShift = CatERing.getInstance().getShiftManager().createShift("Service", LocalTime.parse("11:00:00"), LocalTime.parse("17:00:00"), LocalDate.parse("2024-02-20"), "Aula Studio");
    }
}
