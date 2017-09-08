package my.util;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * ArrayList storing data in an array, length increased if needed when trying to add
 * @author Henrik Drefs
 */
public class ArrayList<E> extends AbstractCollection<E> implements List<E> {
    /**
     * array that stores the element data
     */
    private E[] arr;
    /**
     * represents the number of elements in ArrayList
     */
    private int size;
    /**
     * default initial length of array
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 0;

    /**
     * Constructor for ArrayList from element, Comparator and initial array capacity
     * @param e instance of ArrayList element, needed to initialize generic Array
     * @param c comparator to compare list elements
     * @param initialCapacity initial array length
     */
    public ArrayList(E e, Comparator<E> c, int initialCapacity) {
        this(e.getClass(), c, initialCapacity);
    }

    /**
     * Constructor for ArrayList from element and initial array length
     * using default comparator
     * @param e instance of ArrayList element, needed to initialize generic Array
     * @param initialCapacity initial array length
     */
    public ArrayList(E e, int initialCapacity) {
        this(e, new DefaultComparator<E>(), initialCapacity);
    }

    /**
     * Constructor for ArrayList from element and comparator
     * using default initial array length
     * @param e instance of ArrayList element, needed to initialize generic Array
     * @param c comparator to compare list elements
     */
    public ArrayList(E e, Comparator<E> c) {
        this(e, c, DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructor for ArrayList from list element only
     * using default initial array length and default comparator
     * @param e instance of ArrayList element, needed to initialize generic Array
     */
    public ArrayList(E e) {
        this(e, new DefaultComparator<E>(), DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructor for ArrayList from type of list element, comparator and initial array length
     * @param elementType type of list elements, needed to initialize generic array
     * @param c comparator instance to compare list elements
     * @param initialCapacity initial array length
     */
    public ArrayList(Class<?> elementType, Comparator<E> c, int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        arr = (E[]) Array.newInstance(elementType, initialCapacity);
        size = 0;
        this.c = c;
    }

    /**
     * Constructor for ArrayList from component only
     * using default initial array length and default comparator
     * @param elementType instance of ArrayList element, needed to initialize generic Array
     */
    public ArrayList(Class<?> elementType) {
        this(elementType, new DefaultComparator<E>(), DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructor for ArrayList from element and comparator
     * using default initial array length
     * @param elementType instance of ArrayList element, needed to initialize generic Array
     * @param c comparator to compare list elements
     */
    public ArrayList(Class<?> elementType, Comparator<E> c) {
        this(elementType, c, DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructor for ArrayList from element and initial array length
     * using default comparator
     * @param elementType instance of ArrayList element, needed to initialize generic Array
     * @param initialCapacity initial array length
     */
    public ArrayList(Class<?> elementType, int initialCapacity) {
        this(elementType, new DefaultComparator<E>(), initialCapacity);
    }

    /**
     * protected Constructor for ArrayList from element data array,
     * using default comparator,
     * for public access use {@code Rray.asList(E[] arr)}
     * @param arr
     */
    protected ArrayList(E[] arr) {
        if (arr == null) {
            throw new NullPointerException();
        }
        this.arr = arr;
        size = arr.length;
        this.c = new DefaultComparator<>();
    }

    /**
     * default constructor hidden
     * element type or element sample needed for initialization
     */
    private ArrayList() {
        //unused
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
    public boolean contains(E e) {
        return indexOf(e) > -1;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        arr[size++] = e;
        return true;
    }

    /**
     * ensures the length of array is high enough to add an element, if not it doubles the array length
     */
    private void ensureCapacity() {
        if (size >= arr.length) {
            int newCapacity = newCapacity();
            if (arr.length == newCapacity) {
                throw new OutOfMemoryError();
            }
            arr = Rray.copyOf(arr, newCapacity);
        }
    }

    /**
     * maximum ArrayList capacity
     */
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    /**
     * calculates the new capacity, if old capacity is 0, new will be 1, else the old capacity is doubled,
     * limited by maximum capacity
     * @return new array length
     */
    private int newCapacity() {
        int oldCapacity = arr.length;
        int newCapacity = oldCapacity == 0 ? 1 : 2 * oldCapacity;
        return newCapacity > MAX_CAPACITY ? MAX_CAPACITY : newCapacity;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return arr[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E old = arr[index];
        arr[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            add(element);
            return;
        }
        checkIndex(index);
        ensureCapacity();
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(arr, index, arr, index + 1, numMoved);
        }
        arr[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E old = arr[index];

        int numMoved = size - index - 1;

        if (numMoved > 0) {
            System.arraycopy(arr, index + 1, arr, index, numMoved);
        }
        arr[--size] = null;//let GC do its work
        return old;
    }

    @Override
    public boolean remove(E e) {
        int index = indexOf(e);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;


    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (c.compare(arr[i], e) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        boolean removed = false;
        for (int i = 0; i < arr.length; i++) {
            if (c.contains(arr[i])) {
                remove(i);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public int lastIndexOf(E e) {
        for (int i = size - 1; i > 0; i--) {
            if (c.compare(arr[i], e) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E[] toArray() {
        return Rray.copyOf(arr, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size || a.length > size) {
            return (T[]) Rray.copyOf(arr, size, a.getClass());
        }
        System.arraycopy(arr, 0, a, 0, size);
        return a;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex - 1);
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        int subSize = toIndex - fromIndex;
        E first = this.get(fromIndex);
        List<E> sub = new ArrayList<E>(first, subSize);
        sub.add(first);
        for (int i = fromIndex + 1; i < toIndex; i++) {
            sub.add(arr[i]);
        }
        return sub;
    }

    /**
     * Private List Iterator implementation
     * @author Henrik Drefs
     */
    private class ListItr implements ListIterator<E> {

        E current;

        int index;

        private ListItr(int index) {
            checkIndex(index);
            current = get(index);
            this.index = index;
        }

        private ListItr() {
            current = get(0);
            this.index = 0;
        }

        @Override

        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = get(index++);
            return current;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = get(--index);
            return current;
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            ArrayList.this.remove(index);
            if (hasPrevious()) {
                current = previous();
//                index--;
            }
        }

        @Override
        public void set(E e) {
            ArrayList.this.set(index, e);
        }

        @Override
        public void add(E e) {
            ArrayList.this.add(index, e);
        }
    }
}
