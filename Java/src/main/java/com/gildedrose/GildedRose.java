package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    /*
    - All items have a SellIn value which denotes the number of days we have to sell the item
    - All items have a Quality value which denotes how valuable the item is
    - At the end of each day our system lowers both values for every item
    
    Pretty simple, right? Well this is where it gets interesting:

    - Once the sell by date has passed, Quality degrades twice as fast
    - The Quality of an item is never negative
    - "Aged Brie" actually increases in Quality the older it gets
    - The Quality of an item is never more than 50
    - "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    Quality drops to 0 after the concert
    
    We have recently signed a supplier of conjured items. This requires an update to our system:

    - "Conjured" items degrade in Quality twice as fast as normal items
     */
    
    /*
     * TODO
     * 1. Stop decrementing sellIn at 0
     * 2. 
     */
    
    public void updateQuality() {
        if (validItems()) {
            for (Item item : items) {
                switch(item.name) {
                case "Aged Brie":
                    if (item.sellIn >= 0 && item.quality < 50) {
                        item.quality++;
                    } else if (item.sellIn < 0) {
                        if (item.quality <= 48) {
                            item.quality = item.quality + 2;
                        } else if (item.quality == 49) {
                            item.quality++;
                        }
                    }
                    item.sellIn--;
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    //Quality increases by 2 when there are 10 days or
                    //less and by 3 when there are 5 days or less but
                    //Quality drops to 0 after the concert
                    item.sellIn--;
                    break;
                case "Sulfuras, Hand of Ragnaros":
                    break;
                default:
                    item.sellIn--;
                    break;
                }
            }
        }
    }
    
    private boolean validItems() {
        if (items != null && items.length > 0) {
            for (Item item : items) {
                if (!validItem(item)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean validItem(Item item) {
        //TODO rule for sellIn - if needed
        return (item != null && 
                item.name != null && item.name.length() > 0 &&
                item.quality >= 0);
    }
    
    
    public void updateQuality1() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}