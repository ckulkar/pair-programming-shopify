package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    
    /**
     * Update quality for inventory
     */
    public void updateQuality() {
        if (validItems()) {
            for (Item item : items) {
                switch(item.name) {
                case Constants.BRIE:
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
                case Constants.PASS:
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
                case Constants.SULFURAS:
                	//nothing changes for Sulfuras
                    break;
                case Constants.CONJURED:
                	//these degrade in quality twice as fast as normal items
                	if (item.sellIn > 0) {
                		//quality decreases with age, twice as fast
                		item.quality = item.quality - 2;
                		ensureBoundedQuality(item);
                	} else {
                		//quality decreases after sellIn, twice as fast
                		item.quality = item.quality - 4;
                		ensureBoundedQuality(item);
                	}
                    item.sellIn--;
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
    
    /**
     * Ensure that all the items have all non-null, non-zero fields
     * @return true if the items are valid, false otherwise
     */
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
    
    /**
     * Ensure that an item has non-null, non-zero-length fields
     * @param item
     * @return true if the item is valid, false otherwise
     */
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
    
    /**
     * @deprecated for poor readability and maintainability. Use as a reference for business logic
     * and ensure that tests pass using this if needed. 
     */
    public void updateQualityDeprecated() {
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