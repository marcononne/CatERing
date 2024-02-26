package catering.businesslogic.task;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.exception.SummonException;
import catering.businesslogic.exception.TaskException;
import catering.businesslogic.exception.UseCaseLogicException;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.summon.Summon;

import java.util.ArrayList;
import java.util.Optional;

public class TaskManager {
    private final ArrayList<TaskEventReceiver> receivers;
    private SummarySheet currentSheet;

    public TaskManager() {
        this.receivers = new ArrayList<>();
    }

    public void addReceiver(TaskEventReceiver tsker) {
        receivers.add(tsker);
    }

    public void removeReceiver(TaskEventReceiver tsker) {
        receivers.remove(tsker);
    }

    private void notifySummarySheetCreated(SummarySheet currSheet) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateSummarySheetCreated(currSheet);
        }
    }

    private void notifySummarySheetEdited(SummarySheet currSheet) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateSummarySheetEdited(currSheet);
        }
    }
    private void notifySummarySheetSorted(SummarySheet currentSheet) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateSummarySheetSorted(currentSheet);
        }
    }

    private void notifyTaskInserted(SummarySheet currentSheet, Task task) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateTaskInserted(currentSheet, task);
        }
    }

    private void notifyTaskEdited(SummarySheet currentSheet, Task task) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateTaskEdited(currentSheet, task);
        }
    }

    private void notifyTaskRemoved(SummarySheet currentSheet, Task task) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateTaskRemoved(currentSheet, task);
        }
    }

    private void notifyTaskAssigned(SummarySheet currentSheet, Task task) {
        for(TaskEventReceiver ter: receivers) {
            ter.updateTaskAssigned(currentSheet, task);
        }
    }

    public void setCurrentSheet(SummarySheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public SummarySheet getCurrentSheet() throws UseCaseLogicException {
        if(currentSheet != null) {
            return currentSheet;
        } else {
            throw new UseCaseLogicException("Get current sheet - Null");
        }
    }

    public SummarySheet createSummarySheet(Event event, Service service) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) {
            throw new UseCaseLogicException("Create summary sheet - Unauthorized user");
        }
        if (event.getChef() != CatERing.getInstance().getUserManager().getCurrentUser()) {
            throw new UseCaseLogicException("Create summary sheet - User is not a chef");
        }
        if (!event.containsService(service)) {
            throw new UseCaseLogicException("Create summary sheet - Event does not contain service");
        }
        if (service.getMenu() == null) {
            throw new UseCaseLogicException("Create summary sheet - Non-existent menu");
        }
        currentSheet = new SummarySheet();
        currentSheet.setService(service);
        service.setSummSheet(currentSheet);
        notifySummarySheetCreated(currentSheet);
        return currentSheet;
    }

    public SummarySheet openSummarySheet(Event event, Service service) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) {
            throw new UseCaseLogicException("Open summary sheet - Unauthorized user");
        }
        if (event.getChef() != CatERing.getInstance().getUserManager().getCurrentUser()) {
            throw new UseCaseLogicException("Open summary sheet - User is not a chef");
        }
        if (!event.containsService(service)) {
            throw new UseCaseLogicException("Open summary sheet - Event does not contain service");
        }
        if (currentSheet == null) {
            throw new UseCaseLogicException("Open summary sheet - Current sheet is null");
        }
        return currentSheet;

    }

    public Task addTask(Procedure procedure) throws UseCaseLogicException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("AddTask - Current sheet is null");
        } else {
            Task newTask = new Task(procedure);
            currentSheet.insertTask(newTask);
            notifyTaskInserted(currentSheet, newTask);
            return newTask;
        }
    }

    public void editSelectedTask(Task oldTask, Optional<Task> newTask, Optional<Shift> newShift, Optional<StaffMember> newCook) throws UseCaseLogicException, SummonException, TaskException {
        if (currentSheet != null) {
            if (newTask.isPresent()) {
                Shift oldShift = currentSheet.getShiftFromTask(oldTask);
                StaffMember oldCook = currentSheet.getCookFromTask(oldTask);
                currentSheet.setCookForTask(newTask.get(), oldCook);
                newTask.get().saveAssignment();
                CatERing.getInstance().getShiftManager().setTaskForShift(oldShift, newTask.get());
                removeTask(oldTask);
                notifyTaskEdited(currentSheet, newTask.get());
                notifyTaskRemoved(currentSheet, oldTask);
            }
            if (newShift.isPresent()) {
                StaffMember oldCook = currentSheet.getCookFromTask(oldTask);
                Summon newSumm = CatERing.getInstance().getSummonManager().containsSummon(newShift.get().getDate(), oldCook);
                if (newSumm == null) {
                    throw new SummonException("Edit Selected Task - No (new) summon available for new shift");
                }
                Summon oldSumm = CatERing.getInstance().getSummonManager().containsAssignedSummon(oldTask.getShift().getDate(), oldCook);
                if (oldSumm == null) {
                    throw new SummonException("Edit Selected Task - No (old) summon found");
                }
                CatERing.getInstance().getSummonManager().updateSummonStatus(oldSumm, "revoked");
                CatERing.getInstance().getSummonManager().updateSummonStatus(newSumm, "assigned");
                CatERing.getInstance().getShiftManager().setCookForShift(newShift.get(), oldCook);
                CatERing.getInstance().getShiftManager().setTaskForShift(newShift.get(), oldTask);
            }
            if (newCook.isPresent()) {
                Shift oldShift = currentSheet.getShiftFromTask(oldTask);
                StaffMember oldCook = currentSheet.getCookFromTask(oldTask);
                Summon oldSumm = CatERing.getInstance().getSummonManager().containsAssignedSummon(oldShift.getDate(), oldCook);
                if (oldSumm == null) {
                    throw new SummonException("Edit Selected Task - No (old) summon found");
                }
                CatERing.getInstance().getSummonManager().updateSummonStatus(oldSumm, "revoked");
                Summon newSumm = CatERing.getInstance().getSummonManager().containsSummon(oldShift.getDate(), newCook.get());
                if (newSumm == null) {
                    throw new SummonException("Edit Selected Task - No (new) summon found");
                }
                CatERing.getInstance().getSummonManager().updateSummonStatus(newSumm, "assigned");
                CatERing.getInstance().getShiftManager().setCookForShift(oldShift, newCook.get());
                currentSheet.setCookForTask(oldTask, newCook.get());
                oldTask.saveAssignment();
                notifyTaskEdited(currentSheet, oldTask);
            }
        } else {
            throw new UseCaseLogicException("Edit Task - summary sheet is missing");
        }
    }

    public void removeTask(Task task) throws UseCaseLogicException, TaskException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Remove task - Current sheet is null");
        } else if(!currentSheet.getTasks().contains(task)) {
            throw new TaskException("Remove task -  this task is not included in the summary sheet");
        } else if(task.isCompleted()) {
            throw new TaskException("Remove task - this task is completed");
        } else {
            currentSheet.removeTask(task);
            notifyTaskRemoved(currentSheet, task);
        }
    }

    public void revokeTask(Task task, Shift shift, Optional<StaffMember> cook) throws UseCaseLogicException, TaskException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Revoke Task - Current sheet is null");
        } else if (!currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException("Revoke Task - This task is not included in the summary sheet");
        } else if(task.isCompleted()) {
            throw new TaskException("Revoke Task - This task is completed ");
        } else if(!task.isToDo()) {
            throw new TaskException("Revoke Task - This task is completed ");
        } else {
            currentSheet.revokeTask(task, shift, cook);
            notifyTaskEdited(currentSheet, task);
        }
    }

    public void deleteTask(Task task, Shift shift, Optional<StaffMember> cook) throws UseCaseLogicException, TaskException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Delete Task - Current sheet is null");
        } else if (!currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException("Delete Task - This task is not included in the summary sheet");
        } else if(task.isCompleted()) {
            throw new TaskException("Delete Task - This task is completed ");
        } else {
            currentSheet.deleteTask(task, shift, cook);
            notifyTaskRemoved(currentSheet, task);
            currentSheet.saveTaskDeleted(task);
        }
    }

    public void assignTask(Task task, Shift shift, Optional<StaffMember> cook) throws UseCaseLogicException, SummonException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Assign Task - Current sheet is null");
        } else if (!currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException("Assign Task - This task is not included in the summary sheet");
        } else {
            if(cook.isPresent()) {
                Summon summ = CatERing.getInstance().getSummonManager().containsSummon(shift.getDate(), cook.get());
                if(summ == null) {
                    throw new SummonException("Assign Task - There's not a summon that fits the assignment for cook "+cook.get().getId());
                } else {
                    CatERing.getInstance().getShiftManager().setCookForShift(shift, cook.get());
                    currentSheet.setCookForTask(task, cook.get());
                    CatERing.getInstance().getSummonManager().updateSummonStatus(summ, "assigned");
                }
            }
            currentSheet.setToDo(task, true);
            currentSheet.setCompleted(task, false);
            CatERing.getInstance().getShiftManager().setTaskForShift(shift, task);
            notifyTaskAssigned(currentSheet, task);
        }
    }

    public void assignPriority(Task task, int priority) throws UseCaseLogicException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Assign priority - Current sheet is null");
        } else if(!currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException("Assign priority - This task is not included in the summary sheet");
        } else {
            currentSheet.assignPriority(task, priority);
            notifyTaskEdited(currentSheet, task);
            notifySummarySheetSorted(currentSheet);
        }
    }

    public void markProcedureReady(Task task, int quantity) throws UseCaseLogicException {
        if(currentSheet == null) {
            throw new UseCaseLogicException("Mark procedure ready - Current sheet is null");
        } else if(!currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException("Mark procedure ready - This task is not included in the summary sheet");
        } else {
            currentSheet.markProcedureReady(task, quantity);
            notifyTaskEdited(currentSheet, task);
        }
    }

    public void setEstTimeAndQuantity(Task task, int time, int quantity) throws UseCaseLogicException{
        if (currentSheet == null) {
            throw new UseCaseLogicException("Set estimated time and quantity - No summary sheet found");
        } else if (!currentSheet.getTasks().contains(task)){
            throw new UseCaseLogicException("Set estimated time and quantity - Given task not in summary sheet");
        } else if (task.isCompleted() || !task.isToDo()) {
            throw new UseCaseLogicException("Set estimated time and quantity - Task completed or not to be done");
        } else {
            currentSheet.setEstTimeAndQuantity(task, time, quantity);
            notifyTaskEdited(currentSheet, task);
        }

    }
}
