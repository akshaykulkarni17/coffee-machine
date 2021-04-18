package com.coffee.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DrinkMachine {

    //HashMap to sore the stock value of ingredients
    private Map<Ingredients,Integer> stock = new HashMap<>();

    //get stock of a particular ingredient
    private int getStock(Ingredients ingredient){
        return this.stock.get(ingredient);
    }

    //stock 10 units at initialization, add further after restock
    private void reStock(){
        for (Ingredients ingredient:
             Ingredients.values()) {
            if (!this.stock.containsKey(ingredient))this.stock.put(ingredient,10);
            else this.stock.put(ingredient,this.getStock(ingredient)+10);
        }
    }

    //check if enough stock available to make a drink
    private boolean canMake(Drink drink){
        Map<Ingredients,Integer> recipe= drink.getRecipes();
        for (Ingredients i:
             recipe.keySet()) {
            if (this.getStock(i)<recipe.get(i)) return false;
        }
        return true;
    }

    //order placed, make a drink, reduce the stock
    private void makeDrink(Drink drink){
        if (!canMake(drink)) throw new OutOfStockException(drink);
        Map<Ingredients,Integer> recipe= drink.getRecipes();
        for (Ingredients i:
             recipe.keySet()) {
            this.stock.put(i,this.getStock(i)-recipe.get(i));
        }
    }

    //throw exception for not enough stock
    private class OutOfStockException extends RuntimeException {
         OutOfStockException(Drink drink) {
             super( "The drink " + drink + " is out of stock!!");
        }
    }

    //Dashboard screen
    private String output(){
        StringBuilder output  = new StringBuilder();
        output.append("\nWelcome to Drink Machine! \nWhat would you have today?\n");
        output.append("\nInventory\t:\tStock\n");
        output.append("-----------------------------\n");
        for (Ingredients i : Ingredients.values()) {
            output.append(i);
            output.append("\t:\t");
            output.append(this.getStock(i)+"\n");
        }
        output.append("\n\nIndex\t:\tDrink\t:\tCost\n");
        output.append("-----------------------------\n");
        int index=1;
        for (Drink d:
             Drink.values()) {
            output.append(index+"\t:\t"+d);
            output.append("\t:\t");
            output.append("₹ " + d.getCost() + "\n");
            index++;
        }
        output.append("\nEnter the index to order your drink. Enter 'q' to shutdown.");
        return output.toString();
    }
    public static void main(String[] args) {
        DrinkMachine machine = new DrinkMachine();
        machine.reStock();
        Scanner scanner = new Scanner(System.in);
        System.out.println(machine.output());
        while (true){
            String input = scanner.nextLine().toLowerCase();
            if (input.isEmpty()){
                continue;
            }
            else if (input.equals("r")){
                machine.reStock();
                System.out.println("Stock is refilled");
                System.out.println(machine.output());
            }
            else if (input.equals("q")){
                System.out.println("Shutting down!");
                break;
            }
            else
            try {
                int selection = Integer.parseInt(input);
                Drink order = Drink.values()[selection-1];
                System.out.println("Preparing drink\n");
                Thread.sleep(2000);
                machine.makeDrink(order);
                System.out.println("Dispensing:\t" + order.name()+"\n");
                Thread.sleep(1000);
                System.out.println(machine.output());
                }
            catch (OutOfStockException oos){
                    System.err.println(oos.getMessage()+"\n");
                    System.out.println("Enter 'r' to restock");
                }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException invalid){
                    System.err.println("Invalid input: "+input+"\n");
                    System.out.println(machine.output());
                }
            catch (InterruptedException e) {
                }
        }
    }

}
