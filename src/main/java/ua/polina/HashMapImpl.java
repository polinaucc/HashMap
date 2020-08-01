package ua.polina;

public class HashMapImpl implements HashMap {
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Item[] items;
    private int size;
    private float loadFactor;
    private int limitQuantity;

    public HashMapImpl() {
        items = new Item[16];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        changeLimitQuantity();
    }

    public HashMapImpl(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMapImpl(int capacity, float loadFactor) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity must be a positive number");
        if (loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException(" Load factor should be between 0 and 1");
        items = new Item[capacity];
        changeLimitQuantity();
        this.loadFactor = loadFactor;
    }

    @Override
    public Long get(int key) {
        int step = 0;
        int index = getIndex(key, step);
        while (true) {
            if (items[index] == null) throw new NoSuchElementException("No such element");
            else if (items[index].getKey() == key) {
                return items[index].getValue();
            } else {
                step++;
                index = getIndex(key, step);
                if (index >= items.length) index = 0;
            }
        }
    }

    @Override
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

    @Override
    public int size() {
        return size;
    }

    private int getIndex(int key, int step) {
        int tableSize = items.length;
        int hash = (key % tableSize) + step;
        return Math.abs(hash % tableSize);
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
