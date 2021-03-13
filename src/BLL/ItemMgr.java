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
import java.util.HashSet;

public class ItemMgr implements Serializable {
    private static ItemMgr itemMgr = new ItemMgr();
    private HashSet<Item> items;

    private ItemMgr() {
        items = new HashSet<>();
    }

    public static ItemMgr getInstance() {
        return itemMgr;
    }

    public Item get(String query) {
        for (Item item : items) {
            if (item.getAlias().equals(query) || item.getName().equals(query))
                return item;
        }
        return null;
    }

    public void search(String query) {
        for (Item item : items) {
            if (item.getType() == 1) {
                if (item.getAlias().contains(query) || item.getName().contains(query)) {
                    DoctorStationController.addToResult(item);
                }
            }
        }
        System.out.println("已添加全部检查结果");
    }

    /**
     * 初始化一些物品
     */
    public void init() {
        items = new HashSet<>();
        items.add(new Item("pth", "普通号", 8, true, 0));
        items.add(new Item("zjh", "专家号", 20, true, 0));
        items.add(new Item("jzh", "急诊号", 16, true, 0));
        items.add(new Item("blb", "病历本", 1, true, 4));
        items.add(new Item("xcg", "血常规", 50, false, 1));
        items.add(new Item("ncg", "尿常规", 50, false, 1));
        items.add(new Item("dbcg", "大便常规", 50, false, 1));
        save();
    }

    public void load() {
        try {
            ItemMgr itemMgr1 = DataControl.readItem();
            if (itemMgr1 != null)
                itemMgr = itemMgr1;
            else init();
        } catch (Exception e) {
            init();
            System.err.println("物品数据已损坏并重建");
        }
    }

    public void save() {
        DataControl.writeItem(itemMgr);
    }
}
