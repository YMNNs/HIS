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
import UIL.view.DoctorStationController;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class Pharmacy implements Serializable {
    private static Pharmacy instance = new Pharmacy(); // 单例模式
    Hashtable<Medicine, Integer> store; // 库存

    private Pharmacy() {
        store = new Hashtable<>();
    }

    public static Pharmacy getInstance() {
        return instance;
    }

    public void addMedicine(Medicine medicine, int stock) {
        store.put(medicine, stock);
    }

    public int removeMedicine(Medicine medicine) {
        return store.remove(medicine);
    }

    public void setStock(Medicine medicine, int stock) {
        store.replace(medicine, store.get(medicine), stock);
    }

    public void search(String query) {
        for (Medicine medicine : store.keySet()) {
            if (medicine.getName().contains(query) || medicine.getAlias().contains(query))
                DoctorStationController.addToResult(medicine, store.get(medicine));
        }
        System.out.println("已添加全部药品");
    }

    public boolean lockStock(Medicine medicine, int amount) {
        //load();
        System.out.println(store);
        int stock = store.get(medicine) - amount;
        if (stock >= 0) {
            store.replace(medicine, stock);
            save();
            return true;
        } else {
            save();
            return false;
        }
    }

    public int getStock(Medicine medicine) {
        return store.get(medicine);
    }

    public Hashtable<Medicine, Integer> getMedicine(String query) {
        load();
        Hashtable<Medicine, Integer> result = new Hashtable<>();
        for (Map.Entry<Medicine, Integer> entry : store.entrySet()) {
            if (entry.getKey().getAlias().contains(query) || entry.getKey().getName().contains(query))
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public Medicine getExactMedicine(String alias) {
        for (Medicine medicine : store.keySet()) {
            if (medicine.getAlias().equalsIgnoreCase(alias))
                return medicine;
        }
        return null;
    }

    public void offerMedicine(Rx rx) {
        rx.setStatus(2);
        System.out.println("已取药");
    }

    public int sendRx(Rx rx) {
        switch (rx.getStatus()) {
            case 1: {
                rx.setStatus(2);
                System.out.println("已取药");
                return 1;
            }
            case 0: {
                System.out.println("未付款");
                return 0;
            }
            case 2: {
                System.out.println("已使用");
                return 2;
            }
            case 3: {
                System.out.println("已取消");
                return 3;
            }
            default: {
                System.out.println("未知错误");
                return 4;
            }
        }
    }


    public void init() {
        store = new Hashtable<>();
        store.put(new Medicine("hzy", "耗子药", 20, "盒", "一日三次，一次一盒"), 100);
        store.put(new Medicine("zcg", "痔疮膏", 10, "盒", "一日三次，一次一盒"), 100);
        store.put(new Medicine("qlk", "前列康", 50, "盒", "一日三次，一次一盒"), 100);
        store.put(new Medicine("hdh", "鹤顶红", 100, "盒", "一日三次，一次一盒"), 100);
        save();
    }

    public void load() {
        try {
            Pharmacy instance1 = DataControl.readMedicine();
            if (instance1 != null)
                instance = instance1;
            else init();
        } catch (Exception e) {
            System.err.println("药品数据已损坏并重建");
            init();
        }
    }

    public void save() {
        DataControl.writeMedicine(instance);
    }

}
