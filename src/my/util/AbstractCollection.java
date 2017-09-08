package my.util;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;

/**
 * abstract collection class that implements a few default methods
 * @author Henrik Drefs
 */
public abstract class AbstractCollection<E> implements Collection<E> {
    /**
     * comparator instance used to compare collection elements
     */
    protected Comparator<? super E> c;

    @Override
    public boolean contains(E e) {
        Iterator<E> it = iterator();
        if (e == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (c.compare(e, it.next()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public E[] toArray() {
        Class<?> type = getComponentType();
        if (type == null) {
            return (E[]) new Object[0];
        }
        E[] arr = ((E[]) Array.newInstance(type, size()));
        Iterator<E> it = iterator();
        for (int i = 0; i < arr.length; i++) {
            if (!it.hasNext()) {
                throw new InternalError("Internal Error: size / iterator mismatch");
            }
            arr[i] = it.next();
        }
        return arr;
    }

    /**
     * gets the component type of this collection
     * @return component type of this collection
     */
    private Class<?> getComponentType() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            return null;
        }
        return it.next().getClass();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }
        if (size() == 0) {
            return (T[]) Array.newInstance(a.getClass().getComponentType(), 0);
        }
        T[] arr;
        if (size() == a.length) {
            arr = a;
        } else {
            arr = ((T[]) Array.newInstance(a.getClass().getComponentType(), size()));
        }
        Iterator<E> it = iterator();
        for (int i = 0; i < arr.length; i++) {
            if (!it.hasNext()) {
                throw new InternalError("Internal Error: size / iterator mismatch");
            }
            T t = ((T) it.next());
            arr[i] = t;
        }
        return arr;

    }

    @Override
    public boolean remove(E e) {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (c.compare(e, it.next()) == 0) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<E> c) {
        for (E element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<E> c) {
        boolean added = false;
        for (E e : c) {
            if (add(e)) {
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        boolean removed = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractCollection)) {
            return false;
        }
        AbstractCollection<?> c = (AbstractCollection<?>) obj;
        if (c.size() != size()) {
            return false;
        }
        if (c.getComponentType() != getComponentType()) {
            return false;
        }
        AbstractCollection<E> c1 = ((AbstractCollection<E>) c);
        return containsAll(c1);
    }
}
