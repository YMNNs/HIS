package BLL;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class Record implements Serializable, Comparable<Record> {
    String ticketId; // 病历号
    Date date; // 就诊日期
    int doctorId; // 医生ID
    int departmentId; // 科室ID
    String diagnose; // 诊断
    Vector<String> medicines; // 处方
    int diseaseId; // 病种ID


    public Record(String ticketId, Date date, int doctorId, int departmentId, String diagnose, Vector<String> medicines, int diseaseId) {
        this.ticketId = ticketId;
        this.date = date;
        this.doctorId = doctorId;
        this.departmentId = departmentId;
        this.diagnose = diagnose;
        this.medicines = medicines;
        this.diseaseId = diseaseId;

    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public Vector<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(Vector<String> medicines) {
        this.medicines = medicines;
    }

    public String getRxString() {
        StringBuilder result = new StringBuilder();
        for (String medicine : medicines) {
            result.append(medicine).append("\t");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        Registry registry = Registry.getInstance();
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        String doctorName = doctorMgr.getById(doctorId).getName();
        String patientName = registry.getById(ticketId).getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ticketId).append("\t").append(patientName).append("\t").append(date).append("\n");
        stringBuilder.append("医生：").append(doctorName).append("\n");
        stringBuilder.append("诊断：").append(diagnose).append("\n");
        stringBuilder.append("处方：");
        for (String medicine : medicines) {
            stringBuilder.append(medicine).append("\t");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(@NotNull Record o) {
        return (int) (o.date.getTime() - this.date.getTime());
    }
}
