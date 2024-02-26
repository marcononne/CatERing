package catering.businesslogic.task;

public interface TaskEventReceiver {

    void updateSummarySheetCreated(SummarySheet newSumSheet);
    void updateSummarySheetEdited(SummarySheet sumSheet);
    void updateSummarySheetSorted(SummarySheet sumSheet);
    void updateTaskInserted(SummarySheet sumSheet, Task task);
    void updateTaskAssigned(SummarySheet sumSheet, Task task);
    void updateTaskEdited(SummarySheet sumSheet, Task task);
    void updateTaskRemoved(SummarySheet sumSheet, Task task);
}
