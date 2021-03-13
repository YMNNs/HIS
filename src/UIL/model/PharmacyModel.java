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

import BLL.Medicine;
import javafx.beans.property.SimpleStringProperty;

public class PharmacyModel {
    private final SimpleStringProperty alias;
    private final SimpleStringProperty medicineName;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty unit;
    private final SimpleStringProperty usage;

    public PharmacyModel(Medicine medicine, int quantity) {
        this.alias = new SimpleStringProperty(medicine.getAlias());
        this.medicineName = new SimpleStringProperty(medicine.getName());
        this.quantity = new SimpleStringProperty(quantity + "");
        this.unit = new SimpleStringProperty(medicine.getUnit());
        this.usage = new SimpleStringProperty(medicine.getUsage());
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

    public String getMedicineName() {
        return medicineName.get();
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.set(medicineName);
    }

    public SimpleStringProperty medicineNameProperty() {
        return medicineName;
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
}
