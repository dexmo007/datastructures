package my.util;

import java.util.Iterator;

/**
 * interface for a collection that declares all needed methods for a collection
 * @author Henrik Drefs
 */
public interface Collection<E> extends Iterable<E> {
    /**
     * determines the size of a collection
     * @return the size of the collection
     */
    int size();

    /**
     * if a collection is empty (holds no elements)
     * @return true if empty, false if not
     */
    boolean isEmpty();

    /**
     * searches for a specified element in collection and return if an element if found
     * @param e element to search for
     * @return true if collection holds the specified element, false if not
     */
    boolean contains(E e);

    /**
     * creates an iterator through the collection
     * @return specified iterator
     */
    Iterator<E> iterator();

    /**
     * creates an iterator in reversed direction through the collection
     * @return specified iterator
     */
    Iterator<E> descendingIterator();

    /**
     * converts the collection to array
     * @return generic array holding all collection elements
     */
    E[] toArray();

    /**
     * converts the collection to array into a specified array if length fits and cast is possible
     * @param a array to copy into
     * @param <T> type of array to copy into (should be a child of type E)
     * @return array holding all elements in collection
     */
    <T> T[] toArray(T[] a);

    /**
     * adds a specified element in the collection
     * @param e element to add
     * @return true if element could be added, false if not
     */
    boolean add(E e);

    /**
     * removes a specified element from collection
     * @param e element to remove
     * @return true if element could be removed, false if not
     */
    boolean remove(E e);

    /**
     * checks if this collection holds all elements of a specified collection
     * @param c collection holding elements to check
     * @return true if all elements of the specified collection are in this collection, false if not
     */
    boolean containsAll(Collection<E> c);

    /**
     * adds all elements of the specified collection into this collection
     * @param c collection to add
     * @return true if all elements could be added, false if not
     */
    boolean addAll(Collection<E> c);

    /**
     * removes all elements of a specified collection from this collection
     * @param c collection holding all elements to be removed in this collection
     * @return ture if all elements could be removed, false if not
     */
    boolean removeAll(Collection<E> c);

    /**
     * clears the collection
     */
    void clear();

    /**
     * checks if this collection is equal to a specified object
     * @param o another object to be checked if it is equal to this one
     * @return true if specified object is equal to this collection, false if not
     */
    boolean equals(Object o);

}
