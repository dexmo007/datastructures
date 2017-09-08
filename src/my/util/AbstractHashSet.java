package my.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * abstract Hashset implementation, hash value method is abstract
 * no duplicates in set, hashset uses chaining and rehashing
 * class HashSet uses a default hashValue(E e) method
 * @author Henrik Drefs
 */
public abstract class AbstractHashSet<E> extends AbstractCollection<E> implements Set<E>{

    protected LinkedList<E>[] arr;

    protected int arrSize;

    protected int size;

    protected double maxLoadFactor;

    protected static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    protected static final int DEFAULT_INITIAL_CAPACITY = 10;

    public AbstractHashSet() {
        this(new DefaultComparator<E>(), DEFAULT_MAX_LOAD_FACTOR, DEFAULT_INITIAL_CAPACITY);
    }

    public AbstractHashSet(Comparator<E> c) {
        this(c, DEFAULT_MAX_LOAD_FACTOR, DEFAULT_INITIAL_CAPACITY);
    }

    public AbstractHashSet(double maxLoadFactor) {
        this(new DefaultComparator<E>(), maxLoadFactor, DEFAULT_INITIAL_CAPACITY);
    }

    public AbstractHashSet(int initialCapacity) {
        this(new DefaultComparator<E>(), DEFAULT_MAX_LOAD_FACTOR, initialCapacity);
    }

    public AbstractHashSet(Comparator<E> c, double maxLoadFactor) {
        this(c, maxLoadFactor, DEFAULT_INITIAL_CAPACITY);
    }

    public AbstractHashSet(Comparator<E> c, int initialCapacity) {
        this(c, DEFAULT_MAX_LOAD_FACTOR, initialCapacity);
    }

    public AbstractHashSet(Comparator<E> c, double maxLoadFactor, int initialCapacity) {
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
        initLists();
        this.size = 0;
    }

    private void initLists() {
        for (int i = 0; i < arrSize; i++) {
            arr[i] = new LinkedList<>(c);
        }
    }

    public void setMaxLoadFactor(double maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        if (loadFactor() > maxLoadFactor) {
            rehash();
        }
        return fastAdd(e);
    }

    private boolean fastAdd(E e) {
        LinkedList<E> hashed = arr[hashIndex(e)];
        if (hashed.contains(e)) {
            return false;
        }
        hashed.add(e);
        size++;
        return true;
    }

    private void rehash() {
        LinkedList<E>[] old = arr;
        arrSize = 2 * arrSize;
        arr = new LinkedList[arrSize];
        initLists();
        for (LinkedList<E> l : old) {
            for (E e : l) {
                fastAdd(e);
            }
        }
    }

    @Override
    public boolean contains(E e) {
        return arr[hashIndex(e)].contains(e);
    }

    @Override
    public boolean remove(E e) {
        return arr[hashIndex(e)].remove(e);
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        boolean modified = false;
        for (E e : c) {
            modified = arr[hashIndex(e)].remove(e);
        }
        return modified;
    }

    private double loadFactor() {
        return arr.length == 0 ? 0 : size / arr.length;
    }

    private int hashIndex(E e) {
        return Math.abs(hashValue(e)) % arrSize;
    }

    public abstract int hashValue(E e);

    @Override
    public Iterator<E> iterator() {
        return new HashItr();
    }

    private class HashItr implements Iterator<E> {
        LinkedList<E> currentChain;
        Iterator<E> chainItr;
        E next;
        E lastReturned;
        int arrIndex;

        private HashItr() {
            arrIndex = 0;
            currentChain = arr[arrIndex++];
            chainItr = currentChain.iterator();
            findNextItr();
            next = chainItr.hasNext() ? chainItr.next() : null;
        }

        private void findNextItr() {
            while (!chainItr.hasNext() && arrIndex < arrSize) {
                currentChain = arr[arrIndex++];
                chainItr = currentChain.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            findNextItr();
            lastReturned = next;
            next = chainItr.hasNext() ? chainItr.next() : null;
            return lastReturned;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("Cannot remove before first next()-method call!");
            }
            currentChain.remove(lastReturned);
            AbstractHashSet.this.size--;
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingHashItr();
    }

    private class DescendingHashItr implements Iterator<E> {

        LinkedList<E> currentChain;
        Iterator<E> chainItr;
        E next;
        E lastReturned;
        int arrIndex;

        private DescendingHashItr() {
            arrIndex = arrSize - 1;
            currentChain = arr[arrIndex--];
            chainItr = currentChain.descendingIterator();
            findPrevItr();
            next = chainItr.hasNext() ? chainItr.next() : null;
        }

        private void findPrevItr() {
            while (!chainItr.hasNext() && arrIndex >= 0) {
                currentChain = arr[arrIndex--];
                chainItr = currentChain.descendingIterator();
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            findPrevItr();
            lastReturned = next;
            next = chainItr.hasNext() ? chainItr.next() : null;
            return lastReturned;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("Cannot remove before first next()-method call!");
            }
            currentChain.remove(lastReturned);
            AbstractHashSet.this.size--;
        }
    }
}
