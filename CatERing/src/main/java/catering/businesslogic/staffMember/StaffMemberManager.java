package catering.businesslogic.staffMember;

import catering.businesslogic.summon.Summon;
import java.util.ArrayList;

public class StaffMemberManager {
    private final ArrayList<StaffMember> members;
    private final ArrayList<StaffMemberEventReceiver> receivers;

    public StaffMemberManager() {
        this.members = new ArrayList<>();
        this.receivers = new ArrayList<>();
    }

    public void addReceivers(StaffMemberEventReceiver smer) {
        receivers.add(smer);
    }

    public void notifyStaffMemberCreated(StaffMember member) {
        for(StaffMemberEventReceiver smer: receivers) {
            smer.updateStaffMemberCreated(member);
        }
    }

    public StaffMember getMember(int id) {
        for(StaffMember sm : members) {
            if(sm.getId() == id) {
                return sm;
            }
        }
        return null;
    }
    public StaffMember createMember(String name, String job, String role) {
        StaffMember newMember = new StaffMember(name, job, role);
        this.members.add(newMember);
        notifyStaffMemberCreated(newMember);
        return newMember;
    }

    public void assignSummon(StaffMember person, Summon summon) {}

    public void removeSummon(StaffMember person, Summon summon) {}

}
