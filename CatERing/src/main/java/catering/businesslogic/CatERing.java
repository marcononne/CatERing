package catering.businesslogic;

import catering.businesslogic.availability.AvailabilityManager;
import catering.businesslogic.event.*;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.procedure.ProcedureManager;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.staffMember.StaffMemberManager;
import catering.businesslogic.summon.SummonManager;
import catering.businesslogic.task.TaskManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.*;

public class CatERing {
    private static CatERing singleInstance;
    private final AvailabilityManager availabilityManager;
    private final EventManager eventManager;
    private final SingleEventManager singleEventManager;
    private final ComplexEventManager complexEventManager;
    private final PeriodicEventManager periodicEventManager;
    private final MenuManager menuManager;
    private final ProcedureManager procedureManager;
    private final ShiftManager shiftManager;
    private final StaffMemberManager staffMemberManager;
    private final SummonManager summonManager;
    private final TaskManager taskManager;
    private final UserManager userManager;

    private CatERing() {
        /* Initializing managers */
        availabilityManager = new AvailabilityManager();
        eventManager = new EventManager();
        singleEventManager = new SingleEventManager();
        complexEventManager = new ComplexEventManager();
        periodicEventManager = new PeriodicEventManager();
        menuManager = new MenuManager();
        procedureManager = new ProcedureManager();
        shiftManager = new ShiftManager();
        staffMemberManager = new StaffMemberManager();
        summonManager = new SummonManager();
        taskManager = new TaskManager();
        userManager = new UserManager();

        /* Initializing persistence modules */
        MenuPersistence menuPersistence = new MenuPersistence();
        menuManager.addReceiver(menuPersistence);
        SummonPersistence summonPersistence = new SummonPersistence();
        summonManager.addReceiver(summonPersistence);

        ShiftPersistence shiftPersistence = new ShiftPersistence();
        shiftManager.addReceiver(shiftPersistence);

        TaskPersistence taskPersistence = new TaskPersistence();
        taskManager.addReceiver(taskPersistence);

        EventPersistence eventPersistence = new EventPersistence();
        eventManager.addReceiver(eventPersistence);
        singleEventManager.addReceiver(eventPersistence);
        complexEventManager.addReceiver(eventPersistence);
        periodicEventManager.addReceiver(eventPersistence);

        ProcedurePersistence procedurePersistence = new ProcedurePersistence();
        procedureManager.addReceiver(procedurePersistence);

        StaffMemberPersistence staffMemberPersistence = new StaffMemberPersistence();
        staffMemberManager.addReceivers(staffMemberPersistence);
    }

    public static CatERing getInstance() {
        if(singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    public AvailabilityManager getAvailabilityManager() {
        return availabilityManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public SingleEventManager getSingleEventManager() {
        return singleEventManager;
    }

    public ComplexEventManager getComplexEventManager() {
        return complexEventManager;
    }

    public PeriodicEventManager getPeriodicEventManager() {
        return periodicEventManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public ProcedureManager getProcedureManager() {
        return procedureManager;
    }

    public ShiftManager getShiftManager() {
        return shiftManager;
    }

    public StaffMemberManager getStaffMemberManager() {
        return staffMemberManager;
    }

    public SummonManager getSummonManager() {
        return summonManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
