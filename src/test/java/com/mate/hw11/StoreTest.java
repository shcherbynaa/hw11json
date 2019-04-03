package com.mate.hw11;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StoreTest {
    private List<Fruit> fruits;
    private Store store;

    private static final String PATH = "firstDelivery.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String STORE = "store.json";
    private static final Logger LOGGER = Logger.getLogger(StoreTest.class);

    @Before
    public void init() {
        store = new Store();
        fruits = new ArrayList<>();
        Fruit apple = new Fruit(FruitType.APPLE, 10, LocalDate.of(2019, 3, 31), 20);
        Fruit banana = new Fruit(FruitType.BANANA, 15, LocalDate.of(2019, 3, 31), 30);

        fruits.add(apple);
        fruits.add(banana);

        try (FileOutputStream outputStream = new FileOutputStream(PATH)) {
            mapper.writeValue(outputStream, fruits);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @After
    public void clear() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(PATH);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        writer.print("");
        try {
            writer = new PrintWriter(STORE);
        } catch (FileNotFoundException e) {
            LOGGER.error("Exception ", e);
        }
        writer.print("");
        writer.close();
    }


    @Test
    public void addFruits() {
        store.addFruits(PATH);
        System.out.println(fruits.size());
        assertTrue(store.getStoreDatabase().containsAll(fruits));
    }

    @Test
    public void save() {
        store.addFruits(PATH);
        store.save(STORE);
        List<Fruit> expected = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(STORE)) {
            expected = mapper.readValue(inputStream, new TypeReference<List<Fruit>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        assertTrue(fruits.containsAll(expected));
    }

    @Test
    public void load() {
        store.addFruits(PATH);
        try (FileOutputStream outputStream = new FileOutputStream(STORE)) {
            mapper.writeValue(outputStream, store.getStoreDatabase());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        store.load(STORE);
        assertTrue(fruits.containsAll(store.getStoreDatabase()));
    }

    @Test
    public void getSpoiledFruits() {
        store.addFruits(PATH);
        LocalDate localDate = LocalDate.of(2019, 04, 12);
        List<Fruit> spoiledFruits = store.getSpoiledFruits(localDate);
        assertTrue(spoiledFruits.size() == 1);
    }

    @Test
    public void getAvailableFruits() {
        store.addFruits(PATH);
        List<Fruit> availableFruits = store.getAvailableFruits(LocalDate.of(2019, 04, 12));
        assertTrue(availableFruits.size() == 1);
    }
}
