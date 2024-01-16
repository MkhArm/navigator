package Test;

import DataStructure.TwoLinkedList;
import Entity.Route;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestTwoLinkedList {
    public static void main(String[] args) {
        TwoLinkedList<Route> routeList = new TwoLinkedList<>();

        // Создаем объекты Route
        Route route1 = new Route("1", 10.5, 100, true, new TwoLinkedList<>());
        Route route2 = new Route("2", 15.2, 80, false, new TwoLinkedList<>());
        Route route3 = new Route("3", 20.0, 120, true, new TwoLinkedList<>());

        // 1. addFirst(T data)
        routeList.addFirst(route1);
        routeList.addFirst(route2);
        routeList.addFirst(route3);
        System.out.println("1. Add First: " + routeList);

        // 2. addLast(T data)
        Route route4 = new Route("4", 25.8, 90, false, new TwoLinkedList<>());
        routeList.addLast(route4);
        System.out.println("2. Add Last: " + routeList);

        // 3. insertAfter(T data, T key)
        Route route5 = new Route("5", 18.7, 110, true, new TwoLinkedList<>());
        routeList.insertAfter(route5, route3);
        System.out.println("3. Insert After: " + routeList);

        // 4. remove(T data)
        routeList.remove(route2);
        System.out.println("4. Remove: " + routeList);

        // 5. removeFirst()
        routeList.removeFirst();
        System.out.println("5. Remove First: " + routeList);

        // 6. removeLast()
        routeList.removeLast();
        System.out.println("6. Remove Last: " + routeList);

        // 7. editField(T key, String fieldName, Object newValue)
        routeList.editField(route1, "popularity", 120);
        System.out.println("7. Edit Field: " + routeList);

        // 8. clear()
        routeList.clear();
        System.out.println("8. Clear: " + routeList);

        // 9. size()
        System.out.println("9. Size: " + routeList.size());

        // 10. isEmpty()
        System.out.println("10. Is Empty: " + routeList.isEmpty());

        // 11. get(int index)
        try {
            System.out.println("11. Get at Index 1: " + routeList.get(1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("11. Get at Index 1: " + e.getMessage());
        }

        // 12. findByField(String fieldName, Object value)
        routeList.addLast(route1);
        routeList.addLast(route2);
        System.out.println("12. Find by Field: " + routeList.findByField("id", "2"));

        // 13. display()
        System.out.print("13. Display: ");
        routeList.display();

        // 14. iterator()
        Iterator<Route> iterator = routeList.iterator();
        System.out.print("14. Iterator: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        // 15. iterator() - NoSuchElementException
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            System.out.println("15. Iterator - NoSuchElementException: " + e.getMessage());
        }
    }
}
