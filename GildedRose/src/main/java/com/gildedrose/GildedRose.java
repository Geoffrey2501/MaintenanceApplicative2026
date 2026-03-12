package com.gildedrose;

import com.gildedrose.strategy.*;

import java.util.HashMap;
import java.util.Map;

class GildedRose {
    Item[] items;

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured Mana Cake";

    private final Map<String, UpdateStrategy> strategies = new HashMap<>();
    private final UpdateStrategy defaultStrategy = new NormalItemStrategy();

    public GildedRose(Item[] items) {
        this.items = items;
        strategies.put(AGED_BRIE, new AgedBrieStrategy());
        strategies.put(BACKSTAGE_PASSES, new BackstagePassStrategy());
        strategies.put(SULFURAS, new SulfurasStrategy());
        strategies.put(CONJURED, new ConjuredItemStrategy());
    }

    public void updateQuality() {
        for (Item item : items) {
            UpdateStrategy strategy = strategies.getOrDefault(item.name, defaultStrategy);
            strategy.update(item);
        }
    }
}
