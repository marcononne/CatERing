package catering.businesslogic.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.staffMember.StaffMember;
import catering.persistence.PersistenceManager;

import java.util.Optional;

public class Task {
    private int priority;
    private int estimatedTime;
    private int quantity;
    private boolean completed;
    private boolean toDo;
    private Procedure procedure;
    private int id;
    private StaffMember cook;

    public Task(Procedure proc) {
        this.procedure = proc;
        this.priority = 0;
        this.estimatedTime = 0;
        this.quantity = 0;
        this.completed = false;
        this.toDo = true;
    }

    public int getPriority() {
        return priority;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setToDo(boolean toDo) {
        this.toDo = toDo;
    }

    public boolean isToDo() {
        return toDo;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void updateQuantity(int quantity) {
        this.quantity -= quantity;
        if(this.quantity == 0) {
            this.completed = true;
            this.toDo = false;
        }
    }

    public void saveTask() {
        String query = "INSERT INTO catering.Tasks VALUES ("+null+", '"+procedure.getId()+"', '"+priority+"', " +
            "'"+estimatedTime+"', '"+quantity+"', '"+(completed ? 1:0)+"', '"+(toDo ? 1:0)+"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public void saveUpdates() {
        String query = "UPDATE catering.Tasks SET priority = "+priority+", estimatedTime = "+estimatedTime+", " +
            "quantity = " +quantity+ ", toDo = '" + (toDo ? 1:0) +
            "', completed = '" + (completed ? 1: 0)+ "' WHERE id = " + id;
        PersistenceManager.myExecuteUpdate(query);
    }

    public Shift getShift() {
        int[] shiftId = new int[1];
        PersistenceManager.myExecuteQuery("SELECT shiftId FROM catering.TasksInShifts WHERE" +
            " taskId = " + id, rs -> shiftId[0] = rs.getInt(1));
        return CatERing.getInstance().getShiftManager().getShift(shiftId[0]);
    }

    public void setCook(StaffMember cook) {
        this.cook = cook;
    }

    public void saveAssignment() {
        if(cook != null) {
            String query = "INSERT INTO catering.MembersTasks (memberId, taskId) VALUES ("+cook.getId()+", "+id+")";
            PersistenceManager.myExecuteUpdate(query);
        }
        saveUpdates();
    }

    public StaffMember getCook() {
        String query = "SELECT memberId FROM catering.MembersTasks WHERE taskId = '" + id + "'";
        final int[] cookId = new int[1];
        PersistenceManager.myExecuteQuery(query, rs -> {
            cookId[0] = rs.getInt(1);
        });
        return CatERing.getInstance().getStaffMemberManager().getMember(cookId[0]);
    }

    public void detach(Shift shift, Optional<StaffMember> cook) {
        if (cook.isPresent() && getCook() == cook.get() && getCook() == this.cook && this.cook == cook.get()) {
            PersistenceManager.myExecuteUpdate("DELETE FROM catering.MembersTasks WHERE taskId = "
                + id + " AND memberId = " + getCook().getId());
            this.cook = null;
        }
        if(shift.getId() == getShift().getId()) {
            PersistenceManager.myExecuteUpdate("DELETE FROM catering.TasksInShifts WHERE taskId = "
                + id + " AND shiftId = " + getShift().getId());
        }
        this.procedure = null;
        this.toDo = false;
        this.completed = false;
    }
    public void setTimeAndQuantity(int time, int quantity) {
        this.estimatedTime = time;
        this.quantity = quantity;
        if (time == 0 && quantity == 0) {
            this.toDo = false;
            this.completed = true;
        }
    }

    public void setId(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task id = " + id + " {" +
            " priority = " + priority +
            ", estimatedTime = " + estimatedTime +
            ", quantity = " + quantity +
            ", completed = " + completed +
            ", toDo = " + toDo +
            ", procedureId = " + procedure.getId() +
            ", procedureName = " + procedure.getName() +
            " }";
    }
}
