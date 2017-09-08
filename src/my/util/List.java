package my.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * List interface with all basic list methods and few default methods
 * @author Henrik Drefs
 */
public interface List<E> extends Collection<E> {

    /**
     * sorts the list using quick sort algorithm ({@code Rray.quickSort(Comparator c)})
     * @param c comparator on which the sorting is based
     */
    default void sort(Comparator<? extends E> c) {
        E[] a = this.toArray();
        Rray.quickSort(a, (Comparator) c);
        ListIterator<E> it = this.listIterator();
        for (E e : a) {
            it.next();
            it.set(e);
        }
    }

    /**
     * checks if index is within bounds of list
     * @param index index to check
     * @throws IndexOutOfBoundsException if indexes are out of bounds
     */
    default void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Illegal Index: " + index + " (Size: " + size() + ")");
        }
    }

    /**
     * Overrides the equals method from {@code java.lang.Object}
     * @param o object to compare
     * @return if objects are equal
     */
    @Override
    boolean equals(Object o);

    /**
     * gets a list element based on its index
     * @param index index to get
     * @return element with certain index
     */
    E get(int index);

    /**
     * sets an element with certain index
     * @param index index to set
     * @param element new element
     * @return old element
     */
    E set(int index, E element);

    /**
     * adds an element to list in a specified index and shift rest of list up one index
     * @param index new index to add in
     * @param element element to add
     */
    void add(int index, E element);

    /**
     * removes a specified index from list
     * @param index index to remove
     * @return removed element
     */
    E remove(int index);

    /**
     * searches for the first index of a specified element
     * @param e element to search for
     * @return first index of specified element
     */
    int indexOf(E e);

    /**
     * searches for the last index of a specified element
     * @param e element to search for
     * @return last index of specified element
     */
    int lastIndexOf(E e);

    /**
     * return a list iterator
     * @return list iterator
     */
    ListIterator<E> listIterator();

    /**
     * returns a list iterator that starts at a certain index
     * @param index iterator start index
     * @return the specified list iterator
     */
    ListIterator<E> listIterator(int index);

    @Override
    default Iterator<E> descendingIterator() {
        return new Iterator<E>() {

            ListIterator<E> it = listIterator(size());

            @Override
            public boolean hasNext() {
                return it.hasPrevious();
            }

            @Override
            public E next() {
                return it.previous();
            }

            @Override
            public void remove() {
                it.remove();
            }
        };
    }

    /**
     * creates a new list from a specified index to another specified index
     * @param fromIndex inclusive index in actual list that holds the first element of sublist
     * @param toIndex exclusive (this index - 1 in actual list holds the last element of sublist)
     * @return specified sub list
     */
    List<E> subList(int fromIndex, int toIndex);
}
