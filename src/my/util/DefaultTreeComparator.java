package my.util;

import java.util.Comparator;

/**
 * Created by Henrik on 12/17/2015.
 */
public class DefaultTreeComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        //assert T extends Comparable<? super T>
        Comparable<? super T> com = null;
        try {
            com = ((Comparable<? super T>) o1);
        } catch (Exception e) {
            throw new TreeSetElementsNotComparableException();
        }
        return com.compareTo(o2);
    }
}
