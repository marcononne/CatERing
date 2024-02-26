package catering.persistence;

import catering.businesslogic.summon.Summon;
import catering.businesslogic.summon.SummonEventReceiver;

public class SummonPersistence implements SummonEventReceiver {
    @Override
    public void updateSummonCreated(Summon newSumm) {
        newSumm.saveSummon();
    }

    @Override
    public void updateSummonEdited(Summon summ) {
        summ.saveUpdates();
    }
}
