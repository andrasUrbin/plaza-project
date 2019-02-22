package com.codecool.plaza.cmdprog;

import com.codecool.plaza.Exceptions.*;
import com.codecool.plaza.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class CmdProgram {

    private List<Product> cart;
    private static Scanner scan = new Scanner(System.in);
    private SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");

    CmdProgram() {
        cart = new ArrayList<Product>();
    }

    void run() {
        PlazaImpl plaza;

        while (true) {
            System.out.println("There's nothing here yet. Press 1 to create a new plaza or 2 to exit the app!\n");
            int option = inputInt();
            switch (option) {
                case 1:
                    System.out.println("Give your new plaza a name!\n");
                    scan.nextLine();
                    String plazaName = scan.nextLine();
                    plaza = new PlazaImpl(plazaName);
                    plazaMenu(plaza);
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Try 1 for creating a shop or 2 for exiting the app!");
                    break;
            }
        }
    }

    private void plazaMenu(PlazaImpl plaza) {
        System.out.println("Your new plaza is here: " + plaza.getName());
        String plazaMenuOption;
        ShopImpl myShop;
        String tempShop;
        String tempOwner;

        while (true) {

                System.out.println("Press\n" + "1: List all shops\n" +
                    "2: Add a new shop\n" +
                    "3: Remove an existing shop\n" +
                    "4: Enter a shop by name\n" +
                    "5: Check if the plaza is open or not\n" +
                    "6: Open the plaza\n" +
                    "7: Close the plaza\n" +
                    "0: Leave plaza");
                plazaMenuOption = scan.nextLine();


            try {
                switch (plazaMenuOption) {
                    case "1":
                        for (Shop shop : plaza.getShops()) {
                            System.out.println("The available shops are: " + shop.getName() + " from the owner: " + shop.getOwner());
                        }
                        break;
                    case "2":
                        System.out.println("Enter the name of the new shop!\n");
                        tempShop = scan.nextLine();
                        System.out.println("Enter the name of the shop owner!\n");
                        tempOwner = scan.nextLine();
                        try {
                            plaza.addShop(new ShopImpl(tempShop, tempOwner));
                            System.out.println("The shop: " + tempShop + " was created!");
                        } catch (ShopAlreadyExistsException e) {
                            System.out.println("This shop already exists!");
                        }
                        break;
                    case "3":
                        for (Shop shop : plaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Give me a shop name to remove!");
                        String shopToRemove = scan.nextLine();
                        try {
                            plaza.removeShop(plaza.findShopByName(shopToRemove));
                            System.out.println(shopToRemove + " is removed from the plaza");
                        } catch (NoSuchShopException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "4":
                        for (Shop shop : plaza.getShops()) {
                            System.out.println(shop.getName());
                        }
                        System.out.println("Which shop do you want to go in?");
                        tempShop = scan.nextLine();
                        try {
                            myShop = (ShopImpl) plaza.findShopByName(tempShop);
                            shopMenu(myShop);
                            break;
                        } catch (NoSuchShopException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "5":
                        if(plaza.isOpen()){
                            System.out.println("The plaza is currently open!");
                        }else{
                            System.out.println("The plaza is currently closed!");
                        }
                        break;
                    case "6":
                        plaza.open();
                        System.out.println("The plaza is now opened!");
                        break;
                    case "7":
                        plaza.close();
                        System.out.println("The plaza is now closed!");
                        break;
                    case "0":
                        System.exit(0);
                        break;
                }
            } catch (PlazaIsClosedException e) {
                System.out.println("The plaza is closed");
            }
        }
    }

    private void shopMenu(ShopImpl shop) {
        System.out.println("You're in the " + shop.getName());
        boolean wannaStay = true;
        while(wannaStay){
            System.out.println(
                "1: List all products!\n" +
                "2: Find one product by name!\n" +
                "3: Create a new product!\n" +
                "4: Add quantity to an already existing product!\n" +
                "5: Open the shop!\n" +
                "6: Close the shop!\n" +
                "7: Show the current state of the shop!\n" +
                "8: Buy an item based on the barcode!\n" +
                "0: Exit the shop");

            String menuSwitch = scan.nextLine();
            try {
                switch (menuSwitch) {
                    case "1":
                        System.out.println(shop.toString());
                        break;
                    case "2":
                        System.out.println("Give me a product name to search for: ");
                        String input = scan.nextLine();
                        try {
                            System.out.println(shop.findByName(input).toString());
                        } catch (NoSuchProductException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "3":
                        Product product = null;
                        System.out.println("Do you want to create food or cloth?");
                        boolean inCreation = true;
                        while(inCreation){
                            String chooseType = scan.nextLine();
                            if(chooseType.equals("cloth")){
                                //Creating cloth
                                System.out.println("Enter the barcode of the product!");
                                long barcode = inputLong();
                                System.out.println("Enter the name of the product!");
                                scan.nextLine();
                                String name = scan.nextLine();
                                System.out.println("Enter the manufacturer of the product!");
                                String manufacturer = scan.nextLine();
                                System.out.println("Enter the material of the product!");
                                String material = scan.nextLine();
                                System.out.println("Enter the type of the product!");
                                String type = scan.nextLine();
                                product = new ClothingProduct(barcode, name, manufacturer, material, type);
                                inCreation = false;
                            }else if(chooseType.equals("food")){
                                //Creating food
                                System.out.println("Enter the barcode of the product!");
                                long barcode = inputLong();
                                System.out.println("Enter the name of the product!");
                                scan.nextLine();
                                String name = scan.nextLine();
                                System.out.println("Enter the manufacturer of the product!");
                                String manufacturer = scan.nextLine();
                                System.out.println("Enter the calories of the product!");
                                int calories = inputInt();
                                System.out.println("Enter the date of the product! (dd-mm-yyyy)");
                                Date tdate = null;
                                String tempDate = scan.nextLine();
                                try{
                                    tdate = date.parse(tempDate);
                                }catch(ParseException e){
                                    System.out.println(e);
                                }
                                product = new FoodProduct(barcode, name, manufacturer, calories, tdate);
                                inCreation = false;

                            }else{
                                System.out.println("Not a valid option, try again!");
                            }
                        }
                        System.out.println("How many items would you like to create?");
                        int quantity = inputInt();
                        System.out.println("How much would be the price of one item?");
                        int price = inputInt();
                        try{
                            shop.addNewProduct(product, quantity, price);
                        }catch(ProductAlreadyExistsException e){
                            System.out.println(e);
                        }
                        break;
                    case "4":
                        break;
                    case "5":
                        shop.open();
                        System.out.println("The shop is now opened!");
                        break;
                    case "6":
                        shop.close();
                        System.out.println("The shop is now closed!");
                        break;
                    case "7":
                        if(shop.isOpen()){
                            System.out.println("The shop is currently open!");
                        }else{
                            System.out.println("The shop is currently closed!");
                        }
                        break;
                    case "8":
                        break;
                    case "0":
                        System.out.println("Goodbye!");
                        System.exit(0);
                }
            }catch(ShopIsClosedException e){
                e.printStackTrace();
            }
        }
    }

    static int inputInt() {
        while (true) {
            System.out.println("Give an int input:");
            try {
                return scan.nextInt();
            }catch (java.util.InputMismatchException e) {
                scan.nextLine();
            }
        }
    }

    static boolean inputBool() {
        while (true) {
            System.out.println("Give a boolean input:");
            try {
                return scan.nextBoolean();
            }catch (java.util.InputMismatchException e) {
                scan.nextLine();
            }
        }
    }

    static long inputLong() {
        while (true) {
            System.out.println("Give a long input:");
            try {
                return scan.nextLong();
            }catch (java.util.InputMismatchException e) {
                scan.nextLine();
            }
        }
    }
}
