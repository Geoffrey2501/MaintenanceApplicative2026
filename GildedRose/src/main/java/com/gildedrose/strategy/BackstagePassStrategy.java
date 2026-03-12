package com.gildedrose.strategy;

import com.gildedrose.Item;

public class BackstagePassStrategy implements UpdateStrategy {
    @Override
    public void update(Item item) {
        item.upQuality();
        if (item.sellIn < 11) {
            item.upQuality();
        }
        if (item.sellIn < 6) {
            item.upQuality();
        }
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }
}

