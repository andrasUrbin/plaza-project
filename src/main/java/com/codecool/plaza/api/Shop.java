package com.codecool.plaza.api;

import com.codecool.plaza.Exceptions.*;

import java.util.List;

public interface Shop {

    String getName();

    String getOwner();

    boolean isOpen();

    void open();

    void close();

    Product findByName(String name) throws ShopIsClosedException, NoSuchProductException;

    boolean hasProduct(long barcode) throws ShopIsClosedException;

    void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException;

    void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException;

    Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException, OutOfStockException;

    List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException;

    float getPrice(long barcode) throws NoSuchProductException, ShopIsClosedException;

    int getQuantity(long barcode) throws NoSuchProductException, ShopIsClosedException;

    String toString();
}

