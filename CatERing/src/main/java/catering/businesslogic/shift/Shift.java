package catering.businesslogic.shift;

import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.task.Task;
import catering.persistence.PersistenceManager;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class Shift {
    private final LocalTime start;
    private final LocalTime end;
    private LocalDate date;
    private final String workplace;
    private boolean full;
    private int id;
    private final ArrayList<StaffMember> membersInShift;
    private final ArrayList<Task> tasksInShift;
    private final ArrayList<String> querysToDo;

    public Shift(LocalTime start, LocalTime end, LocalDate date, String workplace) {
        this.start = start;
        this.end = end;
        this.date = date;
        this.workplace = workplace;
        this.full = false;
        this.membersInShift = new ArrayList<>();
        this.tasksInShift = new ArrayList<>();
        this.querysToDo = new ArrayList<>();
    }

    public void setFull() {
        this.full = !this.full;
        querysToDo.add("UPDATE catering.Shifts SET full='"+(full ? 1:0 )+"' WHERE id = " + id);
    }

    public boolean isFull() {
        return this.full;
    }

    public void setCook(StaffMember cook) {
        membersInShift.add(cook);
        querysToDo.add("INSERT INTO catering.MembersShifts (memberId, shiftId) VALUES ("+cook.getId()+", "+id+")");
    }

    public StaffMember getCook(int cookId) {
        final StaffMember[] member = new StaffMember[1];
        String query1 = "SELECT memberId FROM catering.MembersShifts WHERE memberId = " + cookId + " AND shiftId = " + id;
        PersistenceManager.myExecuteQuery(query1, rs -> {
            String query2 = "SELECT * FROM catering.StaffMembers WHERE id = " + cookId + " AND id = " + rs.getInt(1);
            PersistenceManager.myExecuteQuery(query2, rs2 -> {
                member[0] = new StaffMember(rs2.getString(2), rs2.getString(3), rs2.getString(4));
            });
        });
        return member[0];
    }

    public void setTask(Task task) {
        tasksInShift.add(task);
        querysToDo.add("INSERT INTO catering.TasksInShifts (taskId, shiftId) VALUES ("+task.getId()+", "+id+")");
    }

    public void getTask(int taskId) {
        String query1 = "SELECT taskId FROM catering.TasksInShifts WHERE taskId = " + taskId + " AND shiftId = " + id;
        PersistenceManager.myExecuteQuery(query1, rs -> {
            String query2 = "SELECT * FROM catering.Tasks WHERE id = " + taskId + " AND id = " + rs.getInt(1);
            PersistenceManager.myExecuteQuery(query2, rs2 -> {
                System.out.println("Task id = " + rs2.getInt(1) + " of procedure = " + rs2.getInt(2) +
                    ":\n Priority = " + rs2.getInt(3) + ", EstTime = " + rs2.getInt(4) +
                    ", Quantity = " + rs2.getInt(5) + ", Compl: " + rs2.getBoolean(6) +
                    ", toDo: " + rs2.getBoolean(7));
            });
        });
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
        querysToDo.add("UPDATE catering.Shifts SET date ='"+Date.valueOf(date)+"' WHERE id = " + id);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void saveUpdates() {
        for(String query : querysToDo) {
            PersistenceManager.myExecuteUpdate(query);
        }
        querysToDo.clear();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Shift id = " + id + " {" +
            " start = " + start.toString() +
            ", end = " + end.toString() +
            ", date = " + date.toString() +
            ", workplace = '" + workplace + '\'' +
            ", full = " + full +
            " }";
    }

    public void saveShift() {
        String query = "INSERT INTO catering.Shifts (id, start, end, date, workplace, full) VALUES " +
            "("+null+", '"+Time.valueOf(start)+"', '"+Time.valueOf(end)+"', '"+Date.valueOf(date)+"', '"+workplace+"', '"
            + (full ? 1:0 ) +"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }
}
