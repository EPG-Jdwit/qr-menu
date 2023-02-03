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

    /**
     * Randomly pick a name and add the createdCounter to it to avoid random conflicts that could happen.
     * @return : A random category name.
     */
    public String categoryName() {
        return shuffledNames.get(createdCounter++ % names.length) + createdCounter;
    }

    private final String[] names = {
            "Bieren",
            "Pastagerechten",
            "Frietgerechten",
            "Frisdranken",
            "Slaatjes",
            "Warme dranken",
            "Wijnen",
            "Borrelhapjes",
            "Soepen",
            "Digestieven",
            "Aperitieven",
            "Whisky's",
            "Thee",
            "Kindergerechten",
            "Tearoom"
    };
}
