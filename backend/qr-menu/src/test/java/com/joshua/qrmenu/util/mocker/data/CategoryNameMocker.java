package com.joshua.qrmenu.util.mocker.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CategoryNameMocker {

    private int createdCounter = 0;

    private List<String> shuffledNames;

    public CategoryNameMocker() {
        shuffledNames = Arrays.asList(names);
        Collections.shuffle(shuffledNames);
    }

    public String categoryName() {
        return shuffledNames.get(createdCounter++ % names.length);
    }

    private final String[] names = {
            "Bieren",
            "Pastagerechten",
            "Frietgerechten",
            "Frisdranken",
            "Slaatjes",
            "Warme dranken",
            "Wijnen",
            "Borrelhapjes"
    };
}
