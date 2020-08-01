package ua.polina;

/**
 * The type Hash map for int keys and long values. It provides get,
 * put and size operations. Null values as a key are not allowed.
 *
 * @author Polina Serhiienko
 */
public class HashMapImpl implements HashMap {
    /**
     * The default load factor. It is used when it isn't specified in constructor.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * It holds pairs of key-value elements.
     */
    private Item[] items;

    /**
     * The number of key-value elements.
     */
    private int size;

    /**
     * The load factor for the hashtable.
     */
    private float loadFactor;

    /**
     * Max quantity of elements to store.
     */
    private int limitQuantity;

    /**
     * Instantiates a new Hash map with default capacity - 16.
     */
    public HashMapImpl() {
        items = new Item[16];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        changeLimitQuantity();
    }

    /**
     * Instantiates a new Hash map with capacity.
     * Load factor is used by default.
     *
     * @param capacity the capacity
     */
    public HashMapImpl(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Instantiates a new Hash map with capacity and load factor.
     *
     * @param capacity   the capacity
     * @param loadFactor the load factor
     */
    public HashMapImpl(int capacity, float loadFactor) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity must be a positive number");
        if (loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException(" Load factor should be between 0 and 1");
        items = new Item[capacity];
        changeLimitQuantity();
        this.loadFactor = loadFactor;
    }

    /**
     * Gets element by the key.
     *
     * @param key the key of element
     * @throws NoSuchElementException when element doesn't exist
     * @return the value of element
     */
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

    /**
     * Puts element to items. If elements with such key already exists,
     * item should be rewritten.
     *
     * @param key the key of item
     * @param value the value of item
     * @return the previous value of item
     */
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

    /**
     * Gets the number of key-value pairs.
     *
     * @return  the size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Calculates index of array to write element to.
     *
     * @param key the key of element
     * @param step  i parameter of function: h_i = (h_i + i ) % capacity
     * @return index for element
     */
    private int getIndex(int key, int step) {
        int tableSize = items.length;
        int hash = (key % tableSize) + step;
        return Math.abs(hash % tableSize);
    }

    /**
     * Checks if table is overloaded.
     */
    private void isFilled() {
        if (size > limitQuantity) {
            Item[] old = items;
            items = new Item[items.length * 2];
            changeLimitQuantity();
            rewriteHashMap(old);
        }
    }

    /**
     * Recalculates table when it is overloaded.
     *
     * @param old items that were in old (smaller) table
     */
    private void rewriteHashMap(Item[] old) {
        for (Item element : old) {
            if (element != null) {
                put(element.getKey(), element.getValue());
            }
        }
    }

    /**
     * Changes element quantity in items
     */
    private void changeLimitQuantity() {
        size = 0;
        limitQuantity = Math.round(items.length * loadFactor);
    }
}
