package catering.businesslogic.summon;

public interface SummonEventReceiver {
    void updateSummonCreated(Summon newSumm);
    void updateSummonEdited(Summon summ);
}
