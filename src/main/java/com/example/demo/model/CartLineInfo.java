package com.example.demo.model;

import com.example.demo.entity.Product;

public class CartLineInfo {
    Product product;
    int quanity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quality) {
        this.quanity = quality;
    }
    public double getAmount() {
        return this.product.getPrice() * this.quanity;
    }

    @Override
    public String toString() {
        return "CartLineInfo{" +
                "product=" + product.toString() +
                ", quanity=" + quanity +
                '}';
    }
}
