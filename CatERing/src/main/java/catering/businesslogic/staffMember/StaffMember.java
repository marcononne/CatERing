package catering.businesslogic.staffMember;

import catering.businesslogic.summon.Summon;
import catering.persistence.PersistenceManager;

public class StaffMember {
    private String name;
    private String job;
    private String role;
    private int id;

    public StaffMember(String name, String job, String role) {
        this.name = name;
        this.job = job;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addSummon(Summon summon) {}
    public int getId() {
        return id;
    }

    public void saveMember() {
        String query = "INSERT INTO catering.StaffMembers (id, name, job, role) VALUES "+
            "("+null+", '"+name+"', '"+job+"', '"+role+"')";
        PersistenceManager.myExecuteUpdate(query);
        id = PersistenceManager.getLastId();
    }

    @Override
    public String toString() {
        return "StaffMember id = "+id+" {" +
            " name = '" + name + '\'' +
            ", job = '" + job + '\'' +
            ", role = '" + role + '\'' +
            " }";
    }
}
