package catering.businesslogic.task;

import catering.businesslogic.event.Service;
import catering.businesslogic.exception.TaskException;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.staffMember.StaffMember;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class SummarySheet {
    private final ArrayList<Task> tasks;
    private int id;
    private Service service;

    public SummarySheet() {
        this.tasks = new ArrayList<>();
    }

    public void insertTask(Task newTask) {
        tasks.add(newTask);
    }

    public void saveTaskInserted(Task newTask) {
        newTask.saveTask();
        String query = "INSERT INTO catering.SummarySheetTasks VALUES ('"+id+"', '"+newTask.getId()+"')";
        PersistenceManager.myExecuteUpdate(query);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void saveTaskRemoved(Task task) {
        String query = "DELETE FROM catering.SummarySheetTasks WHERE sumSheetId = '"+id+"' AND taskId = " + task.getId();
        PersistenceManager.myExecuteUpdate(query);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void assignPriority(Task task, int priority) {
        task.setPriority(priority);
    }

    public void setCookForTask(Task task, StaffMember cook) {
        task.setCook(cook);
    }

    public void setToDo(Task task, boolean val) {
        task.setToDo(val);
    }

    public void setCompleted(Task task, boolean val) {
        task.setCompleted(val);
    }

    public void markProcedureReady(Task task, int quantity) {
        task.updateQuantity(quantity);
    }

    public Shift getShiftFromTask(Task task) throws TaskException {
        if (tasks.contains(task)) {
            return task.getShift();
        } else {
            throw new TaskException("Get Shift from Task - Given task not in ArrayList");
        }
    }

    public StaffMember getCookFromTask(Task task) throws TaskException {
        if (tasks.contains(task)) {
            return task.getCook();
        } else {
            throw new TaskException("Get Cook from Task - Given task not in ArrayList");
        }
    }

    public void revokeTask(Task task, Shift shift, Optional<StaffMember> cook) {
        task.detach(shift, cook);
        tasks.remove(task);
        updateTaskBoard();
    }
    public void updateTaskBoard() {
        PersistenceManager.myExecuteQuery("SELECT taskId FROM catering.SummarySheetTasks WHERE sumSheetId = "+id,
            rs -> {
                if(!containsId(rs.getInt(1))) {
                    PersistenceManager.myExecuteUpdate("DELETE FROM catering.SummarySheetTasks WHERE " +
                        "taskId = " + rs.getInt(1));
                }
            }
        );
    }

    public void updateTaskBoardSorted() {
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                int p1 = o1.getPriority(), p2 = o2.getPriority();
                if(p1 < p2) {
                    return 1;
                } else if(p1 == p2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    private boolean containsId(int id) {
        for(Task t: tasks) {
            if(t.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void deleteTask(Task task, Shift shift, Optional<StaffMember> cook) {
        task.detach(shift, cook);
        tasks.remove(task);
    }

    public void saveTaskDeleted(Task task) {
        PersistenceManager.myExecuteUpdate("DELETE FROM catering.Tasks WHERE id = " + task.getId());
    }

    public void setEstTimeAndQuantity(Task task, int time, int quantity) {
        task.setTimeAndQuantity(time, quantity);
    }

    public void saveUpdatesInTask(Task task) {
        task.saveUpdates();
    }

    public void setService(Service serv) {
        this.service = serv;
    }

    public void saveSheet() {
        String query = "INSERT INTO catering.SummarySheets VALUES ("+null+", '"+service.getId()+"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    public void saveTaskAssigned(Task task) {
        task.saveAssignment();
    }

    @Override
    public String toString() {
        return "SummarySheet id = "+id+" {" +
            " service ID= '" + service.getId() + '\'' +
            " type= '" + service.getType() + '\'' +
            " service status= '" + service.getStatus() + '\'' +
            " menu_title= '" + service.getMenu().getTitle() + '\'' +
            '}';
    }
}
