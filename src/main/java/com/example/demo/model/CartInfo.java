package com.example.demo.model;

import com.example.demo.entity.Product;

import java.util.*;

public class CartInfo {
    private int orderNum;

    private CustomerInfo customerInfo;

    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

    public CartInfo() {

    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<CartLineInfo> getCartLines() {
        return this.cartLines;
    }

    private CartLineInfo findLineByCode(String code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProduct().getCode().equals(code)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(Product productInfo, int quantity) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuanity(0);
            line.setProduct(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuanity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuanity(newQuantity);
        }
    }

    public void validate() {

    }

    public void updateProduct(String code, int quantity) {
        CartLineInfo line = this.findLineByCode(code);

        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {

                    line.setQuanity(quantity);


            }
        }
    }

    public void removeProduct(Product productInfo, int quantity) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());
        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                int newQuantity = line.getQuanity() - quantity;
                if(newQuantity<=0){
                    this.cartLines.remove(line);
                }else {
                    line.setQuanity(newQuantity);
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuanity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }

    public void updateQuantity(CartInfo cartForm) {
        if (cartForm != null) {
            List<CartLineInfo> lines = cartForm.getCartLines();
            for (CartLineInfo line : lines) {
                this.updateProduct(line.getProduct().getCode(), line.getQuanity());
            }
        }

    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "orderNum=" + orderNum +
                ", customerInfo=" + customerInfo.toString() +
                ", cartLines=" + cartLines.toString() +
                '}';
    }
}
