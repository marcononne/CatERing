package catering.businesslogic.shift;

import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;

public class KitchenShift extends Shift {
    public KitchenShift(LocalTime start, LocalTime end, LocalDate date, String workplace) { super(start, end, date, workplace); }

    public void setFull() { super.setFull(); }

    public boolean isFull() {
      return super.isFull();
    }

    public void setCook(StaffMember cook){ super.setCook(cook); }

    public StaffMember getCook(int cookId){ return super.getCook(cookId); }

    public void setTask(Task task){ super.setTask(task); }

    public LocalDate getDate() {
      return super.getDate();
    }

    public void saveUpdates() {
        super.saveUpdates();
    }

    public int getId() {
      return super.getId();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void saveShift() {
        super.saveShift();
    }
}
