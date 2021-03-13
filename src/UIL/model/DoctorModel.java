package UIL.model;

import BLL.DepartmentMgr;
import BLL.Doctor;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

public class DoctorModel {
    private final SimpleStringProperty name;
    private final int id;
    private final SimpleStringProperty ticketsRemaining;

    public DoctorModel(Doctor doctor, int remaining) {
        this.id = doctor.getId();
        this.name = new SimpleStringProperty(doctor.getName());
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        Calendar now = Calendar.getInstance();
        //int remaining = 0;
        //int today = now.get(Calendar.DAY_OF_WEEK);
        //for (Integer[] integers : departmentMgr.getById(doctor.getDepartmentId()).getShiftTable()) {
        //    if (integers[0] == doctor.getId()){
        //        remaining = integers[today];
        //        break;
        //    }
        //}
        this.ticketsRemaining = new SimpleStringProperty(remaining + "");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getTicketsRemaining() {
        return ticketsRemaining.get();
    }

    public void setTicketsRemaining(String ticketsRemaining) {
        this.ticketsRemaining.set(ticketsRemaining);
    }

    public SimpleStringProperty ticketsRemainingProperty() {
        return ticketsRemaining;
    }
}
