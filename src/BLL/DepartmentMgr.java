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

public class DepartmentMgr implements Serializable {
    private static DepartmentMgr departmentMgr = new DepartmentMgr();
    private HashSet<Department> departments;

    private DepartmentMgr() {
        departments = new HashSet<>();
        //load();
    }

    public static DepartmentMgr getInstance() {
        return departmentMgr;
    }

    public HashSet<Department> getDepartments() {
        return departments;
    }

    public Department get(String query) {
        for (Department department : departments) {
            if (department.getAlias().equals(query) || department.getName().equals(query))
                return department;
        }
        return null;
    }

    public Department getById(int id) {
        for (Department department : departments) {
            if (department.getId() == id)
                return department;
        }
        return null;
    }

    public void init() {
        departments = new HashSet<>();
        Department dept1 = new Department(1, "mnnk", "泌尿内科", false, null);
        dept1.setShift(1);
        dept1.setShift(3);
        departments.add(dept1);
        Department dept2 = new Department(2, "jzk", "急诊科", true, null);
        dept2.setShift(2);
        dept2.setShift(4);
        departments.add(dept2);
        save();

    }

    public void save() {
        DataControl.writeDepartment(departmentMgr);
    }

    public void load() {
        try {
            DepartmentMgr departmentMgr1 = DataControl.readDepartment();
            if (departmentMgr1 == null)
                init();
            else departmentMgr = departmentMgr1;
        } catch (Exception e) {
            init();
            System.err.println("科室数据已损坏并重建");
        }
    }


    public void printAll() {
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        for (Department department : departments) {
            System.out.println(department.getId() + "\t" + department.getAlias() + "\t" + department.getName() + "\t");
            if (department.getAvailableDoctors() != null) {
                for (int doctorId : department.getAvailableDoctors().keySet()) {
                    Doctor doctor = doctorMgr.getById(doctorId);
                    if (doctor.isSpecialist()) {
                        System.out.print("\t专家\t");
                    } else System.out.print("\t普通\t");
                    System.out.println(doctor.getId() + "\t" + doctor.getName() + "\t" + department.getAvailableDoctors().get(doctorId));
                }
            } else System.out.println("暂无可用医生");
        }
    }
}
