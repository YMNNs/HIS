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

import java.io.Serializable;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Vector;

public class Doctor implements Serializable {
    private int id; // 医生ID
    private String name; // 医生姓名
    private boolean isSpecialist; // 是否为专家
    private int departmentId; // 所属科室
    private PriorityQueue<Ticket> queue; // 待诊队列
    private String currentTicketID; // 正在诊断的病历号

    public Doctor(int id, String name, boolean isSpecialist, int departmentId) {
        this.id = id;
        this.name = name;
        this.isSpecialist = isSpecialist;
        //this.department = null;
        this.departmentId = departmentId;
        queue = new PriorityQueue<>();
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public boolean getIsSpecialist() {
        return isSpecialist;
    }

    public String getCurrentTicketID() {
        return currentTicketID;
    }

    public void setCurrentTicketID(String currentTicketID) {
        this.currentTicketID = currentTicketID;
    }

    public boolean isSpecialist() {
        return isSpecialist;
    }

    // FOR JSON MUST HAVE GETTERS AND SETTERS
    public void setSpecialist(boolean isSpecialist) {
        this.isSpecialist = isSpecialist;
    }

    public void add(Ticket ticket) {
        DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
        Department department = departmentMgr.getById(departmentId);
        queue.add(ticket);

    }

    public void remove(Ticket ticket) {
        queue.remove(ticket);
        System.out.println(queue.size());
    }

    public PriorityQueue<Ticket> getQueue() {
        return queue;
    }

    public void setQueue(PriorityQueue<Ticket> queue) {
        this.queue = queue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void confirmTicket() {
        Registry registry = Registry.getInstance();
        Ticket ticket = registry.getById(currentTicketID);
        this.currentTicketID = ticket.getId();
        for (Order ticketOrder : ticket.getOrders()) {
            if (ticketOrder.getType() == 0 && ticketOrder.getStatus() == 1) {
                //ticketOrder.get(0).setRefundable(false);
                //ticketOrder.setStatus(3);
                ticket.removeOrder(ticketOrder);
                return;
            }
        }
    }

    public boolean callNext() {
        if (queue.isEmpty()) {
            System.out.println("当前暂无患者");
            return false;
        } else {
            currentTicketID = queue.poll().getId();
            System.out.println("确认看诊后不可退款");
            //confirmTicket();
            return true;
        }
    }

    public Rx makeRx() {
        Pharmacy pharmacy = Pharmacy.getInstance();
        Registry registry = Registry.getInstance();
        Ticket ticket = registry.getById(currentTicketID);
        Rx rx = new Rx();
        //Hashtable<Medicine, Integer> medicines = pharmacy.getMedicine("tbdn");
        //for (Medicine medicine : medicines.keySet()) {
        //    System.out.println(medicine.toString());
        //}
        rx.addMedicine(pharmacy.getExactMedicine("hdh"), 1);
        // 命令行添加 结束
        System.out.println(rx.toString());
        ticket.setRx(rx);
        return rx;
    }


    /**
     * 为患者添加本次就诊记录
     *
     * @param diagnose 诊断
     * @param rx       当前处方
     */
    public void addRecord(String diagnose, Rx rx, int diseaseId) {
        RecordMgr recordMgr = RecordMgr.getInstance();
        Vector<String> medicines = new Vector<>();
        for (Item item : rx.getMedicineList().keySet()) {
            medicines.add(item.getName());
        }
        recordMgr.add(new Record(currentTicketID, new Date(), id, departmentId, diagnose, medicines, diseaseId));
        System.out.println(recordMgr.get(currentTicketID).toString());
    }


    public void offDuty() {
        queue.clear();
        currentTicketID = null;
    }


}
