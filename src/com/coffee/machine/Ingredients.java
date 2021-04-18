package com.coffee.machine;

public enum Ingredients {

    //enum for ingredient and its cost
    CoffeeBeans(4.00),
    Milk(3.00),
    Sugar(2.50),
    TeaLeaves(3.50),
    Water(1.00);

    public String name;
    public double cost;

    Ingredients(double cost) {
        this.name=name();
        this.cost=cost;
    }
}

