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
import java.util.Date;
import java.util.Hashtable;

public class Registry implements Serializable {
    private static Registry registry = new Registry();
    private Hashtable<Ticket, Date> hashtable;

    private Registry() {
        hashtable = new Hashtable<>();
    }

    public static Registry getInstance() {
        return registry;
    }

    public boolean register(Ticket ticket) {
        if (ticket.getStatus()) {
            if (hashtable == null)
                hashtable = new Hashtable<>();
            hashtable.put(ticket, new Date());
            if (!ticket.getDepartment().register(ticket)) {
                System.out.println("挂号失败");
                return false;
            }

        } else {
            System.out.println("未付款");
            return false;
        }
        save();
        return true;
    }

    public Ticket getByIdNumber(String idNumber) {
        for (Ticket ticket1 : hashtable.keySet()) {
            if (ticket1.getIdNumber().equalsIgnoreCase(idNumber))
                return ticket1;
        }
        System.out.println("身份证号输入有误");
        return null;
    }

    public Ticket getById(String id) {
        for (Ticket ticket1 : hashtable.keySet()) {
            if (ticket1.getId().equalsIgnoreCase(id))
                return ticket1;
        }
        System.out.println("病历号输入有误");
        return null;
    }


    public boolean cancelTicket(Ticket ticket) {
        if (!ticket.getStatus()) {
            return false;
        } else {
            ticket.setStatus(false);
            ticket.getDepartment().cancel(ticket);
            save();
            return true;
        }

    }

    public void load() {
        try {
            Registry registry1 = DataControl.readRegistry();
            if (registry1 != null) {
                registry = registry1;
            } else {
                init();
            }
        } catch (Exception e) {
            init();
            System.err.println("挂号数据已损坏并重建");
        }
    }

    public void save() {
        try {
            DataControl.writeRegistry(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        registry = new Registry();
    }

}
