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

    public void upQuality() {
        if (this.quality < 50) {
            this.quality = this.quality + 1;
        }
    }

    public void downQuality() {
        if (this.quality > 0) {
            this.quality = this.quality - 1;
        }
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
