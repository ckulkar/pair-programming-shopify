package com.gildedrose;

public enum Constants {
    BRIE("Aged Brie"),
    PASS("Backstage passes to a TAFKAL80ETC concert"),
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    CONJURED("Conjured");
    
    private String name;
    
    Constants(String n) {
        name = n;
    }
    
    public String getName() {
        return name;
    }
}
