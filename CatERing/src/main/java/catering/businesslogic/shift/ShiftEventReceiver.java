package catering.businesslogic.shift;

public interface ShiftEventReceiver {
    void updateShiftCreated(Shift shift);
    void updateShiftEdited(Shift shift);
    void updateShiftRemoved(Shift shift);
}
