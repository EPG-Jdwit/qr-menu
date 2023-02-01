package com.joshua.qrmenu.util.mocker.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubcategoryNameMocker {

    private int createdCounter = 0;

    private List<String> shuffledNames;

    public SubcategoryNameMocker() {
        shuffledNames = Arrays.asList(names);
        Collections.shuffle(shuffledNames);
    }

    public String subcategoryName() {
        return shuffledNames.get(createdCounter++ % names.length);
    }

    private final String[] names = {
            "Trappist",
            "Pasta met vlees",
            "Pasta met vis",
            "Non-alcoholisch",
            // TODO:   Subcategory(subcategoryId=2504552591575445, name=TheeÃ«n)
            // "Theeën",
            "Croques",
            "Kroketten",
            "Fruitsappen",
            "Ijskoffie",
            "Bruine bieren",
            "Blonde bieren",
            "Amber bieren",
            "Witte wijnen",
            "Rode wijnen",
            "Rose wijnen"
    };
}
