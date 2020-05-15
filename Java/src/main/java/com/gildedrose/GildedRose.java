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
                    if (item.sellIn >= 0) {
                    	//before sellIn, quality increases with age, never goes beyond 50
                    	item.quality++;
                    	ensureBoundedQuality(item);
                    } else {
                    	//after the sell-in, quality increases twice as fast, never beyond 50
                    	item.quality = item.quality + 2;
                    	ensureBoundedQuality(item);
                    }
                    item.sellIn--;
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    if (item.sellIn <= 0) {
                		//quality drops to 0 after the concert
                		item.quality = 0;
                	} else if (item.sellIn <= 5) {
                		//within 5 days of sellIn, quality increases by 3
                		item.quality = item.quality + 3;
                		ensureBoundedQuality(item);
                	} else if (item.sellIn <= 10) {
                		//between 6 to 10 days of sellIn, quality increases by 2
                		item.quality = item.quality + 2;
                		ensureBoundedQuality(item);
                	}
                    item.sellIn--;
                    break;
                case "Sulfuras, Hand of Ragnaros":
                	//nothing changes for Sulfuras
                    break;
                default:
                	if (item.sellIn > 0) {
                		//quality decreases with age
                		item.quality--;
                		ensureBoundedQuality(item);
                	} else {
                		//quality decreases twice as fast after sellIn
                		item.quality = item.quality - 2;
                		ensureBoundedQuality(item);
                	}
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
        return (item != null && 
                item.name != null && item.name.length() > 0);
    }

    /**
     * Ensure that the quality for an item is never over 50 and below 0
     * @param item for which the quality value needs to be evaluated
     */
    private void ensureBoundedQuality(Item item) {
    	if (validItem(item)) {
			if (item.quality > 50) {
				item.quality = 50;
			} else if (item.quality < 0) {
				item.quality = 0;
			}
    	}
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