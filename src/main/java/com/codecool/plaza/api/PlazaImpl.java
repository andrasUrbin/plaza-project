package com.codecool.plaza.api;

import com.codecool.plaza.Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlazaImpl implements Plaza{

    private List<Shop> shops;
    private String name;
    private boolean open;

    public PlazaImpl(List<Shop> shops, String name, boolean open) {
        this.shops = shops;
        this.name = name;
        this.open = open;
    }

    public PlazaImpl(String name) {
        shops = new ArrayList<Shop>();
        this.name = name;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        if (isOpen()){
            return shops;
        }else{
            throw new PlazaIsClosedException();
        }
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        if (isOpen()){
            for(Shop iterateShop : shops){
                if(iterateShop.getName().equals(shop.getName())) {
                    throw new ShopAlreadyExistsException();
                }
            }
            shops.add(shop);
        }else{
            throw new PlazaIsClosedException();
        }
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (isOpen()){
            if(shops.contains(shop)){
                shops.remove(shop);
            }else{
                throw new NoSuchShopException();
            }
        }else{
            throw new PlazaIsClosedException();
        }
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        if (isOpen()) {
            for (Shop iterateShop : shops){
                if(iterateShop.getName().equals(name)){
                    return iterateShop;
                }else{
                    throw new NoSuchShopException();
                }
            }
            return null;
        } else {
            throw new PlazaIsClosedException();
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void open() {
        open = true;
    }

    @Override
    public void close() {
        open = false;
    }

    public String getName() {
        return name;
    }
}
