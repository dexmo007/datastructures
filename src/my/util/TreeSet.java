package my.util;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Binary Search Tree
 *
 * @author Henrik Drefs
 */
public class TreeSet<E> extends AbstractCollection<E> implements Set<E> {

    private Node<E> root;

    private int size;

    private class Node<E> {
        E data;
        Node<E> leftChild;
        Node<E> rightChild;

        Node(E data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

//    public TreeSet() {
//        assert E extends Comparable<? super E>
//        this(new DefaultTreeComparator<E>());
//    }

    public TreeSet(E root) {
        //assert E extends Comparable<? super E>
        this(root, new DefaultTreeComparator<E>());
    }

    public <T extends Comparable<? super E>> TreeSet() {
//        this.c = new ComparableComparator<T>();
//        this.c = new DefaultTreeComparator<>();
//        this(new ComparableComparator<E>());
    }


    public TreeSet(Comparator<? super E> c) {
        this.c = c;
        this.size = 0;
        this.root = null;
    }

    public TreeSet(E root, Comparator<E> c) {
        this.c = c;
        this.size = 1;
        this.root = new Node<>(root);
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
        if (root == null) {
            root = newNode;
            return true;
        } else {
            Node<E> focus = root;
            while (true) {
                int compare = c.compare(e, focus.data);
                if (compare < 0) { //element is smaller than focus
                    focus = focus.leftChild;
                    if (focus == null) {
                        focus.leftChild = newNode;
                        return true;
                    }
                } else if (compare > 0) { // element is bigger that focus
                    focus = focus.rightChild;
                    if (focus == null) {
                        focus.rightChild = newNode;
                        return true;
                    }
                } else { // set already contains the element
                    return false;
                }
            }
        }
    }

    @Override
    public boolean remove(E e) {
        return super.remove(e);
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
