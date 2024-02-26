package catering.businesslogic.summon;

import catering.businesslogic.exception.SummonException;
import catering.businesslogic.staffMember.StaffMember;
import catering.persistence.PersistenceManager;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Summon {
    private final LocalDate startingDate;
    private final LocalDate endingDate;
    private final LocalTime startingTime;
    private final LocalTime endingTime;
    private String status;
    private int id;
    private StaffMember member;

    public Summon(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startingDate = startDate;
        this.endingDate = endDate;
        this.startingTime = startTime;
        this.endingTime = endTime;
    }

    public void setMember(StaffMember member) {
        this.member = member;
        this.status = "active";
        saveUpdates();
        String query = "INSERT INTO catering.MembersSummons (memberId, summonId) VALUES ('"+member.getId()+"', '"+id+"')";
        PersistenceManager.myExecuteUpdate(query);
    }

    public StaffMember getMember() {
        return this.member;
    }

    public LocalDate getStartingDate() {
        return this.startingDate;
    }

    public LocalDate getEndingDate() {
        return this.endingDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String st) throws SummonException {
        if (!st.equals("active") && !st.equals("assigned") && !st.equals("revoked")) {
            throw new SummonException("Set Status - invalid status");
        }
        else {
            this.status = st;
            saveUpdates();
        }
    }

    public void saveSummon() {
        String summonInsert = "INSERT INTO catering.Summons (id, startingDate, endingDate, startingTime, endingTime, status) VALUES " +
            "("+null+", '"+Date.valueOf(startingDate)+"', '"+Date.valueOf(endingDate)+"', '"+Time.valueOf(startingTime)+"', '"
            +Time.valueOf(endingTime)+"', '"+status+"');";
        PersistenceManager.myExecuteUpdate(summonInsert);
        id = PersistenceManager.getLastId();
    }

    public void saveUpdates() {
        String summonUpdate = "UPDATE catering.Summons SET startingDate = '"+Date.valueOf(startingDate)+"', endingDate = '"+
            Date.valueOf(endingDate)+"', startingTime = '"+Time.valueOf(startingTime)+"', endingTime = '"+
            Time.valueOf(endingTime)+"', status = '"+status+"' WHERE id = "+id;
        PersistenceManager.myExecuteUpdate(summonUpdate);
    }

    @Override
    public String toString() {
        return "Summon id = "+id+" {" +
            "startDate='" + startingDate + '\'' +
            ", endDate='" + endingDate + '\'' +
            ", startTime='" + startingTime + '\'' +
            ", endTime='" + endingTime + '\'' +
            ", status='" + status + '\'' +
            '}';
    }

}
