package UIL.model;

import BLL.*;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class RecordModel {
    private final SimpleStringProperty ticketId;
    private final SimpleStringProperty date;
    private final SimpleStringProperty name;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty disease;
    private final SimpleStringProperty department;
    private final SimpleStringProperty doctor;
    private final SimpleStringProperty diagnose;
    private final SimpleStringProperty rx;

    public RecordModel(Record record) {
        Registry registry = Registry.getInstance();
        Ticket ticket = registry.getById(record.getTicketId());
        this.ticketId = new SimpleStringProperty(record.getTicketId());
        this.name = new SimpleStringProperty(ticket.getName());
        if (ticket.isSex()) {
            this.sex = new SimpleStringProperty("男");
        } else {
            this.sex = new SimpleStringProperty("女");
        }
        DiseaseType diseaseType = DiseaseType.getInstance();
        this.disease = new SimpleStringProperty(diseaseType.get(record.getDiseaseId()).getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = new SimpleStringProperty(simpleDateFormat.format(record.getDate()));
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        this.department = new SimpleStringProperty(departmentMgr.getById(record.getDepartmentId()).getName());
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        this.doctor = new SimpleStringProperty(doctorMgr.getById(record.getDoctorId()).getName());
        this.diagnose = new SimpleStringProperty(record.getDiagnose());
        this.rx = new SimpleStringProperty(record.getRxString());
    }

    public String getTicketId() {
        return ticketId.get();
    }

    public void setTicketId(String ticketId) {
        this.ticketId.set(ticketId);
    }

    public SimpleStringProperty ticketIdProperty() {
        return ticketId;
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

    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public String getDisease() {
        return disease.get();
    }

    public void setDisease(String disease) {
        this.disease.set(disease);
    }

    public SimpleStringProperty diseaseProperty() {
        return disease;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public String getDoctor() {
        return doctor.get();
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public SimpleStringProperty doctorProperty() {
        return doctor;
    }

    public String getDiagnose() {
        return diagnose.get();
    }

    public void setDiagnose(String diagnose) {
        this.diagnose.set(diagnose);
    }

    public SimpleStringProperty diagnoseProperty() {
        return diagnose;
    }

    public String getRx() {
        return rx.get();
    }

    public void setRx(String rx) {
        this.rx.set(rx);
    }

    public SimpleStringProperty rxProperty() {
        return rx;
    }
}
