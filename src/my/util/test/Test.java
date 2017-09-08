import my.util.*;
import my.util.HashSet;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henrik on 11/28/2015.
 */
public class Test {
    <T extends Comparable<? super T>> void test(T t) {
        Comparator<? super T> c = new ComparableComparator<T>();
    }
    public static void main(String[] args) {

    }
}
