package com.codecool.plaza.cmdprog;

import com.codecool.plaza.Exceptions.*;
import com.codecool.plaza.api.PlazaImpl;
import com.codecool.plaza.api.Product;
import com.codecool.plaza.api.Shop;
import com.codecool.plaza.api.ShopImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CmdProgram {

    private List<Product> cart;
    private Scanner scan = new Scanner(System.in);
    SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");

    CmdProgram() {
        cart = new ArrayList<Product>();
    }

    public void run() {
        PlazaImpl plaza;

        while (true) {
            System.out.println("There's nothing here yet. Press 1 to create a new plaza or 2 to exit the app!\n");
            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Give your new plaza a name!\n");
                    String plazaName = scan.nextLine();
                    plaza = new PlazaImpl(plazaName);
                    plazaMenu(plaza);
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    System.exit(0);
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
                break;
            }
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
                        break;
                }
            } catch (PlazaIsClosedException e) {
                System.out.println("The plaza is closed");
            }
        }
    }

    private void shopMenu(ShopImpl shop){
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
}
