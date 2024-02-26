package catering.persistence;

import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.procedure.ProcedureEventReceiver;

public class ProcedurePersistence implements ProcedureEventReceiver {
    @Override
    public void updateProcedureCreated(Procedure proc) {
        proc.saveProcedure();
    }
}
