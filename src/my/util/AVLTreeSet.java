package my.util;

import java.util.Iterator;

/**
 * AVL Search Tree
 */
public class AVLTreeSet<E> extends TreeSet<E> implements Set<E> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }
}
