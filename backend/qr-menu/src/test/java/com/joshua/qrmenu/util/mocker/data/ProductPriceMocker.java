package com.joshua.qrmenu.util.mocker.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductPriceMocker {

    private int createdCounter;

    private final List<Double> shuffledPrices;

    public ProductPriceMocker() {
        shuffledPrices = Arrays.asList(prices);
        Collections.shuffle(shuffledPrices);
    }

    /**
     * Semi-randomly pick a product price.
     * @return : A semi-random product price.
     */
    public Double productPrice() {
        return shuffledPrices.get(createdCounter++ % prices.length);
    }

    private Double[] prices = {
            2.90,
            3.00,
            3.10,
            3.20,
            3.30,
            3.40,
            3.50
    };
}
