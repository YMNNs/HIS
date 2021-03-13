/*
 * MIT License
 *
 * Copyright (c) 2019. 杨梦博
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package BLL;

import DAL.DataControl;

import java.io.Serializable;
import java.util.HashSet;

public class DoctorMgr implements Serializable {
    private static DoctorMgr instance = new DoctorMgr(); // 单例模式
    private HashSet<Doctor> doctors; // 医生集合

    private DoctorMgr() {
        doctors = new HashSet<>();
    }

    public static DoctorMgr getInstance() {
        return instance;
    }

    public void hire(Doctor doctor) {

        int id = 1;
        for (Doctor doctor1 : doctors) {
            id = doctor1.getId();
        }
        doctor.setId(id);
        doctors.add(doctor);
        save();
    }

    public Doctor getByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name))
                return doctor;
        }
        return null;
    }

    public HashSet<Doctor> getDoctors() {
        return doctors;
    }

    public Doctor getById(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id)
                return doctor;
        }
        return null;
    }


    public void assignDepartment(int doctorId, Department department) {
        department.setShift(doctorId);
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        departmentMgr.save();
        //doctor.setDepartment(department);
        save();
    }

    public void fire(Doctor doctor) {
        doctor.offDuty();
        doctors.remove(doctor);
        save();
    }

    public void printAll() {
        for (Doctor doctor : doctors) {
            System.out.print(doctor.getId() + "\t" + doctor.getName());
            if (doctor.isSpecialist())
                System.out.print("\t专家\n");
            else System.out.print("\t普通\n");
        }
    }

    public void init() {
        doctors = new HashSet<>();
        doctors.add(new Doctor(1, "医生甲", true, 1));
        doctors.add(new Doctor(2, "医生乙", false, 2));
        doctors.add(new Doctor(3, "医生丙", false, 1));
        doctors.add(new Doctor(4, "医生丁", false, 2));
        save();
    }

    public void load() {
        try {
            DoctorMgr instance1 = DataControl.readDoctor();
            if (instance1 != null)
                instance = instance1;
            else init();
        } catch (Exception e) {
            System.err.println("医生数据已损坏并重建");
            init();
        }
    }

    public void save() {
        DataControl.writeDoctor(instance);
    }


}
