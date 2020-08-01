package ua.polina;

public class HashMapImpl implements HashMap {
    private Item[] items;
    private int size;
    private float loadFactor = 0.75f;
    private int limitQuantity;

    public HashMapImpl(int capacity) {
        items = new Item[capacity];
        changeLimitQuantity();
    }

    public HashMapImpl(int capacity, float loadFactor) {
        items = new Item[capacity];
        this.loadFactor = loadFactor;
        changeLimitQuantity();
    }

    public Long get(int key) {
        int step = 0;
        int index = getIndex(key, step);
        while (true) {
            if (items[index] == null) return null;
            else if (items[index].getKey() == key) {
                return items[index].getValue();
            } else {
                step++;
                index = getIndex(key, step);
                if (index >= items.length) index = 0;
            }
        }
    }

    public Long put(int key, long value) {
        int step = 0;
        int index = getIndex(key, step);
        while (true) {
            if (items[index] == null) {
                items[index] = new Item(key, value);
                size++;
                isFilled();
                return null;
            } else if (items[index].getKey() == key) {
                long oldValue = items[index].getValue();
                items[index] = new Item(key, value);
                return oldValue;
            } else {
                step++;
                index = getIndex(key, step);
                if (index >= items.length) index = 0;
            }
        }
    }

    private int getIndex(int key, int step) {
        int tableSize = items.length;
        int hash = (key % tableSize) + step;
        return Math.abs(hash % tableSize);
    }

    public int size() {
        return size;
    }

    private void isFilled() {
        if (size > limitQuantity) {
            Item[] old = items;
            items = new Item[items.length * 2];
            changeLimitQuantity();
            rewriteHashMap(old);
        }
    }

    private void rewriteHashMap(Item[] old) {
        for (Item element : old) {
            if (element != null) {
                put(element.getKey(), element.getValue());
            }
        }
    }

    private void changeLimitQuantity() {
        size = 0;
        limitQuantity = Math.round(items.length * loadFactor);
    }

}
