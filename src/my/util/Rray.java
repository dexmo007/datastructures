package my.util;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * Small array utility - static methods to: sort, search, copy, toList
 * @author Henrik Drefs
 */
public class Rray {
    /**
     * copies an array into a new array with new length
     * @param orig array to copy
     * @param newLength new length
     * @param <T> type of array component
     * @return new array
     */
    public static <T> T[] copyOf(T[] orig, int newLength) {
        return (T[]) copyOf(orig, newLength, orig.getClass());
    }

    /**
     * copies an array into a new array with new length and specified type
     * @param orig original array
     * @param newLength new array length
     * @param newType specified type of new array
     * @param <T> type of new array
     * @param <U> type of original array
     * @return the specified array copy
     */
    public static <T, U> T[] copyOf(U[] orig, int newLength, Class<? extends T[]> newType) {
        T[] copy = (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(orig, 0, copy, 0, orig.length);
        return copy;
    }

    /**
     * converts a specified array to an ArrayList
     * @param arr arr to convert into ArrayList
     * @param <T> component type of array
     * @return specified ArrayList
     */
    public static <T> ArrayList<T> asList(T[] arr) {
        return new ArrayList<>(arr);
    }

    /**
     * sorts an generic array through the QuickSort algorithm
     * @param objects    array to be sorted
     * @param comparator comparator used to compare elements
     * @param <T>        type of compared elements
     */
    public static <T> void quickSort(T[] objects, Comparator<T> comparator) {
        if (objects == null) {
            return;
        }
        recQuickSort(objects, 0, objects.length - 1, comparator);
        return;
    }

    /**
     * recursive method for QuickSort
     * @param objects    array to be sorted
     * @param leftBound  left bound of the part to be sorted
     * @param rightBound right bound of the part to be sorted
     * @param comparator comparator to compare array elements
     * @param <T>        type of elements to be sorted
     */
    private static <T> void recQuickSort(T[] objects,
                                         int leftBound, int rightBound, Comparator<T> comparator) {
        if (rightBound <= leftBound) {
            return;
        }
        T pivot = objects[rightBound];
        int wall = leftBound;
        for (int i = leftBound; i < rightBound; i++) {
            T currentElement = objects[i];
            if (comparator.compare(currentElement, pivot) < 0) {
                // swop current element with the element next to the wall
                objects[i] = objects[wall];
                objects[wall] = currentElement;
                wall++;
            }
        }
        //swop pivot element with the element next to the wall
        objects[rightBound] = objects[wall];
        objects[wall] = pivot;

        recQuickSort(objects, leftBound, wall - 1, comparator);
        recQuickSort(objects, wall + 1, rightBound, comparator);
    }

    /**
     * sorts an array through the MergeSort algorithm
     * @param objects    array to be sorted
     * @param comparator comparator to compare elements
     * @param <T>        type of the array elements
     * @return sorted array
     */
    public static <T> T[] mergeSort(T[] objects, Comparator<T> comparator) {
        if (objects == null || objects.length == 0) {
            return null;
        }
        return recMergeSort(objects, 0, objects.length - 1, comparator);
    }

    /**
     * recursive method for MergeSort
     * @param objects    array to be sorted
     * @param leftBound  left bound of the part to be sorted
     * @param rightBound right bound of the part to be sorted
     * @param comparator comparator to compare elements
     * @param <T>        type of the elements
     * @return sorted part between bounds
     */
    private static <T> T[] recMergeSort(T[] objects, int leftBound,
                                        int rightBound, Comparator<T> comparator) {
        if (rightBound <= leftBound) {
            T[] result = (T[]) Array.newInstance(objects[0].getClass(), 1);
            result[0] = objects[leftBound];
            return result;
        }
        int middle = (leftBound + rightBound) / 2;
        T[] part1 = recMergeSort(objects, leftBound, middle, comparator);
        T[] part2 = recMergeSort(objects, middle + 1, rightBound, comparator);
        return merge(part1, part2, comparator);
    }

    /**
     * helper method for MergeSort algorithm
     * merges and sorts two sorted array into a new sorted array
     * @param part1      first array
     * @param part2      first array
     * @param comparator comparator to compare elements
     * @param <T>        type of the elements
     * @return merged, sorted array
     */
    private static <T> T[] merge(T[] part1, T[] part2, Comparator<T> comparator) {
        if (part1.length == 0) {
            return part2;
        } else if (part2.length == 0) {
            return part1;
        }
        T[] merged = (T[]) Array.newInstance(part1[0].getClass(), part1.length + part2.length);
        int i = 0;
        int j = 0;
        while (i < part1.length && j < part2.length) {
            if (comparator.compare(part1[i], part2[j]) < 0) {
                merged[i + j] = part1[i];
                i++;
            } else {
                merged[i + j] = part2[j];
                j++;
            }
        }
        while (i < part1.length) { //loop exited with j >= part2.length
            merged[i + j] = part1[i];
            i++;
        }
        while (j < part2.length) { //loop exited with i >= part1.length
            merged[i + j] = part2[j];
            j++;
        }
        return merged;
    }

    /**
     * generic method to search a sorted array for a String key
     * @param data       sorted array to search
     * @param key        String search key
     * @param comparator comparator to compare elements with key
     * @param <T>        type of elements
     * @return index of the key in data if found, else -1
     */
    public static <T> int binSearch(T[] data, T key, Comparator<T> comparator) {
        if (data == null) {
            return -1;
        }
        return recBinSearch(data, key, 0, data.length - 1, comparator);
    }

    /**
     * recursive method for binary search
     * @param data       array to search in
     * @param key        key to search for
     * @param leftBound  left bound
     * @param rightBound right bound
     * @param comparator comparator to compare elements
     * @param <T>        type of the elements
     * @return index of the element if found, else -1
     */
    private static <T> int recBinSearch(T[] data, T key,
                                        int leftBound, int rightBound, Comparator<T> comparator) {
        if (rightBound < leftBound) {
            return -1;
        }
        int middle = (leftBound + rightBound) / 2;
        if (comparator.compare(key, data[middle]) < 0) {
            return recBinSearch(data, key, leftBound, middle - 1, comparator);
        } else if (comparator.compare(key, data[middle]) > 0) {
            return recBinSearch(data, key, middle + 1, rightBound, comparator);
        } else {
            return middle;
        }
    }

    /**
     * counts entries matching a key using binary search
     * @param data       array
     * @param key        key to search for
     * @param comparator comparison basis
     * @param <T>        type of elements
     * @return number of entries matching the key
     */
    public static <T> int binCountEntries(T[] data, T key, Comparator<T> comparator) {
        int index = binFindFirst(data, key, comparator);
        if (index == -1) {
            return 0;
        }
        int number = 1;
        index++;
        while (index < data.length && comparator.compare(data[index], key) == 0) {
            number++;
            index++;
        }
        return number;
    }

    /**
     * finds first entry matching the key using binary search
     * @param data       array to search in
     * @param key        key to search for
     * @param comparator comparison basis
     * @param <T>        type of elements
     * @return first index of key if found, else -1
     */
    public static <T> int binFindFirst(T[] data, T key, Comparator<T> comparator) {
        int index = binSearch(data, key, comparator);
        while (index > 0) {
            if (comparator.compare(data[index - 1], key) != 0) {
                return index;
            }
            index--;
        }
        return index;
    }
}
