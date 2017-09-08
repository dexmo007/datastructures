package my.util;

import java.util.Comparator;

/**
 * Abstract hash set implementation using Object's hashCode as hashValue
 * @author Henrik Drefs
 */
public class HashSet<E> extends AbstractHashSet<E> {

    public HashSet() {
        this(new DefaultComparator<E>(), DEFAULT_MAX_LOAD_FACTOR, DEFAULT_INITIAL_CAPACITY);
    }

    public HashSet(Comparator<E> c) {
        this(c, DEFAULT_MAX_LOAD_FACTOR, DEFAULT_INITIAL_CAPACITY);
    }

    public HashSet(double maxLoadFactor) {
        this(new DefaultComparator<E>(), maxLoadFactor, DEFAULT_INITIAL_CAPACITY);
    }

    public HashSet(int initialCapacity) {
        this(new DefaultComparator<E>(), DEFAULT_MAX_LOAD_FACTOR, initialCapacity);
    }

    public HashSet(Comparator<E> c, double maxLoadFactor) {
        this(c, maxLoadFactor, DEFAULT_INITIAL_CAPACITY);
    }

    public HashSet(Comparator<E> c, int initialCapacity) {
        this(c, DEFAULT_MAX_LOAD_FACTOR, initialCapacity);
    }

    public HashSet(Comparator<E> c, double maxLoadFactor, int initialCapacity) {
        if (maxLoadFactor < 0 || initialCapacity < 0) {
            throw new IllegalArgumentException("MaxLoadFactor and Initial Capacity must not be negative");
        }
        if (initialCapacity == 0) {
            throw new IllegalArgumentException("Initial Capacity cannot be 0");
        }
        this.c = c;
        this.maxLoadFactor = maxLoadFactor;
        this.arr = new LinkedList[initialCapacity];
        this.arrSize = arr.length;
        for (int i = 0; i < arrSize; i++) {
            arr[i] = new LinkedList<>(c);
        }
        this.size = 0;
    }

    @Override
    public int hashValue(E e) {
        return e.hashCode();
    }

}
