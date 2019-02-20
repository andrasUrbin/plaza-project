package com.codecool.plaza.api;

import com.codecool.plaza.Exceptions.NoSuchProductException;
import com.codecool.plaza.Exceptions.OutOfStockException;
import com.codecool.plaza.Exceptions.ProductAlreadyExistsException;
import com.codecool.plaza.Exceptions.ShopIsClosedException;

import java.util.*;

public class ShopImpl implements Shop {

    private String name;
    private String owner;
    private Map<Long, ShopEntryImpl> products;
    private boolean isOpen;

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        isOpen = true;
    }

    @Override
    public void close() {
        isOpen = false;
    }

    @Override
    public Product findByName(String name) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()) {
                ShopEntryImpl value = entry.getValue();
                Product tempProduct = value.getProduct();
                if (tempProduct.getName().equals(name)) {
                    return tempProduct;
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()) {
                Long code = entry.getKey();
                if (code == barcode) {
                    return true;
                }
            }
            return false;
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if (isOpen()) {
            if (hasProduct(product.getBarcode())) {
                throw new ProductAlreadyExistsException();
            } else {
                products.put(product.getBarcode(), new ShopEntryImpl(product, quantity, price));
            }
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    value.increaseQuantity(quantity);
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    if (value.getQuantity() != 0) {
                        value.decreaseQuantity(1);
                    } else {
                        throw new OutOfStockException();
                    }
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if (isOpen()){
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()){
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key){
                    if (value.getQuantity() != 0) {
                        value.decreaseQuantity(quantity);
                    } else {
                        throw new OutOfStockException();
                    }
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public float getPrice(long barcode) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()) {
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key) {
                    return value.getPrice();
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    @Override
    public int getQuantity(long barcode) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImpl.ShopEntryImpl> entry : products.entrySet()) {
                Long key = entry.getKey();
                ShopEntryImpl value = entry.getValue();
                if (barcode == key) {
                    return value.getQuantity();
                }
            }
            throw new NoSuchProductException();
        } else {
            throw new ShopIsClosedException();
        }
    }

    private class ShopEntryImpl {
        private Product product;
        private int quantity;
        private float price;

        public ShopEntryImpl(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void increaseQuantity(int quantity) {
            this.quantity += quantity;
        }

        public void decreaseQuantity(int quantity) {
            this.quantity -= quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ShopEntryImpl{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
        }
    }

}
