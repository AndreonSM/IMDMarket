package com.example.imdmarket.models;

public class Product {
    private long id;
    private String name;
    private double prod_code;
    private String description;
    private double stock_quantity;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getStock_quantity() {
        return stock_quantity;
    }
    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
    public double getProd_code() {
        return prod_code;
    }
    public void setProd_code(double prod_code) {
        this.prod_code = prod_code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Constructors
    public Product() {
        // Default constructor
    }

    public Product(String name, double prod_code, String description, double stock_quantity) {
        this.name = name;
        this.stock_quantity = stock_quantity;
        this.prod_code = prod_code;
        this.description = description;
    }

}
