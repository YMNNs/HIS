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

import javafx.beans.property.SimpleStringProperty;

public class RxModel {
    private final SimpleStringProperty rxName;
    private final SimpleStringProperty quantity;
    private String alias;
    private int type; // 1 药品 2 检查

    public RxModel(MedicineModel medicineModel, int quantity, int type) {
        this.rxName = new SimpleStringProperty(medicineModel.getStockName());
        String quantityStr = quantity + "";
        this.quantity = new SimpleStringProperty(quantityStr);
        this.alias = medicineModel.getAlias();
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public String getRxName() {
        return rxName.get();
    }

    public void setRxName(String rxName) {
        this.rxName.set(rxName);
    }

    public SimpleStringProperty rxNameProperty() {
        return rxName;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }
}
