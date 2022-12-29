package com.joshua.qrmenu.util.mocker.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDescriptionMocker {

    private int createdCounter = 0;

    private List<String> shuffledDescriptions;

    public ProductDescriptionMocker() {
        shuffledDescriptions = Arrays.asList(descriptions);
        Collections.shuffle(shuffledDescriptions);
    }

    public String productDescription() {
        return shuffledDescriptions.get(createdCounter++ % descriptions.length);
    }

    private String[] descriptions = {
            "first description",
            "second description",
            "third description",
            "fourth description",
            "fifth description",
            "sixth description"
    };
}
