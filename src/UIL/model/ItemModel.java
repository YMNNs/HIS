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

public class ItemModel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty price;
    private final SimpleStringProperty refundable;
    private final SimpleStringProperty subtotal;

    public ItemModel(String fName, int fQuantity, double fPrice, boolean fRefundable) {
        this.name = new SimpleStringProperty(fName);
        this.quantity = new SimpleStringProperty(fQuantity + "");
        this.price = new SimpleStringProperty(fPrice + "");
        if (fRefundable)
            this.refundable = new SimpleStringProperty("可退款");
        else this.refundable = new SimpleStringProperty("不可退款");
        double fSubtotal = fQuantity * fPrice;
        this.subtotal = new SimpleStringProperty(fSubtotal + "");
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
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

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public String getRefundable() {
        return refundable.get();
    }

    public void setRefundable(String refundable) {
        this.refundable.set(refundable);
    }

    public SimpleStringProperty refundableProperty() {
        return refundable;
    }

    public String getSubtotal() {
        return subtotal.get();
    }

    public void setSubtotal(String subtotal) {
        this.subtotal.set(subtotal);
    }

    public SimpleStringProperty subtotalProperty() {
        return subtotal;
    }
}
