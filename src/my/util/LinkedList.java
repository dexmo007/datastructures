package my.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * LinkedList storing data by pointing to next and previous elements
 * @author Henrik Drefs
 */
public class LinkedList<E> extends AbstractCollection<E> implements List<E> {

    /**
     * First node in list
     */
    private Node<E> firstNode;
    /**
     * Last node in list
     */

    private Node<E> lastNode;

    /**
     * Node of linked list storing the data and pointing to next and previous node
     * @param <E> type of list data
     */
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> previous;

        Node(E data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }

    /**
     * size of linked list
     */
    private int size;

    /**
     * Constructor for LinkedList from a comparator
     * @param c comparator instance used to compare list elements
     */
    public LinkedList(Comparator<E> c) {
        firstNode = null;
        lastNode = null;
        this.c = c;
        size = 0;
    }

    /**
     * Constructor for LinkedList using a default comparator
     */
    public LinkedList() {
        this(new DefaultComparator<E>());
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
        Node<E> newNode = new Node<E>(e);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
            newNode.previous = lastNode;
        }
        lastNode = newNode;
        size++;
        return true;
    }


    @Override
    public E get(int index) {
        checkIndex(index);
        return nodeAt(index).data;
    }

    private Node<E> nodeAt(int index) {
        if (index < size / 2) {
            Node<E> node = firstNode;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = lastNode;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
            return node;
        }
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Node<E> node = nodeAt(index);
        E old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            add(element);
            return;
        }
        checkIndex(index);
        ListIterator<E> it = listIterator(index);
        it.add(element);
    }

    @Override
    public E remove(int index) {
        ListIterator<E> it = listIterator(index);
        E old = it.next();
        it.remove();
        return old;
    }

    /**
     * unlinks a node from linked list
     * previous points to next and next to previous,
     * so the specified node is skipped, the specified node is then set to null
     * @param node node to unlink from list
     */
    private void unlink(Node<E> node) {
        // assert node != null;
        final Node<E> next = node.next;
        final Node<E> previous = node.previous;

        if (previous == null) {
            firstNode = next;
        } else {
            previous.next = next;
            node.previous = null;
        }
        if (next == null) {
            lastNode = previous;
        } else {
            next.previous = previous;
            node.next = null;
        }
        node.data = null;
        size--;
    }

    @Override
    public int indexOf(E e) {
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (c.compare(it.next(), e) == 0) {
                return it.nextIndex() - 1;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        Iterator<E> it = descendingIterator();
        int i = size - 1;
        while (it.hasNext()) {
            if (c.compare(it.next(), e) == 0) {
                return i;
            }
            i--;
        }
        return -1;
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
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        return new ListItr(index);
    }

    /**
     * List Iterator implementation
     */
    private class ListItr implements ListIterator<E> {

        Node<E> next;
        Node<E> lastReturned;
        int nextIndex;

        private ListItr() {
            next = firstNode;
            nextIndex = 0;
        }

        private ListItr(int index) {
            next = (index == size) ? null : nodeAt(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.data;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastReturned = next = (next == null) ? lastNode : next.previous;
            nextIndex--;
            return lastReturned.data;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("Cannot remove before first next() method call");
            }
            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
        }

        @Override
        public void set(E e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.data = e;
        }

        @Override
        public void add(E e) {
            Node<E> newNode = new Node<>(e);
            if (nextIndex == 0) {
                firstNode = newNode;
                newNode.next = next;
                next.previous = newNode;
            } else if (nextIndex == size) {
                LinkedList.this.add(e);
                return;
            } else {
                next.previous.next = newNode;
                newNode.previous = next.previous;
                newNode.next = next;
                next.previous = newNode;
            }
            size++;
        }
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex - 1);
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        List<E> list = new LinkedList<E>();
        int index = 0;
        for (E e : this) {
            if (index >= fromIndex) {
                list.add(e);
            }
            index++;
            if (index == toIndex) {
                return list;
            }
        }
        return null;
    }
}
