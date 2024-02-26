package catering.businesslogic.summon;

import catering.businesslogic.exception.SummonException;
import catering.businesslogic.staffMember.StaffMember;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class SummonManager {
    private final ArrayList<SummonEventReceiver> receivers;
    private final ArrayList<Summon> summonBoard;

    public SummonManager() {
        receivers = new ArrayList<>();
        summonBoard = new ArrayList<>();
    }

    public void addReceiver(SummonEventReceiver suer) {
        receivers.add(suer);
    }

    public void removeReceiver(SummonEventReceiver suer) {
        receivers.remove(suer);
    }

    private void notifySummonCreated(Summon newSumm) {
        for(SummonEventReceiver suer: receivers) {
            suer.updateSummonCreated(newSumm);
        }
    }

    private void notifySummonEdited(Summon summ) {
        for(SummonEventReceiver suer: receivers) {
            suer.updateSummonEdited(summ);
        }
    }

    public ArrayList<Summon> getSummonBoard() {
        return this.summonBoard;
    }

    public ArrayList<Summon> getSummons(LocalDate date, Optional<String> job) {
        ArrayList<Summon> summonList = new ArrayList<>();
        for (Summon s: summonBoard) {
            if ( s.getStartingDate().equals(date) || s.getEndingDate().equals(date) ||
                    (s.getStartingDate().isBefore(date) && s.getEndingDate().isAfter(date))) {
                if (job.isPresent()) {
                    if (s.getMember().getJob().equals(job.get())) {
                        summonList.add(s);
                    }
                } else {
                    summonList.add(s);
                }
            }
        }
        return summonList;
    }

    public Summon containsSummon(LocalDate shiftDate, StaffMember cook) {
        for (Summon s: summonBoard) {
            if ( (s.getStartingDate().equals(shiftDate) || s.getEndingDate().equals(shiftDate) ||
                    (s.getStartingDate().isBefore(shiftDate) && s.getEndingDate().isAfter(shiftDate))) &&
                     s.getMember().getId() == cook.getId() && s.getStatus().equals("active")) {
                return s;
            }
        }
        return null;
    }

    public Summon containsAssignedSummon(LocalDate shiftDate, StaffMember cook) {
        for (Summon s: summonBoard) {
            if ( (s.getStartingDate().equals(shiftDate) || s.getEndingDate().equals(shiftDate) ||
                    (s.getStartingDate().isBefore(shiftDate) && s.getEndingDate().isAfter(shiftDate))) &&
                    s.getMember().getId() == cook.getId() && s.getStatus().equals("assigned")) {
                return s;
            }
        }
        return null;
    }

    public void updateSummonStatus(Summon summ, String status) throws SummonException {
        summ.setStatus(status);
        notifySummonEdited(summ);
    }

    public Summon createSummon(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Summon summ = new Summon(startDate, endDate, startTime, endTime);
        this.summonBoard.add(summ);
        notifySummonCreated(summ);
        return summ;
    }

    public void setMemberInSummon(Summon summ, StaffMember member) {
        summ.setMember(member);
    }
}
