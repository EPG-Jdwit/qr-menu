package com.joshua.qrmenu.util.mocker.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductNameMocker {

    private int createdCounter = 0;

    private final List<String> shuffledNames;

    public ProductNameMocker() {
        shuffledNames = Arrays.asList(names);
        Collections.shuffle(shuffledNames);
    }

    /**
     * Randomly pick a name and add the createdCounter to it to avoid random conflicts that could happen.
     * @return : A random product name.
     */
    public String productName() {
        return shuffledNames.get(createdCounter++ % names.length) + createdCounter;
    }

    private final String[] names = {
            "Stella 25",
            "Stella 33",
            "Rodenbach 25",
            "Rodenbach 33",
            "Vol-au-vent met frietjes",
            "Chouffe",
            "Spaghetti bolognaise"
    };
}
