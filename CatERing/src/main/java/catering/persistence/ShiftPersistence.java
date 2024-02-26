package catering.persistence;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.shift.ShiftEventReceiver;

public class ShiftPersistence implements ShiftEventReceiver {
    @Override
    public void updateShiftCreated(Shift shift) {
        shift.saveShift();
    }

    @Override
    public void updateShiftEdited(Shift shift) {
        shift.saveUpdates();
    }

    @Override
    public void updateShiftRemoved(Shift shift) {}
}
