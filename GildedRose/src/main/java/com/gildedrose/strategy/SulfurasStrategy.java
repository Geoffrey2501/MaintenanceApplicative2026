package com.gildedrose.strategy;

import com.gildedrose.Item;

public class SulfurasStrategy implements UpdateStrategy {
    @Override
    public void update(Item item) {
        // Sulfuras ne change jamais : ni sellIn, ni quality
    }
}

