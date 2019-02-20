package com.codecool.plaza.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodProduct extends Product {

    private int calories;
    private Date bestBefore;
    public String timeStamp = new SimpleDateFormat("dd-mm-yyyy").format(Calendar.getInstance().getTime());


    public FoodProduct(long barcode, String name, String manufacturer, int calories, Date bestBefore) {
        super(barcode, name, manufacturer);
        this.calories = calories;
        this.bestBefore = bestBefore;
    }



    public boolean isStillConsumable(String timeStamp) throws ParseException {
        if (new SimpleDateFormat("MM/yyyy").parse(timeStamp).before(new Date())) {
            return true;
        }
        return false;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public int getCalories() {
        return calories;
    }

}
