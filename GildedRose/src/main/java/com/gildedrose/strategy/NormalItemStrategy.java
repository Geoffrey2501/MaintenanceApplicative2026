package com.gildedrose.strategy;

import com.gildedrose.Item;

public class NormalItemStrategy implements UpdateStrategy {
    @Override
    public void update(Item item) {
        item.downQuality();
        item.sellIn--;
        if (item.sellIn < 0) {
            item.downQuality();
        }
    }
}

