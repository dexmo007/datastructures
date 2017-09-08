package my.util;

import java.util.Comparator;

/**
 * Default comparator class using Object's equals method
 * @author Henrik Drefs
 */
public class DefaultComparator<E> implements Comparator<E> {
    @Override
    public int compare(E e1, E e2) {
        if (e1 == null && e2 == null) {
            return 0;
        }
        return e1.equals(e2) ? 0 : -1;
    }
}
