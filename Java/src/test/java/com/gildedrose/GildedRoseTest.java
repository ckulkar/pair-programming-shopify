package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void brieQualityUpdate() {
        Item[] items1 = new Item[] { new Item(Constants.BRIE.getName(), 1, 0) };
        GildedRose app = new GildedRose(items1);
        
        //test quality update within range
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        
        //test quality update over fifty
        app.items[0].quality = 50;
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        
        //test quality after sellIn date
        app.items[0].sellIn = 1;
        app.items[0].quality = 0;
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
        app.items[0].sellIn = -1;
        app.items[0].quality = 10;
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
        assertEquals(-2, app.items[0].sellIn);
    }
    
    /*
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    Quality drops to 0 after the concert
     */
    @Test
    void passesQualityUpdate() {
        //Item - name, sellIn, quality
        Item[] items1 = new Item[] { new Item(Constants.PASS.getName(), 9, 0) };
        GildedRose app = new GildedRose(items1);
        
        //test quality update within 10 days
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
        
        //test quality update within 5 days
        app.items[0].sellIn = 4;
        app.items[0].quality = 0;
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
        
        //test quality after sellIn
        app.items[0].sellIn = 0;
        app.items[0].quality = 10;
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        
        //test quality update over fifty
        app.items[0].quality = 50;
        app.updateQuality();
       // assertEquals(50, app.items[0].quality);
    }
    
    //@Test
    void sulfurasNoOpUpdate() {
        //Item - name, sellIn, quality
        Item[] items1 = new Item[] { new Item(Constants.SULFURAS.getName(), 100, 1000) };
        GildedRose app = new GildedRose(items1);
        
        //test quality updat
        app.updateQuality();
        assertEquals(100, app.items[0].sellIn);
        assertEquals(1000, app.items[0].quality);
    }
    
    @Test
    void itemQualityUpdate() {
        //Item - name, sellIn, quality
        Item[] items1 = new Item[] { new Item("CHEESE", 10, 20) };
        GildedRose app = new GildedRose(items1);
        
        //test quality update within sellIn
        app.updateQuality();
        assertEquals(19, app.items[0].quality);
        
        //test quality update after sellIn
        app.items[0].sellIn = 0;
        app.items[0].quality = 10;
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
        
        //test non-negative quality
        app.items[0].sellIn = 0;
        app.items[0].quality = 1;
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        
    }
    
    void conjuredQualityUpdate() {
        //Item - name, sellIn, quality
        Item[] items1 = new Item[] { new Item(Constants.CONJURED.getName(), 100, 1000) };
        GildedRose app = new GildedRose(items1);
        
        //test quality updat
        app.updateQuality();
        assertEquals(100, app.items[0].sellIn);
        assertEquals(1000, app.items[0].quality);
    }
}
