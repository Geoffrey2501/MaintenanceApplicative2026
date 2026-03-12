package com.gildedrose.strategy;

import com.gildedrose.Item;

public class AgedBrieStrategy implements UpdateStrategy {
    @Override
    public void update(Item item) {
        item.upQuality();
        item.sellIn--;
        if (item.sellIn < 0) {
            item.upQuality();
        }
    }
}

