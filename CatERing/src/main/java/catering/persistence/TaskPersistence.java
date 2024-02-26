package catering.persistence;

import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.Task;
import catering.businesslogic.task.TaskEventReceiver;

public class TaskPersistence implements TaskEventReceiver {
    @Override
    public void updateSummarySheetCreated(SummarySheet newSumSheet) {
        newSumSheet.saveSheet();
    }

    @Override
    public void updateSummarySheetEdited(SummarySheet sumSheet) {
        sumSheet.updateTaskBoard();
    }

    public void updateSummarySheetSorted(SummarySheet sumSheet) {
        sumSheet.updateTaskBoardSorted();
    }

    @Override
    public void updateTaskInserted(SummarySheet sumSheet, Task task) {
        sumSheet.saveTaskInserted(task);
    }

    @Override
    public void updateTaskAssigned(SummarySheet sumSheet, Task task) {
        sumSheet.saveTaskAssigned(task);
    }

    @Override
    public void updateTaskEdited(SummarySheet sumSheet, Task task) {
        sumSheet.saveUpdatesInTask(task);
    }

    @Override
    public void updateTaskRemoved(SummarySheet sumSheet, Task task) {
        sumSheet.saveTaskRemoved(task);
    }
}
