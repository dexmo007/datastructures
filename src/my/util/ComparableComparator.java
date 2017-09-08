package my.util;

import java.util.Comparator;

/**
 * Created by Henrik on 12/7/2015.
 */
public class ComparableComparator<T extends Comparable<? super T>> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
}
