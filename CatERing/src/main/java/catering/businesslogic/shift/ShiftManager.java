package catering.businesslogic.shift;

import catering.businesslogic.exception.ShiftException;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.task.Task;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ShiftManager {
    private final ArrayList<ShiftEventReceiver> receivers;
    private final ArrayList<Shift> kitchenShiftBoard;
    private final ArrayList<Shift> serviceShiftBoard;

    public ShiftManager() {
        this.kitchenShiftBoard = new ArrayList<>();
        this.serviceShiftBoard = new ArrayList<>();
        this.receivers = new ArrayList<>();
    }

    public void addReceiver(ShiftEventReceiver shter) {
        receivers.add(shter);
    }

    public void removeReceiver(ShiftEventReceiver shter) {
        receivers.remove(shter);
    }

    private void notifyShiftCreated(Shift shift) {
        for(ShiftEventReceiver sher: receivers) {
            sher.updateShiftCreated(shift);
        }
    }

    private void notifyShiftEdited(Shift shift) {
        for(ShiftEventReceiver sher: receivers) {
            sher.updateShiftEdited(shift);
        }
    }

    private void notifyShiftRemoved(Shift shift) {
        for(ShiftEventReceiver sher: receivers) {
            sher.updateShiftRemoved(shift);
        }
    }

    public Shift createShift(String type, LocalTime start, LocalTime end, LocalDate date, String workplace) {
        if(type.equals("Kitchen")) {
            KitchenShift shift = new KitchenShift(start, end, date, workplace);
            this.kitchenShiftBoard.add(shift);
            notifyShiftCreated(shift);
            return shift;
        } else {
            ServiceShift shift = new ServiceShift(start, end, date, workplace);
            this.serviceShiftBoard.add(shift);
            notifyShiftCreated(shift);
            return shift;
        }
    }

    public void removeShift(Shift shift) {}

    public ArrayList<Shift> getKitchenShift(LocalDate date){
        return null;
    }

    public void updateShift(Shift shift) throws ShiftException {
        shift.setFull();
        this.notifyShiftEdited(shift);
    }

    public void setCookForShift(Shift shift, StaffMember cook) {
        shift.setCook(cook);
        this.notifyShiftEdited(shift);
    }

    public StaffMember getCookForShift(Shift shift, int cookId) {
        return shift.getCook(cookId);
    }

    public void setTaskForShift(Shift shift, Task task) {
        shift.setTask(task);
        notifyShiftEdited(shift);
    }

    public void getTaskForShift(Shift shift, int taskId) {
        shift.getTask(taskId);
    }

    public Shift getShift(int id) {
        for(Shift s: kitchenShiftBoard) {
            if(s.getId() == id) {
                return s;
            }
        }
        for(Shift s: serviceShiftBoard) {
            if(s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<Shift> getKitchenShifts(LocalDate date) {
        ArrayList<Shift> result = new ArrayList<>();
        for(Shift s: kitchenShiftBoard) {
            if(s.getDate().equals(date)) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Shift> getKitchenShiftBoard() {
        return kitchenShiftBoard;
    }
}
