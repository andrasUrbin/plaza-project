package com.codecool.plaza.api;

public abstract class Product {

    long barcode;
    String name;
    String manufacturer;

    Product(long barcode, String name, String manufacturer) {
        this.barcode = barcode;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    long getBarcode() {
        return barcode;
    }

    String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return "Product{" +
            "barcode=" + barcode +
            ", name='" + name + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            '}';
    }
}
