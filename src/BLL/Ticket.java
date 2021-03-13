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

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Ticket implements Comparable<Ticket>, Serializable {
    private String id; // 病历号
    private String name; // 姓名
    private boolean sex; // 性别 true = 男, false = 女
    private int age; // 年龄
    private int payMethod; // 支付方式
    private String idNumber; // 身份证号
    private String address; // 地址
    private int priority; // 挂号级别 1 = 复诊 2 = 初诊 3 = 加急
    private int departmentId; // 所属科室
    private int doctorId; // 所属医生
    private boolean status; // 挂号有效性
    private Rx rx; // 处方
    private ArrayList<Order> orders; // 订单
    private int realPriority; // 真实挂号级别

    public Ticket(String id, String name, boolean sex, int age, int payMethod, String idNumber, String address, int departmentId, int priority) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.payMethod = payMethod;
        this.idNumber = idNumber;
        this.address = address;
        this.departmentId = departmentId;
        //this.doctorId = doctorId;
        this.doctorId = -1;
        this.priority = priority;
        this.status = false;
        this.orders = new ArrayList<>();
        this.realPriority = priority;
    }

    public int getRealPriority() {
        return realPriority;
    }

    public void setRealPriority(int realPriority) {
        this.realPriority = realPriority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Rx getRx() {
        return rx;
    }

    public void setRx(Rx rx) {
        this.rx = rx;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Date dob) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getIdNumber() {
        return idNumber;
    }

    /*public int getRegLevel() {
        return regLevel;
    }*/

    /*public void setRegLevel() {
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        if (departmentMgr.getById(this.departmentId).isEmergency())
            this.regLevel = 2;
        else if (doctorMgr.getById(this.doctorId).isSpecialist())
            this.regLevel = 1;
        else this.regLevel = 0;
    }*/

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDepartment() {
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        return departmentMgr.getById(this.departmentId);
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Doctor getDoctor() {
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        return doctorMgr.getById(this.doctorId);
    }


    public boolean cancel() {
        return true;
    }

    public Item toItem() {
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        ItemMgr itemMgr = ItemMgr.getInstance();
        if (priority == 3)
            return itemMgr.get("jzh");
            //else if (doctorMgr.getById(doctorId).isSpecialist())
            //    return itemMgr.get("zjh");
        else return itemMgr.get("pth");
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public Order getOrders(int index) {
        if (orders.size() == 0)
            return null;
        return orders.get(index);
    }

    public void addOrders(Order order) {
        orders.add(order);
        System.out.println("病历号" + id + "已添加订单");
    }

    /**
     * 比较器（降序）
     *
     * @param o 待比较的对象
     * @return 权重差
     */
    @Override
    public int compareTo(@NotNull Ticket o) {
        return o.realPriority - this.realPriority;
    }

    @Override
    public String toString() {
        String priorityStr = "";
        String doctorName;
        DoctorMgr doctorMgr = DoctorMgr.getInstance();
        switch (priority) {
            case 1: {
                priorityStr = "复诊";
                break;
            }
            case 2: {
                priorityStr = "初诊";
                break;
            }
            case 3: {
                priorityStr = "加急";
                break;
            }
        }
        if (doctorId == -1) {
            doctorName = "未指定";
        } else {
            doctorName = doctorMgr.getById(doctorId).getName();
        }
        return id + "\t" + name + "\t" + priorityStr + "\t" + doctorName;
    }
}


