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
import java.util.Date;

public class Order implements Serializable {
    ArrayList<Item> items;
    private double amount;
    private long id;
    private Date settleTime;
    private Date completeTime;
    private Date cancelTime;
    private int type; // 0 = ticket, 1= medicine, 2 = examination
    private int status; // 0 = unpaid, 1 = paid, 2 = cancelled, 3 = used;

    public Order() {
        this.id = System.currentTimeMillis();
        this.status = 0;
        this.amount = 0;
        items = new ArrayList<>();
    }

    public static long addOrder() {
        Order order = new Order();
        return order.getId();
    }


    public void add(Item item) {
        items.add(item);
    }

    public boolean remove(Item item) {
        if (!items.contains(item))
            return false;
        else {
            items.remove(item);
            return true;
        }
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public double getAmount() {
        double amount = 0;
        for (Item item : items) {
            amount += item.getPrice() * item.getQuantity();
        }
        this.amount = amount;
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Item get(int index) {
        return items.get(index);
    }

    public void settleForProceed() {
        double amount = 0;
        for (Item item : items) {
            amount += item.getPrice() * item.getQuantity();
        }
        this.amount = amount;
        this.settleTime = new Date();
        print();
    }

    public void settleForRefund() {
        if (this.status == 1) {
            for (Item item : items) {
                if (!item.isRefundable())
                    amount -= item.getPrice() * item.getQuantity();
            }
        }
        print();
        if (amount == 0)
            System.out.println("此订单不可退款");
    }

    public int getStatus() {
        return status;
    }

    public boolean setStatus(int status) {
        if (this.status == 0 && status == 1) {
            this.status = status;
            this.completeTime = new Date();
            return true;
        } else if (this.status == 1 && status == 2) {
            this.status = status;
            this.cancelTime = new Date();
        }

        return false;
    }

    public void print() {
        System.out.println("项目\t数量\t单价\t");
        System.out.println("========================================");
        for (Item item : items) {
            System.out.println(item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
        }
        System.out.println("========================================");
        System.out.println("金额 " + amount + "\t结算时间 " + settleTime);
        if (status == 2)
            System.out.println("已退款 " + amount + "\t退款时间 " + settleTime);

    }
}
