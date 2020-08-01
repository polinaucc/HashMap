package ua.polina;

import org.junit.*;

import static org.junit.Assert.*;

public class HashMapImplTest {

    HashMap myHashMap = new HashMapImpl(5);


    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeCapacity() {
       HashMap hashMap = new HashMapImpl(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithWrongLoadFactor() {
        HashMap hashMap = new HashMapImpl(10, 5);
    }

    @Test
    public void put() {
        assertNull(myHashMap.put(1, 100));
        assertNull(myHashMap.put(2, 50));
    }

    @Test
    public void putAgain() {
        myHashMap.put(1, 100);
        assertEquals(myHashMap.put(1, 15), Long.valueOf(100L));
    }

    @Test
    public void get() {
        myHashMap.put(7, 1800);
        myHashMap.put(2, 70);
        Long actual = myHashMap.get(2);
        Long expected = 70L;
        assertEquals(expected, actual);
    }

    @Test
    public void sizeAfterAddingDifferentElements() {
        myHashMap.put(7, 1800);
        myHashMap.put(2, 70);
        assertEquals(2, myHashMap.size());
    }

    @Test
    public void sizeAfterAddingElementsWithSameKeys() {
        myHashMap.put(7, 1800);
        myHashMap.put(7, 70);
        assertEquals(1, myHashMap.size());
    }

    @Test
    public void sizeAfterIncreasingCapacity() {
        myHashMap.put(7, 1800);
        myHashMap.put(1, 70);
        myHashMap.put(-1, 70);
        myHashMap.put(10, 70);
        myHashMap.put(3, 75);
        myHashMap.put(9, 175);
        assertEquals(6, myHashMap.size());
    }
}
