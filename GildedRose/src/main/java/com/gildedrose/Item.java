package com.gildedrose;

public class Item {
    // name représente le nom de l'article
    public String name;
    // sellIn représente le nombre de jours restant pour vendre l'article
    public int sellIn;
    // quality représente la qualité de l'article
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
