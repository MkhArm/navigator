package Test;

import java.util.*;
import DataStructure.MyLinkedList;

public class TestList {
    public static void main(String[] args) {
        List<Integer> linkedList = new MyLinkedList<Integer>();

        // 1. size()
        System.out.println("1. Size: " + linkedList.size());

        // 2. isEmpty()
        System.out.println("2. Is Empty: " + linkedList.isEmpty());

        // 3. add(T t)
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        // 4. contains(Object o)
        System.out.println("4. Contains 2: " + linkedList.contains(2));

        // 5. iterator()
        System.out.print("5. Iterator: ");
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        // 6. toArray()
        Object[] array = linkedList.toArray();
        System.out.println("6. To Array: " + Arrays.toString(array));

        // 7. toArray(T[] a)
        Integer[] intArray = new Integer[3];
        Integer[] resultArray = linkedList.toArray(intArray);
        System.out.println("7. To Array (with specified array): " + Arrays.toString(resultArray));

        // 8. remove(Object o)
        linkedList.remove(2);
        System.out.println("8. Remove 2: " + linkedList);

        // 9. containsAll(Collection<?> c)
        List<Integer> sublist = Arrays.asList(1, 3);
        System.out.println("9. Contains All [1, 3]: " + linkedList.containsAll(sublist));

        // 10. addAll(Collection<? extends T> c)
        linkedList.addAll(sublist);
        System.out.println("10. Add All [1, 3]: " + linkedList);

        // 11. addAll(int index, Collection<? extends T> c)
        linkedList.addAll(2, Arrays.asList(4, 5));
        System.out.println("11. Add All at index 2 [4, 5]: " + linkedList);

        // 12. removeAll(Collection<?> c)
        linkedList.removeAll(Arrays.asList(3, 4));
        System.out.println("12. Remove All [3, 4]: " + linkedList);

        // 13. retainAll(Collection<?> c)
        linkedList.retainAll(Arrays.asList(1, 5));
        System.out.println("13. Retain All [1, 5]: " + linkedList);

        // 14. clear()
        linkedList.clear();
        System.out.println("14. Clear: " + linkedList);

        // 15. add(int index, T element)
        linkedList.add(0, 10);
        linkedList.add(1, 20);
        linkedList.add(2, 30);
        System.out.println("15. Add at index: " + linkedList);

        // 16. get(int index)
        System.out.println("16. Get at index 1: " + linkedList.get(1));

        // 17. set(int index, T element)
        linkedList.set(1, 25);
        System.out.println("17. Set at index 1 to 25: " + linkedList);

        // 18. remove(int index)
        linkedList.remove(1);
        System.out.println("18. Remove at index 1: " + linkedList);

        // 19. indexOf(Object o)
        System.out.println("19. Index of 30: " + linkedList.indexOf(30));

        // 20. lastIndexOf(Object o)
        linkedList.add(40);
        linkedList.add(30);
        System.out.println("20. Last Index of 30: " + linkedList.lastIndexOf(30));

        // 21. listIterator()
        ListIterator<Integer> listIterator = linkedList.listIterator();
        System.out.print("21. List Iterator (Forward): ");
        while (listIterator.hasNext()) {
            System.out.print(listIterator.next() + " ");
        }
        System.out.println();

        // 22. listIterator(int index)
        listIterator = linkedList.listIterator(2);
        System.out.print("22. List Iterator starting from index 2: ");
        while (listIterator.hasNext()) {
            System.out.print(listIterator.next() + " ");
        }
        System.out.println();

        // 23. subList(int fromIndex, int toIndex)
        List<Integer> subList = linkedList.subList(1, 3);
        System.out.println("23. Sublist [1, 3]: " + subList);

    }
}
