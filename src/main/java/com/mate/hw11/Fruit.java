package com.mate.hw11;

import java.time.LocalDate;

public class Fruit {

    private FruitType fruitType;
    private int expirationDate;  //10 days, 20 days
    private LocalDate dateOfDelivery;
    private int price;

    public Fruit(FruitType fruitType, int expirationDate, LocalDate dateOfDelivery, int price) {
        this.fruitType = fruitType;
        this.expirationDate = expirationDate;
        this.dateOfDelivery = dateOfDelivery;
        this.price = price;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public void setFruitType(FruitType fruitType) {
        this.fruitType = fruitType;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
