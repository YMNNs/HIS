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
import java.util.Hashtable;

public class Rx implements Serializable {

    private Hashtable<Item, Integer> medicineList;
    private long id;
    private int status; // 0 = unpaid, 1 = paid, 2 = used, 3 = canceled

    public Rx() {
        this.medicineList = new Hashtable<>();
        this.id = System.currentTimeMillis();
        this.status = 0;
    }

    public Hashtable<Item, Integer> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(Hashtable<Item, Integer> medicineList) {
        this.medicineList = medicineList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) { // 0 = unpaid, 1 = paid, 2 = used, 3 = canceled
        this.status = status;
    }

    public void addMedicine(Item item, int amount) {
        //Pharmacy pharmacy = Pharmacy.getInstance();
        //if (pharmacy.lockStock(medicine, amount))
        medicineList.put(item, amount);
    }


    //public void sendToPharmacy() {
    //    Pharmacy pharmacy = Pharmacy.getInstance();
    //    pharmacy.sendRx(this);
    //}

    public Item toItem() {
        boolean isExam = false;
        double amount = 0;
        for (Item item : medicineList.keySet()) {
            amount += item.getPrice() * medicineList.get(item);
            if (item.getType() == 1)
                isExam = true;
        }
        if (isExam)
            return new Item("jcf", "检查费", amount, true, 3);
        else
            return new Item("xyf", "西药费", amount, true, 3);
    }

    public void printAll() {
        System.out.println();
        for (Item item : medicineList.keySet()) {
            System.out.println(item.toString() + "\t" + medicineList.get(item));
        }
    }
}
