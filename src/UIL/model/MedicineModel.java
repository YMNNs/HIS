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

package UIL.model;

import BLL.Item;
import BLL.Medicine;
import javafx.beans.property.SimpleStringProperty;

public class MedicineModel {
    private final SimpleStringProperty alias;
    private final SimpleStringProperty stockName;
    private final SimpleStringProperty unit;
    private final SimpleStringProperty usage;
    private final SimpleStringProperty price;
    private final SimpleStringProperty stock;

    public MedicineModel(Medicine medicine, int stock) {
        this.alias = new SimpleStringProperty(medicine.getAlias());
        this.stockName = new SimpleStringProperty(medicine.getName());
        this.unit = new SimpleStringProperty(medicine.getUnit());
        this.usage = new SimpleStringProperty(medicine.getUsage());
        this.price = new SimpleStringProperty(medicine.getPrice() + "");
        String stockStr = stock + "";
        this.stock = new SimpleStringProperty(stockStr);
    }

    public MedicineModel(Item item) {
        this.alias = new SimpleStringProperty(item.getAlias());
        this.stockName = new SimpleStringProperty(item.getName());
        this.unit = new SimpleStringProperty("次");
        this.usage = new SimpleStringProperty("");
        this.price = new SimpleStringProperty(item.getPrice() + "");
        this.stock = new SimpleStringProperty("不适用");
    }

    public String getAlias() {
        return alias.get();
    }

    public void setAlias(String alias) {
        this.alias.set(alias);
    }

    public SimpleStringProperty aliasProperty() {
        return alias;
    }

    public String getStockName() {
        return stockName.get();
    }

    public void setStockName(String stockName) {
        this.stockName.set(stockName);
    }

    public SimpleStringProperty stockNameProperty() {
        return stockName;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public String getUsage() {
        return usage.get();
    }

    public void setUsage(String usage) {
        this.usage.set(usage);
    }

    public SimpleStringProperty usageProperty() {
        return usage;
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public String getStock() {
        return stock.get();
    }

    public void setStock(String stock) {
        this.stock.set(stock);
    }

    public SimpleStringProperty stockProperty() {
        return stock;
    }
}
