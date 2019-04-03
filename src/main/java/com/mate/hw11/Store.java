package com.mate.hw11;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;

public class Store {
    private static final Logger LOGGER = Logger.getLogger(Store.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String PATH = "delivery.json";

    private List<Fruit> storeDatabase;
    private static List<Fruit> fruits;

    public Store() {
        storeDatabase = new ArrayList<>();
    }

    public List<Fruit> getStoreDatabase() {
        return storeDatabase;
    }

    public void addFruits(String pathToJsonFile) {
        List<Fruit> delivery;
        try (FileInputStream inputStream = new FileInputStream(pathToJsonFile)) {
            delivery = mapper.readValue(inputStream, new TypeReference<List<Fruit>>() {
            });
            storeDatabase.addAll(delivery);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void save(String pathToJsonFile) {
        try (FileOutputStream outputStream = new FileOutputStream(pathToJsonFile)) {
            mapper.writeValue(outputStream, storeDatabase);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void load(String pathToJsonFile) {
        storeDatabase.clear();
        try (FileInputStream inputStream = new FileInputStream(pathToJsonFile)) {
            storeDatabase = mapper.readValue(inputStream, new TypeReference<List<Fruit>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public List<Fruit> getSpoiledFruits(LocalDate date) {
        List<Fruit> listSpoiledFruits = new ArrayList<>();
        storeDatabase.forEach(fruit -> {
            LocalDate spoiledAt = fruit.getDateOfDelivery().plusDays(fruit.getExpirationDate());
            if (date.isAfter(spoiledAt)) {
                listSpoiledFruits.add(fruit);
            }
        });
        return listSpoiledFruits;
    }

    public List<Fruit> getSpoiledFruits(LocalDate date, FruitType type) {
        List<Fruit> listSpoiledFruits = new ArrayList<>();

        storeDatabase.forEach(fruit -> {
            LocalDate spoiledAt = fruit.getDateOfDelivery().plusDays(fruit.getExpirationDate());
            if (date.isAfter(spoiledAt) && (fruit.getFruitType() == type)) {
                listSpoiledFruits.add(fruit);
            }
        });
        return listSpoiledFruits;
    }


    public List<Fruit> getAvailableFruits(LocalDate date) {
        List<Fruit> listAvailableFruits = new ArrayList<>();
        storeDatabase.forEach(fruit -> {
            LocalDate spoiledAt = fruit.getDateOfDelivery().plusDays(fruit.getExpirationDate());
            if (date.isBefore(spoiledAt)) {
                listAvailableFruits.add(fruit);
            }
        });
        return listAvailableFruits;
    }

    public List<Fruit> getAvailableFruits(LocalDate date, FruitType type) {
        List<Fruit> listAvailableFruits = new ArrayList<>();
        storeDatabase.forEach(fruit -> {
            LocalDate spoiledAt = fruit.getDateOfDelivery().plusDays(fruit.getExpirationDate());
            if (date.isBefore(spoiledAt) && (fruit.getFruitType() == type)) {
                listAvailableFruits.add(fruit);
            }
        });
        return listAvailableFruits;
    }

    public List<Fruit> getAddedFruits(LocalDate date) {
        fruits = new ArrayList<>();
        storeDatabase.forEach(fruit -> {
            if (fruit.getDateOfDelivery().equals(date)){
                fruits.add(fruit);
            }
        });
        return fruits;
    }

    public List<Fruit> getAddedFruits(LocalDate date,  FruitType type) {
        fruits = new ArrayList<>();
        storeDatabase.forEach(fruit -> {
            if (fruit.getDateOfDelivery().equals(date) && (fruit.getFruitType().equals(type))){
                fruits.add(fruit);
            }
        });
        return fruits;
    }
}
