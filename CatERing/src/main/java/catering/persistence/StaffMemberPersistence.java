package catering.persistence;

import catering.businesslogic.staffMember.StaffMember;
import catering.businesslogic.staffMember.StaffMemberEventReceiver;

public class StaffMemberPersistence implements StaffMemberEventReceiver {
    @Override
    public void updateStaffMemberCreated(StaffMember member) {
      member.saveMember();
    }
}
