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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Department implements Serializable {
    private String alias;
    private int id;
    private String name;
    private boolean isEmergency;
    //private Set<Doctor> doctors;
    private ArrayList<Integer[]> shiftTable;
    private PriorityQueue<Ticket> queue;

    public Department(int id, String alias, String name, boolean isEmergency, ArrayList<Integer[]> shiftTable) {
        this.shiftTable = shiftTable;
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.isEmergency = isEmergency;
        this.queue = new PriorityQueue<>();
    }

    public PriorityQueue<Ticket> getQueue() {
        if (queue.size() == 0)
            System.err.println("队列中没有病人");
        return queue;
    }

    public void setQueue(PriorityQueue<Ticket> queue) {
        this.queue = queue;
    }

    public ArrayList<Integer[]> getShiftTable() {
        return shiftTable;
    }

    public void setShiftTable(ArrayList<Integer[]> shiftTable) {
        this.shiftTable = shiftTable;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    /**
     * 排班表定义
     * int,int,int,int,int,int,int,int
     * int[0] = doctorId
     * int[1-7] = Each day of the week (form Sun. to Mon.) available tickets.
     * Split with ","
     */

    public boolean setShift(int doctorId, String shift) {
        if (shiftTable == null)
            shiftTable = new ArrayList<>();
        if (shift.matches("([0-9]\\d*,){6}[0-9]\\d*")) {
            String[] shiftArr = shift.split(",");
            Integer[] shiftInt = new Integer[8];
            shiftInt[0] = doctorId;
            for (int i = 0; i < shiftArr.length; i++) {
                shiftInt[i + 1] = Integer.parseInt(shiftArr[i]);
            }
            shiftTable.add(shiftInt);
            return true;
        }
        return false;
    }

    public boolean setShift(int doctorId) {
        return setShift(doctorId, "10,10,10,10,10,10,10");
    }

    public Hashtable<Integer, Integer> getAvailableDoctors() {
        Hashtable<Integer, Integer> table = new Hashtable<>();
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        if (shiftTable == null || shiftTable.size() == 0) {
            return null;
        } else {
            for (Integer[] integers : shiftTable) {
                if (integers[today] > 0)
                    table.put(integers[0], integers[today]);
            }
        }
        return table;
    }

    //重写方法加入分诊队列
    /*public void register(Ticket ticket) {
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        for (Integer[] integers : shiftTable) {
            if (integers[0] == ticket.getDoctorId())
                integers[today]--;
        }
        ticket.getDoctor().add(ticket);
    }*/
    public boolean register(Ticket ticket) {
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        int available = 0;
        for (Integer[] integers : shiftTable) {
            available += integers[today];
        }
        if (available > 0) {
            queue.add(ticket);
            return true;
        } else {
            System.err.println("当日无可用号");
            return false;
        }

    }

    public void cancel(Ticket ticket) {
       /* Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        for (Integer[] integers : shiftTable) {
            if (integers[0] == ticket.getDoctorId())
                integers[today]++;
        }*/
        if (ticket.getDoctorId() != -1) {
            ticket.getDoctor().remove(ticket);
            modifyShift(ticket.getDoctor(), true);
            ticket.setDoctorId(-1);
        } else {
            queue.remove(ticket);
        }
    }

    /**
     * 更新医生的剩余号
     *
     * @param doctor   医生
     * @param isDecent 为真减1
     */
    public void modifyShift(Doctor doctor, boolean isDecent) {
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        for (Integer[] integers : shiftTable) {
            if (integers[0] == doctor.getId()) {
                if (isDecent) {
                    integers[today]--;
                } else {
                    integers[today]++;
                }
                break;
            }
        }

    }
}
