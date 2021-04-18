package com.coffee.machine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Drink {

    //enum of drink and its recipe
    Coffee("4_CoffeeBeans","2_Milk","2_Sugar","2_Water"),
    Milk("4_Milk","2_Sugar"),
    Tea("5_TeaLeaves","1_Milk","2_Sugar","2_Water");

    private final String drink;
    private Map<Ingredients, Integer> recipes = new HashMap<>();
    private double cost;

    Drink( String... recipe) {
        Map<Ingredients,Integer> make = new HashMap<>();
        double cost = 0;
        for (String s:
             recipe) {
            String[] str = s.split("_");
            Ingredients ingredient = Enum.valueOf(Ingredients.class,str[1]);
            int quantity = Integer.parseInt(str[0]);
            make.put(ingredient,quantity);
            cost+=quantity*ingredient.cost;
        }
        this.drink=name();
        this.recipes= Collections.unmodifiableMap(make);
        this.cost=cost;
    }
    public Map<Ingredients, Integer> getRecipes() {
        return recipes;
    }
    public double getCost() {
        return cost;
    }
}
